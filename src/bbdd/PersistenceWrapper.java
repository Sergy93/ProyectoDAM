package bbdd;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Sergio Jimenez Romero
 */
public final class PersistenceWrapper {

    /**
     * Singleton Instance
     */
    private static final PersistenceWrapper INSTANCE = new PersistenceWrapper();

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    private static final String DB_URL = "jdbc:mysql://localhost/";

    private Connection conn;
    private PreparedStatement stmt;

    private PersistenceWrapper() {
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 
     * @return 
     */
    public static PersistenceWrapper getInstance() {
        return INSTANCE;
    }

    /**
     * Changes the actual database pointer.
     *
     * @param database
     */
    public void changeDatabase(String database) {
        try {
            this.conn = DriverManager.getConnection(DB_URL + database, DB_USER, DB_PASS);
            ObjectManager.actualDatabase = database;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Gets a list of all databases.
     *
     * @return
     */
    public ArrayList<String> getDatabases() {
        ArrayList result = new ArrayList();
        try {

            DatabaseMetaData meta = this.conn.getMetaData();
            try (ResultSet rs = meta.getCatalogs()) {
                while (rs.next()) {
                    String db = rs.getString("TABLE_CAT");
                    result.add(db);
                }
            }

            return result;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Gets the total count of tables in a database.
     *
     * @param database
     * @return
     */
    public ArrayList<String> getTablesCount(String database) {
        String sql = "SELECT count(table_name) FROM information_schema.tables WHERE table_type = 'base table' AND table_schema='" + database + "'";

        return executeQuery(sql);

    }

    /**
     * Gets the list of tables in a database.
     *
     * @param database
     * @return
     */
    public ArrayList<String> getTables(String database) {
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_type = 'base table' AND table_schema='" + database + "'";

        return executeQuery(sql);
    }

    /**
     * Gets all the rows in a table.
     *
     * @param table
     * @return
     */
    public ArrayList<String> getRows(String table) {
        String sql = "SELECT * FROM " + table;

        return executeQueryWithFields(sql);
    }

    /**
     * Gets the row count in a table.
     *
     * @param table
     * @return
     */
    public ArrayList<String> getRowCount(String table) {
        changeDatabase(ObjectManager.actualDatabase);
        String sql = "SELECT count(*) FROM " + table;

        return executeQuery(sql);
    }

    /**
     *
     * @param table
     * @return
     */
    public String[] getTableFields(String table) {
        String headerSql = "SELECT * FROM " + table + " LIMIT 1;";

        ArrayList result = executeQueryWithFields(headerSql);

        return (String[]) result.get(0);
    }

    /**
     *
     * Executes a simple SQL query.
     *
     * @param sql
     * @return
     */
    public ArrayList<String> executeQuery(String sql) {
        try {
            stmt = conn.prepareStatement(sql);
            ArrayList result;
            try (ResultSet rs = stmt.executeQuery()) {
                result = new ArrayList();
                while (rs.next()) {
                    result.add(rs.getString(1));
                }
                return result;
            }

        } catch (SQLException | NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }

    /**
     * Executes a Query returning both values and field names.
     *
     * @param sql
     * @return
     */
    public ArrayList executeQueryWithFields(String sql) {

        ArrayList result = new ArrayList();
        String[][] rows;
        String[] headers;
        try {
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();

            int columnCount = metaData.getColumnCount();
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            headers = new String[columnCount];
            rows = new String[columnCount][rowCount];

            for (int i = 1; i <= columnCount; i++) {
                String colName = metaData.getColumnName(i);
                headers[i - 1] = colName;
            }

            ArrayList<String[]> rowsList = new ArrayList();
            while (rs.next()) {
                String[] rowArray = new String[columnCount];
                for (int i = 0; i < rowArray.length; i++) {
                    rowArray[i] = rs.getString(i + 1);
                }
                rowsList.add(rowArray);
            }

            result.add(headers);
            result.add(rowsList);

            return result;

        } catch (SQLException | NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Executes a SQL insert into the database.
     *
     * @param table
     * @param fields
     * @return
     */
    public boolean insertSql(String table, String[] fields) {

        String[] headers = getTableFields(table);

        String headerString = "";
        String valueString = "";

        for (String field : (String[]) headers) {
            headerString += field.toString() + ",";
        }
        for (String value : fields) {
            valueString += value.toString() + ",";
        }
        if (!headerString.equals("")) {
            headerString = headerString.substring(0, headerString.length() - 1);
        }
        if (!valueString.equals("")) {
            valueString = valueString.substring(0, valueString.length() - 1);
        }

        String sql = "INSERT INTO " + table + " (" + headerString + ") VALUES (" + valueString + ");";
        try {
            stmt.execute(sql);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    public boolean deleteSql(String table, String[] values) {
        try {
            String sql = "DELETE FROM " + table + " WHERE " + values[0] + " = '" + values[1] + "';";
            stmt.execute(sql);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Updates the selected field to the database.
     *
     * @param table
     * @param field
     * @param oldValue
     * @param newValue
     * @return
     */
    public boolean sqlUpdate(String table, String field, String oldValue, String newValue) {
        String sql = "UPDATE " + table + " SET " + field + " = '" + newValue + "' WHERE " + field + " = '" + oldValue + "';";

        try {
            int rows = stmt.executeUpdate(sql);

            stmt.close();

            return rows >= 1;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    /**
     *
     * @param name
     * @param fields
     * @return
     */
    public boolean createTable(String name, HashMap fields) {
        changeDatabase(ObjectManager.actualDatabase);

        String sql = "CREATE TABLE IF NOT EXISTS " + name + "(";
        Iterator it = fields.entrySet().iterator();

        //Get the first value
        Map.Entry firstValue = (Map.Entry) it.next();
        sql += firstValue.getValue() + " " + firstValue.getKey();
        it.remove();

        //Continue iterating
        while (it.hasNext()) {
            Map.Entry field = (Map.Entry) it.next();
            sql += (", " + field.getValue() + " " + field.getKey());
        }

        sql += ");";
        try {
            stmt.execute(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean dropTable(String name) {
        changeDatabase(ObjectManager.actualDatabase);
        String sql = "DROP TABLE " + name + ";";

        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to DROP the table " + name + "?", "Drop table", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
            try {
                return (stmt.executeUpdate(sql) == 0);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean createDatabase(String name) {

        String sql = "CREATE DATABASE " + name + ";";

        try {
            return (stmt.executeUpdate(sql) == 0);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Write a valid name.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean dropDatabase(String name) {

        String sql = "DROP DATABASE " + name + ";";

        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to DROP the database " + name + "?", "Drop database", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
            try {
                return (stmt.executeUpdate(sql) == 0);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            return false;
        }
    }
}
