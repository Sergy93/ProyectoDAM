package bbdd;

import gestor.JCreateTablePanel;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
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
     * @throws java.sql.SQLException
     */
    public void changeDatabase(String database) throws SQLException {
        // this.conn = DriverManager.getConnection(DB_URL + database, DB_USER, DB_PASS);
        String sql = "USE " + database + "; ";
        stmt.executeUpdate(sql);

        ObjectManager.setActualDatabase(database);
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
        try {
            return executeQueryWithFields(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Gets the row count in a table.
     *
     * @param table
     * @param database
     * @return
     * @throws java.sql.SQLException
     */
    public ArrayList<String> getRowCount(String table, String database) throws SQLException {
        changeDatabase(database);
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

        ArrayList result;
        try {
            result = executeQueryWithFields(headerSql);
            return (String[]) result.get(0);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

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
     * @throws java.sql.SQLException
     */
    public ArrayList executeQueryWithFields(String sql) throws SQLException {

        ArrayList result = new ArrayList();
        String[][] rows;
        String[] headers;

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

    }

    /**
     * Executes a SQL insert into the database.
     *
     * @param table
     * @param fields
     * @return
     * @throws java.sql.SQLException
     */
    public boolean insertSql(String table, String[] fields) throws SQLException {

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
        stmt.execute(sql);

        return true;

    }

    /**
     *
     * @param table
     * @param values
     * @return
     * @throws java.sql.SQLException
     */
    public boolean deleteSql(String table, String[] values) throws SQLException {
        String sql = "DELETE FROM " + table + " WHERE " + values[0] + " = '" + values[1] + "';";
        stmt.execute(sql);
        return true;
    }

    /**
     * Updates the selected field to the database.
     *
     * @param table
     * @param field
     * @param oldValue
     * @param newValue
     * @return
     * @throws java.sql.SQLException
     */
    public boolean sqlUpdate(String table, String field, String oldValue, String newValue) throws SQLException {
        String sql = "UPDATE " + table + " SET " + field + " = '" + newValue + "' WHERE " + field + " = '" + oldValue + "';";

        int rows = stmt.executeUpdate(sql);

        stmt.close();

        return rows >= 1;

    }

    /**
     *
     * @param tableName
     * @param fields
     * @param database
     * @return
     * @throws java.sql.SQLException
     */
    public boolean createTable(String tableName, ArrayList<JCreateTablePanel> fields, String database) throws SQLException {

        changeDatabase(database);

        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (";
        for (JCreateTablePanel row : fields) {
            sql += row.txtRowName.getText().toString() + " " + row.cmbRowType.getSelectedItem() + ",";
        }
        sql += "PRIMARY KEY (" + fields.get(0).txtRowName.getText().toString() + "));";

        stmt.executeUpdate(sql);

        return true;
    }

    public boolean dropTable(String tableName, String database) throws SQLException {
        changeDatabase(database);
        String sql = "DROP TABLE " + tableName + ";";

        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to DROP the table " + tableName + "?", "Drop table", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
            return (stmt.executeUpdate(sql) == 0);
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
     * @throws java.sql.SQLException
     */
    public boolean dropDatabase(String name) throws SQLException {

        String sql = "DROP DATABASE " + name + ";";

        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to DROP the database " + name + "?", "Drop database", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
            return (stmt.executeUpdate(sql) == 0);

        } else {
            return false;
        }
    }

    public void renameTable(String table, String newName, String database) throws SQLException {
        String sql = "RENAME TABLE " + table + " TO " + newName;

        stmt.executeUpdate(sql);
    }
}
