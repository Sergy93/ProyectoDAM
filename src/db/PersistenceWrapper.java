package db;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import functions.ObjectManager;
import ui.JCreateTablePanel;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ui.JavaDBManager;
import functions.LogFile;
import static functions.LogFile.updateLogError;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class contains all methods related to the database interaction, and is
 * the only one which have direct access to it.
 *
 * @author Sergio Jimenez Romero & Filip Enculescu
 *
 */
public final class PersistenceWrapper {

    /**
     * Singleton Instance
     */
    private static final PersistenceWrapper INSTANCE = new PersistenceWrapper();

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/";

    private static String DB_USER = "root";
    private static String DB_PASS = "";

    private Connection conn;
    private PreparedStatement stmt;

    private PersistenceWrapper() {
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "There has been an error connecting to the database, please check your credentials.", "", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public static PersistenceWrapper getInstance() {
        return INSTANCE;
    }

    // ---------- Sergio CODE -------------//
    /**
     * Changes the actual database pointer.
     *
     * @param database
     * @throws java.sql.SQLException
     */
    public void changeDatabase(String database) throws SQLException {

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

            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getCatalogs()) {
                while (rs.next()) {
                    String db = rs.getString("TABLE_CAT");
                    result.add(db);
                }
            }

            return result;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "There has been an error listing the databases.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Gets the total count of tables in a database.
     *
     * @param database
     * @return
     * @throws java.sql.SQLException
     */
    public ArrayList<String> getTablesCount(String database) throws SQLException {
        String sql = "SELECT count(table_name) FROM information_schema.tables WHERE table_type = 'base table' AND table_schema='" + database + "'";

        return executeQuery(sql);

    }

    /**
     * Gets the list of tables in a database.
     *
     * @param database
     * @return
     * @throws java.sql.SQLException
     */
    public ArrayList<String> getTables(String database) throws SQLException {
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
            JOptionPane.showMessageDialog(null, "We cannot retrieve rows for table " + table + ", sorry for inconveniences.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
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
     * Returns the fields on a database along with their types.
     *
     * @param table
     * @return
     */
    public TreeMap<String, String> getTableFields(String table) {
        String headerSql = "SHOW COLUMNS FROM " + table;

        try {

            TreeMap<String, String> fields = new TreeMap<>();
            try (ResultSet rs = stmt.executeQuery(headerSql)) {
                while (rs.next()) {
                    String name = rs.getString("Field");
                    String type = rs.getString("Type");
                    fields.put(name, type);
                }
            }
            return fields;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "There has been an error retrieving fields for table " + table, "SQL Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    /**
     *
     * Executes a simple SQL query.
     *
     * @param sql
     * @return
     * @throws java.sql.SQLException
     */
    public ArrayList<String> executeQuery(String sql) throws SQLException {

        stmt = conn.prepareStatement(sql);
        ArrayList result;
        try (ResultSet rs = stmt.executeQuery()) {
            result = new ArrayList();
            while (rs.next()) {
                result.add(rs.getString(1));

            }
            return result;
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

        ResultSet rs;
        ResultSetMetaData metaData;

        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        metaData = rs.getMetaData();

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
     * @param values
     * @return
     * @throws java.sql.SQLException
     */
    public boolean insertSql(String table, String[] values) throws SQLException {

        TreeMap<String, String> headers = getTableFields(table);

        String fieldsString = "";
        String valueString = "";

        Object[] fieldsArray = headers.keySet().toArray();
        Object[] typesArray = headers.values().toArray();

        for (Object field : fieldsArray) {
            fieldsString += field.toString() + ",";
        }
        for (int i = 0; i < values.length; i++) {
            String value = values[i];

            String type = typesArray[i].toString();
            if (type.contains("int") || type.contains("date") || type.contains("bool")) {
                valueString += value.toString() + ",";
            } else {
                valueString += "'" + value.toString() + "',";
            }
            if (type.contains("date") && value.toString().contains("/")) {
                throw new SQLException("Invalid date format.");
            }
        }
        if (!fieldsString.equals("")) {
            fieldsString = fieldsString.substring(0, fieldsString.length() - 1);
        }
        if (!valueString.equals("")) {
            valueString = valueString.substring(0, valueString.length() - 1);
        }

        String sql = "INSERT INTO " + table + " (" + fieldsString + ") VALUES (" + valueString + ");";
        stmt.execute(sql);

        return true;

    }

    /**
     * Deletes the rows with the given parameters on a table
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
     * Creates a table into the given database
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

    /**
     * Drops the table with the given name
     *
     * @param tableName
     * @param database
     * @return
     * @throws SQLException
     */
    public boolean dropTable(String tableName, String database) throws SQLException {
        changeDatabase(database);
        String sql = "DROP TABLE " + tableName + ";";

        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to DROP the table " + tableName + "?", "Drop table", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
            return (stmt.executeUpdate(sql) == 0);
        } else {
            return false;
        }
    }

    /**
     * Creates a database with the given name
     *
     * @param name
     * @return
     */
    public boolean createDatabase(String name) {

        String sql = "CREATE DATABASE " + name + ";";

        try {
            return (stmt.executeUpdate(sql) == 0);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Write a valid name.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Drops the database with the given name
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

    /**
     * Renames a table
     *
     * @param table
     * @param newName
     * @param database
     * @throws SQLException
     */
    public void renameTable(String table, String newName, String database) throws SQLException {
        String sql = "RENAME TABLE " + table + " TO " + newName;

        stmt.executeUpdate(sql);
    }

    // ----------- Filip CODE ---------------//
    /**
     * Method to validate User or to login
     *
     * @param user
     * @param pass
     * @return
     */
    public boolean logIn(String user, String pass) {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, user, pass);
            DB_USER = user;
            DB_PASS = pass;

        } catch (ClassNotFoundException | SQLException ex) {
            LogFile.updateLogError("Connection Itent", "Start JDBC_DRIVER");
            //JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public ArrayList getUsers() {
        ArrayList ar = new ArrayList();
        String sql = "SELECT * FROM mysql.user";
        try {
            Statement sentencia = conn.createStatement();
            ResultSet resul = sentencia.executeQuery(sql);  //executequerry solo para SELECT
            while (resul.next()) {
                ar.add(resul.getString(2) + " / Grant Priv: " + resul.getString(14));
            }
            resul.close();
            sentencia.close();
        } catch (SQLException e) {
            updateLogError("Llenado tabla usuarios fallida", sql);
            LogFile.updateLogError("Get Users", sql);
        }
        return ar;
    }

    public void newUser(String user, String pass, String perm) {
        ArrayList ar = new ArrayList();
        String sql = "";

        if (perm == "ALL") {
            sql = "GRANT ALL PRIVILEGES ON *.* TO '" + user + "'@'localhost' IDENTIFIED BY '" + pass + "' With Grant Option";
        } else {
            sql = "GRANT SELECT, INSERT, UPDATE, DELETE ON " + perm + ".* TO '" + user + "'@'localhost' IDENTIFIED BY '" + pass + "'";
        }

        try {
            Statement sentencia = conn.createStatement();
            ResultSet resul = sentencia.executeQuery(sql);

            resul.close();// Cerrar ResultSet
            sentencia.close();// Cerrar Statement
        } catch (SQLException e) {
            updateLogError("Create new user failed!!", sql);
        }
    }

    public void deleteUser(String user) {
        String sql = "DROP USER " + user + "@localhost";

        try {
            Statement sentencia = conn.createStatement();
            sentencia.executeUpdate(sql);

            sentencia.close();// Cerrar Statement
        } catch (SQLException e) {
            updateLogError("Delete " + user + " failed!!", sql);
        }
    }

    // ------- SQL Management ----------//
    // ------- SQL Management ----------//   
    private void refreshTable(JavaDBManager j) {
        if (ObjectManager.getActualTable() != null) {
            ObjectManager.getInstance().showRowsOnTable(j.getTable(), ObjectManager.getActualDatabase(), ObjectManager.getActualTable());
        }
    }

    private void sqlError(String e, JavaDBManager j) {
        j.setText("<span style=\"color:red;\">ERROR: " + e + "</span>");
    }

    public boolean select(String sql, JavaDBManager j) {
        String actualDB = ObjectManager.getActualDatabase();
        ArrayList rowData = null;

        if (actualDB == null) {
            sqlError("No database selected!!", j);
            return false;
        }
        try {
            rowData = executeQueryWithFields(sql);
        } catch (SQLException ex) {
            sqlError(ex.getMessage(), j);
        }

        String table = "<table border=\"1\">";
        ArrayList rows = null;
        try {
            rows = (ArrayList) rowData.get(1);
        } catch (Exception e) {
            return false;
        }

        for (int x = 0; x < rows.size(); x++) {
            table = table.concat("<tr>");
            String[] row = (String[]) rows.get(x);
            for (int i = 0; i < row.length; i++) {
                table = table.concat("<td>" + row[i] + "</td>");
            }
            table = table.concat("</tr>");
        }
        table = table.concat("</table>");
        table = table.concat("<br><span style=\"color:blue;\">" + rows.size() + " - Results.</span>");
        table = table.concat("<br><span style=\"color:blue;\">Your sql command was executed correctly.</span>");
        j.setText(table);
        return true;
    }

    public boolean insert(String sql, JavaDBManager j) {
        String actualDB = ObjectManager.getActualDatabase();
        if (actualDB == null) {
            sqlError("No database selected!!", j);
            return false;
        }

        try {
            stmt.execute(sql);
            j.setText("<span style=\"color:blue;\">Data inserted successfully!!</span>");
            refreshTable(j);
        } catch (SQLException ex) {
            sqlError(ex.getMessage(), j);
        }
        return true;
    }

    public boolean delete(String sql, JavaDBManager j) {
        String actualDB = ObjectManager.getActualDatabase();
        if (actualDB == null) {
            sqlError("No database selected!!", j);
            return false;
        }

        try {
            stmt.execute(sql);
            j.setText("<span style=\"color:blue;\">Data deleted successfully!!</span>");
            refreshTable(j);
        } catch (SQLException ex) {
            sqlError(ex.getMessage(), j);
        }
        return true;
    }

    public boolean update(String sql, JavaDBManager j) {
        String actualDB = ObjectManager.getActualDatabase();
        if (actualDB == null) {
            sqlError("No database selected!!", j);
            return false;
        }

        try {
            stmt.execute(sql);
            j.setText("<span style=\"color:blue;\">Data updated successfully!!</span>");
            refreshTable(j);
        } catch (SQLException ex) {
            sqlError(ex.getMessage(), j);
        }
        return true;
    }
}
