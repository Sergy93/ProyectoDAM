package bbdd;

import gestor.JCreateTablePanel;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Sergio Jimenez Romero
 */
public final class ObjectManager {

    /**
     * Singleton Instance
     */
    private static final ObjectManager INSTANCE = new ObjectManager();

    private final PersistenceWrapper persistence;

    private static String actualDatabase;
    private static String actualTable;

    public ArrayList<String> databases;

    private ObjectManager() {
        persistence = PersistenceWrapper.getInstance();
        refreshDatabaseList();
    }

    /**
     *
     * @return
     */
    public static ObjectManager getInstance() {
        return INSTANCE;
    }

    public static void setActualDatabase(String db) {
        actualDatabase = db;
    }

    public static String getActualDatabase() {
        return actualDatabase;
    }

    /**
     *
     */
    private void refreshDatabaseList() {
        databases = persistence.getDatabases();
    }

    /**
     * Loads the the jTree with the list of databases.
     *
     * @param tree
     */
    public void loadDatabaseTree(JTree tree) {

        DefaultMutableTreeNode treeRoot = (new DefaultMutableTreeNode("Databases"));

        if (databases != null) {
            for (Object database : databases) {
                ArrayList<String> tables = persistence.getTables(database.toString());
                DefaultMutableTreeNode dbNode = new DefaultMutableTreeNode(database.toString());

                for (String table : tables) {
                    DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(table);
                    dbNode.add(tableNode);
                }

                treeRoot.add(dbNode);
            }
        }
        tree.setModel(new DefaultTreeModel(treeRoot));
    }

    /**
     * Update jTable with the list of databases.
     *
     * @param jTable
     */
    public void showDatabases(JTable jTable) {
        refreshDatabaseList();

        Object[][] contentDatabases = new Object[databases.size()][2];
        String[] fields = new String[]{
            "Database", "Number of tables"
        };
        for (int i = 0; i < databases.size(); i++) {
            String database = databases.get(i).toString();
            String tableText = countToString(persistence.getTablesCount(database).toString()) + " tables.";

            contentDatabases[i][0] = databases.get(i);
            contentDatabases[i][1] = tableText;
        }
        fillTableWithContents(jTable, contentDatabases, fields);
    }

    /**
     * Update jTable with the list of tables on a database.
     *
     * @param jTable
     * @param database
     */
    public void showTablesOnDatabase(JTable jTable, String database) {
        ArrayList tables = persistence.getTables(database);
        Object[][] contentTables = new Object[tables.size()][2];
        String[] fields = new String[]{
            "Table", "Number of Rows"
        };
        try {
            persistence.changeDatabase(database);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
        actualDatabase = database;

        for (int i = 0; i < tables.size(); i++) {
            contentTables[i][0] = tables.get(i);
            String table = tables.get(i).toString();
            String tableText;
            try {
                tableText = countToString(persistence.getRowCount(table, actualDatabase).toString()) + " rows.";

                contentTables[i][1] = tableText;

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        fillTableWithContents(jTable, contentTables, fields);
    }

    /**
     * Update jTable with the list of rows on a table.
     *
     * @param jTable
     * @param database
     * @param table
     */
    public void showRowsOnTable(JTable jTable, String database, String table) {
        ArrayList rowData = persistence.getRows(table);

        actualDatabase = database;
        actualTable = table;

        String[] fields = (String[]) rowData.get(0);
        ArrayList rows = (ArrayList) rowData.get(1);
        Object[][] contentRows = new Object[rows.size()][fields.length];

        for (int x = 0; x < rows.size(); x++) {
            String[] row = (String[]) rows.get(x);
            System.arraycopy(row, 0, contentRows[x], 0, fields.length);
        }
        fillTableWithContents(jTable, contentRows, fields);
    }

    /**
     * Remove braces on count numbers.
     *
     * @param count
     * @return
     */
    public String countToString(String count) {
        String replace = count.replace("[", "").replace("]", "");
        return replace;
    }

    /**
     * Sets the info requested on the jTable with the given params.
     *
     * @param jTable
     * @param content
     * @param header
     */
    public void fillTableWithContents(JTable jTable, Object[][] content, Object[] header) {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.setDataVector(content, header);

        jTable.setModel(tableModel);

        jTable.setRowHeight(50);
    }

    /**
     *
     * @param sql
     * @return
     */
    public boolean executeSQL(String sql) {
        return (persistence.executeQuery(sql) != null);
    }

    /**
     *
     * @param table
     * @param values
     * @return
     */
    public boolean insertIntoTable(String table, String[] values) {
        try {
            return persistence.insertSql(table, values);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     *
     * @param jTable
     * @param field
     * @param oldValue
     * @param newValue
     */
    public void updateRowContent(JTable jTable, String field, String oldValue, String newValue) {
        try {
            if (persistence.sqlUpdate(actualTable, field, oldValue, newValue)) {
                showRowsOnTable(jTable, actualDatabase, actualTable);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String[] getFieldsOnTable(String table) {
        ArrayList data;
        try {
            data = persistence.executeQueryWithFields(table);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (data != null) {
            return (String[]) data.get(0);
        } else {
            return null;
        }
    }

    public boolean createTable(String table, ArrayList<JCreateTablePanel> fields) {

        for (JCreateTablePanel row : fields) {
            String fieldName = row.txtRowName.getText().toString();
            if (fieldName.equals("") || fieldName.length() > 15) {
                return false;
            }
        }
        try {
            return persistence.createTable(table, fields, actualDatabase);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean dropTable(String name) {
        try {
            return persistence.dropTable(name, actualDatabase);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Write a valid name.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean dropDatabase(String name) {
        try {
            return persistence.dropDatabase(name);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Write a valid name.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void changeDatabase(String database) {
        try {
            persistence.changeDatabase(database);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean createDatabase(String database) {
        return persistence.createDatabase(database);
    }
}
