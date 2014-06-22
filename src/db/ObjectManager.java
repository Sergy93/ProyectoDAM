package db;

import static java.lang.Character.UnicodeScript.of;
import static java.lang.ProcessBuilder.Redirect.to;
import ui.JCreateTablePanel;
import ui.JInsertRowPanel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
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

    /*
     * Getters and Setters
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

    public static String getActualTable() {
        return actualTable;
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
                ArrayList<String> tables = null;
                try {
                    tables = persistence.getTables(database.toString());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "There has been an error retrieving the database list.", "SQL Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                DefaultMutableTreeNode dbNode = new DefaultMutableTreeNode(new String[]{database.toString()});
                /*
                 ImageIcon icon = new ImageIcon("images/db.png");

                 DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();

                 renderer.setLeafIcon(icon);

                 tree.setCellRenderer(renderer);
                 */
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
            String tableText = null;
            try {
                tableText = countToString(persistence.getTablesCount(database).toString()) + " tables.";
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "There has been an error retrieving the database list.", "SQL Error", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
            }

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
        ArrayList tables = null;
        try {
            tables = persistence.getTables(database);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "There has been an error retrieving the list of tables.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        Object[][] contentTables = new Object[tables.size()][2];
        String[] fields = new String[]{
            "Table", "Number of Rows"
        };
        try {
            persistence.changeDatabase(database);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "We cannot change the database connection.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
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
                JOptionPane.showMessageDialog(null, "There has been an error retrieving the rows for this table.", "SQL Error", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            return (persistence.executeQuery(sql) != null);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "There has been an error executing your order.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     *
     * @param table
     * @param panels
     * @return
     */
    public boolean insertIntoTable(String table, ArrayList<JInsertRowPanel> panels) {
        try {
            String[] values = new String[panels.size()];

            for (int i = 0; i < panels.size(); i++) {
                JInsertRowPanel rowPanel = panels.get(i);
                values[i] = rowPanel.txtName.getText().toString();
            }

            return persistence.insertSql(table, values);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Invalid values to insert.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
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
    public void updateRowValues(JTable jTable, String field, String oldValue, String newValue) {
        try {
            if (persistence.sqlUpdate(actualTable, field, oldValue, newValue)) {
                showRowsOnTable(jTable, actualDatabase, actualTable);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Invalid replacing value.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TreeMap<String, String> getFieldsOnTable(String table) {
        TreeMap<String, String> data = persistence.getTableFields(table);
        if (data != null) {
            return data;
        } else {
            return null;
        }
    }

    public boolean createTable(String table, ArrayList<JCreateTablePanel> fields) {

        for (JCreateTablePanel row : fields) {
            String fieldName = row.txtRowName.getText().toString();
            if (fieldName.isEmpty() || fieldName.length() > 15 || isNumeric(fieldName)) {
                JOptionPane.showMessageDialog(null, "The field " + fieldName + " has an invalid name.", "Length Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        try {
            return persistence.createTable(table, fields, actualDatabase);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "There has been an error, check your syntax.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean dropTable(String name) {
        try {
            return persistence.dropTable(name, actualDatabase);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Write a valid name.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean dropDatabase(String name) {
        try {
            return persistence.dropDatabase(name);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Write a valid name.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void changeDatabase(String database) {
        try {
            persistence.changeDatabase(database);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "An error ocurred while changing the database connection.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean createDatabase(String database) {
        return persistence.createDatabase(database);
    }

    public boolean renameTable(String table, String newName) {
        try {
            persistence.renameTable(table, newName, actualDatabase);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Impossible to rename, check your syntax.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deleteRow(String[] values) {
        try {
            return persistence.deleteSql(actualTable, values);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "An error ocurred deleting the row.", "SQL Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

}
