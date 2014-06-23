package ui;

import functions.Variables;
import functions.ObjectManager;
import db.PersistenceWrapper;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

/**
 * @author Sergio Jimenez Romero & Filip Veronel Enculescu
 *
 * This class is the main Frame This class is the main Frame This class is the
 * main Frame
 */
public final class JavaDBManager extends JFrame {

    InterfaceManager intManager;
    ObjectManager objManager;

    private static JavaDBManager MANAGER;

    public JavaDBManager() {
        initComponents();

        intManager = new InterfaceManager();

        addListeners();

        intManager.launchLogin();

        if (Variables.isAdminActive) {
            mnShowLog.setEnabled(true);
        }
        objManager = ObjectManager.getInstance();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollDb = new javax.swing.JScrollPane();
        treeDatabases = new javax.swing.JTree();
        tbpTabs = new javax.swing.JTabbedPane();
        pnlDatabases = new javax.swing.JPanel();
        scrollShow = new javax.swing.JScrollPane();
        tblShow = new javax.swing.JTable();
        btnInsert = new javax.swing.JButton();
        btnRename = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSqlSelect = new javax.swing.JButton();
        btnSqlUpdate = new javax.swing.JButton();
        btnSqlDelete = new javax.swing.JButton();
        btnSqlInsert = new javax.swing.JButton();
        btnExecute = new javax.swing.JButton();
        scrollSql = new javax.swing.JScrollPane();
        txtSql = new javax.swing.JTextPane();
        btnClear = new javax.swing.JButton();
        mbMenu = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        mnUsers = new javax.swing.JMenuItem();
        mnLogoff = new javax.swing.JMenuItem();
        mnExit = new javax.swing.JMenuItem();
        menuAyuda = new javax.swing.JMenu();
        mnSql = new javax.swing.JMenuItem();
        mnShowLog = new javax.swing.JMenuItem();
        mnAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Databases");
        treeDatabases.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        scrollDb.setViewportView(treeDatabases);

        tblShow.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        tblShow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollShow.setViewportView(tblShow);

        btnInsert.setText("Insert");

        btnRename.setText("Rename");

        btnDelete.setText("Delete Selected");

        javax.swing.GroupLayout pnlDatabasesLayout = new javax.swing.GroupLayout(pnlDatabases);
        pnlDatabases.setLayout(pnlDatabasesLayout);
        pnlDatabasesLayout.setHorizontalGroup(
            pnlDatabasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatabasesLayout.createSequentialGroup()
                .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRename, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 298, Short.MAX_VALUE))
            .addComponent(scrollShow)
        );
        pnlDatabasesLayout.setVerticalGroup(
            pnlDatabasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatabasesLayout.createSequentialGroup()
                .addComponent(scrollShow, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatabasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatabasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnRename, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                        .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                    .addComponent(btnInsert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        tbpTabs.addTab("Databases", pnlDatabases);

        btnSqlSelect.setText("Select");

        btnSqlUpdate.setText("Update");

        btnSqlDelete.setText("Delete");

        btnSqlInsert.setText("Insert");

        btnExecute.setText("Execute");

        scrollSql.setViewportView(txtSql);

        btnClear.setText("Clear");

        menuArchivo.setText("File");

        mnUsers.setText("Users");
        menuArchivo.add(mnUsers);

        mnLogoff.setText("Log out");
        menuArchivo.add(mnLogoff);

        mnExit.setText("Exit");
        menuArchivo.add(mnExit);

        mbMenu.add(menuArchivo);

        menuAyuda.setText("Help");
        menuAyuda.setToolTipText("");

        mnSql.setText("SQL Commands");
        menuAyuda.add(mnSql);

        mnShowLog.setText("Log as Admin");
        menuAyuda.add(mnShowLog);

        mnAbout.setText("About");
        menuAyuda.add(mnAbout);

        mbMenu.add(menuAyuda);

        setJMenuBar(mbMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollDb, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tbpTabs, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollSql)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSqlSelect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSqlUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSqlDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSqlInsert)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExecute)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollDb, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tbpTabs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollSql)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSqlSelect)
                    .addComponent(btnSqlUpdate)
                    .addComponent(btnSqlDelete)
                    .addComponent(btnSqlInsert)
                    .addComponent(btnExecute)
                    .addComponent(btnClear))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public static void main(String args[]) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            Logger.getLogger(JavaDBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MANAGER = new JavaDBManager();
                MANAGER.setLocationRelativeTo(null);
                MANAGER.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExecute;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnRename;
    private javax.swing.JButton btnSqlDelete;
    private javax.swing.JButton btnSqlInsert;
    private javax.swing.JButton btnSqlSelect;
    private javax.swing.JButton btnSqlUpdate;
    private javax.swing.JMenuBar mbMenu;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenuItem mnAbout;
    private javax.swing.JMenuItem mnExit;
    private javax.swing.JMenuItem mnLogoff;
    private javax.swing.JMenuItem mnShowLog;
    private javax.swing.JMenuItem mnSql;
    private javax.swing.JMenuItem mnUsers;
    private javax.swing.JPanel pnlDatabases;
    private javax.swing.JScrollPane scrollDb;
    private javax.swing.JScrollPane scrollShow;
    private javax.swing.JScrollPane scrollSql;
    private javax.swing.JTable tblShow;
    private javax.swing.JTabbedPane tbpTabs;
    private javax.swing.JTree treeDatabases;
    private javax.swing.JTextPane txtSql;
    // End of variables declaration//GEN-END:variables

    public void addListeners() {

        txtSql.setContentType("text/html");
        Font currentFont = treeDatabases.getFont();
        Font bigFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 3);
        treeDatabases.setFont(bigFont);

        mnExit.addActionListener(intManager);
        menuAyuda.addActionListener(intManager);
        mnSql.addActionListener(intManager);
        mnShowLog.addActionListener(intManager);
        mnLogoff.addActionListener(intManager);
        mnUsers.addActionListener(intManager);
        mnAbout.addActionListener(intManager);

        tblShow.addMouseListener(intManager);

        btnSqlDelete.addActionListener(intManager);
        btnSqlInsert.addActionListener(intManager);
        btnSqlSelect.addActionListener(intManager);
        btnSqlUpdate.addActionListener(intManager);
        btnExecute.addActionListener(intManager);
        btnClear.addActionListener(intManager);

        btnInsert.addActionListener(intManager);
        btnDelete.addActionListener(intManager);
        btnRename.addActionListener(intManager);

        treeDatabases.addTreeSelectionListener(intManager);
    }

    // Method to load initial data
    public void init() {
        objManager.refreshDatabaseList();
        objManager.loadDatabaseTree(treeDatabases);
        objManager.showDatabases(tblShow);
    }

    // Method to clear all data form txtSql panel
    public void clearPanel() {
        txtSql.setText(null);
    }

    // Method to set text into txtsql
    public void setText(String txt) {
        txtSql.setText(txt);
    }

    // Method to show/hide menu option Log History
    public void showLog(boolean status) {
        mnShowLog.setEnabled(status);
    }

    // Method manage table
    public JTable getTable() {
        return tblShow;
    }

    /**
     * @author Sergio Jimenez Romero & Filip Veronel Enculescu
     *
     * This class handles all events related to the main frame interaction.
     */
    private class InterfaceManager extends MouseAdapter implements ActionListener, TreeSelectionListener {

        //  ----------- Filip----------- //
        public void launchLogin() {
            txtSql.setText(null);
            Login l = new Login(JavaDBManager.this);
            l.setLocationRelativeTo(null);
            l.setVisible(true);
            l.toFront();
        }

        public void webSql() {
            try {
                Desktop.getDesktop().browse(new URL("https://dev.mysql.com/doc/refman/5.0/es/sql-syntax.html").toURI());
            } catch (Exception ex) {
                Logger.getLogger(JavaDBManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        public void readLog() {
            txtSql.removeAll();
            String linea;
            String log = "";

            try { //leemos el archivo con un FileReader y un BufferedReader
                BufferedReader br = new BufferedReader(new FileReader("LOG.txt"));
                while ((linea = br.readLine()) != null) {
                    log = log + linea + "\n";
                }
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(JavaDBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            txtSql.setText(log);
        }

        //Tratamiento botones
        @Override
        public void actionPerformed(ActionEvent e) {
            Object o = e.getSource();
            checkMenuButtons(e);
            try {
                checkSqlButtons(e);
            } catch (SQLException ex) {
                Logger.getLogger(JavaDBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (o == mnShowLog) {
                readLog();
            }
            if (o == mnLogoff) {
                launchLogin();
            }
            if (o == mnExit) {
                System.exit(0);
            }
            if (o == mnSql) {
                webSql();
            }
            if (o == mnSql) {
                webSql();
            }
            if (o == btnClear) {
                clearPanel();
            }
            if (o == mnAbout) {
                About s = new About();
                s.setLocationRelativeTo(null);
                s.setVisible(true);
            }
            if (o == mnUsers) {
                AdminUsers a = new AdminUsers(JavaDBManager.this);
                a.setLocationRelativeTo(null);
                a.setVisible(true);
                a.toFront();
            }
        }

        public boolean checkSqlButtons(ActionEvent e) throws SQLException {
            String table = ObjectManager.getActualTable();
            boolean returnValue = true;

            //TODO descomentar las lineas de select reales a usar en produccion.
            if (e.getSource().equals(btnSqlSelect)) {
                txtSql.setText("SELECT value FROM " + table + " WHERE field = 'value';");

            } else if (e.getSource().equals(btnSqlDelete)) {
                txtSql.setText("DELETE FROM " + table + " WHERE field = 'value';");
            } else if (e.getSource().equals(btnSqlUpdate)) {
                txtSql.setText("UPDATE " + table + " SET field = 'value' WHERE another_field = 'another_value';");
            } else if (e.getSource().equals(btnSqlInsert)) {
                txtSql.setText("INSERT INTO " + table + " (field1, field2, field3) VALUES ('value1', 'value2', 'value3');");
            } else if (e.getSource().equals(btnExecute)) {
                executeSql();
            } else {
                returnValue = false;
            }
            return returnValue;
        }

        private void executeSql() throws SQLException {
            String sql = txtSql.getText().toString();
            sql = sql.replaceAll("\\<.*?>", "").trim();
            boolean successful = false;

            if (sql.isEmpty()) {
                txtSql.setText("<span style=\"color:red;\">ERROR: Sql EMPTY statement!!</span>");
            }
            if (sql.contains("ERROR")) {
                txtSql.setText("<span style=\"color:red;\">Please write a correct SQL statement!!</span>");
            }
            if (sql.contains("SELECT")) {
                successful = PersistenceWrapper.getInstance().select(sql, JavaDBManager.this);
            }
            if (sql.contains("INSERT")) {
                successful = PersistenceWrapper.getInstance().insert(sql, JavaDBManager.this);
            }
            if (sql.contains("DELETE")) {
                successful = PersistenceWrapper.getInstance().delete(sql, JavaDBManager.this);
            }
            if (sql.contains("UPDATE")) {
                successful = PersistenceWrapper.getInstance().update(sql, JavaDBManager.this);
            }

            if (successful) {
                //txtSql.add("Your sql command was executed correctly.");
            } else {
                //txtSql.setText("Your sql command could not be executed.");
            }
        }

        //  ----------- Sergio----------- //
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            TreePath path = e.getPath();
            String node = path.getLastPathComponent().toString();
            int level = path.getPathCount();

            if (level == 1) {
                objManager.showDatabases(tblShow);
            } else if (level == 2) {
                objManager.showTablesOnDatabase(tblShow, node);
            } else {
                String database = path.getParentPath().getLastPathComponent().toString();
                objManager.changeDatabase(database);
                objManager.showRowsOnTable(tblShow, database, node);
            }
        }

        //Handles clicking events on the main JTable.
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {

                JFrame parent = (JFrame) SwingUtilities.getRoot(tblShow);

                int row = tblShow.getSelectedRow();
                int column = tblShow.getSelectedColumn();

                String rowValue;
                try {
                    rowValue = tblShow.getModel().getValueAt(row, column).toString();
                } catch (NullPointerException ex) {
                    rowValue = "";
                }

                String columnType = "";
                String columnName = "";
                String columnFirstValue = "";

                try {
                    columnType = tblShow.getModel().getColumnName(0);
                    columnName = tblShow.getModel().getColumnName(column);
                    columnFirstValue = tblShow.getModel().getValueAt(row, 0).toString();

                } catch (Exception ex) {
                    Logger.getLogger(PersistenceWrapper.class.getName()).log(Level.SEVERE, null, ex);
                }

                switch (columnType) {
                    case "Database":
                        objManager.showTablesOnDatabase(tblShow, columnFirstValue);
                        break;
                    case "Table":
                        objManager.showRowsOnTable(tblShow, ObjectManager.getActualDatabase(), columnFirstValue);
                        break;
                    default:

                        String newValue = JOptionPane.showInputDialog(parent, "Write the new value:", rowValue);
                        objManager.updateRowValues(tblShow, columnName, rowValue, newValue);
                        break;
                }

            }
        }

        //Handles action when pressing Insert,Delete and Rename buttons.
        public boolean checkMenuButtons(ActionEvent e) {

            int row = tblShow.getSelectedRow() < 0 ? 0 : tblShow.getSelectedRow();
            int column = tblShow.getSelectedColumn() <= 0 ? 0 : tblShow.getSelectedColumn();

            String cellValue = "";
            String columnName = "";
            String firstValueName = "";
            String columnType = tblShow.getModel().getColumnName(0);

            if (tblShow.getModel().getRowCount() > 0) {
                cellValue = tblShow.getModel().getValueAt(row, column).toString();

                columnName = tblShow.getModel().getColumnName(column);
                firstValueName = tblShow.getModel().getValueAt(row, 0).toString();
            }
            if (e.getSource().equals(btnRename)) {
                if (columnType.equals("Table")) {
                    String newTable = JOptionPane.showInputDialog(MANAGER, "New table name:", "Rename table", JOptionPane.QUESTION_MESSAGE);
                    if (objManager.renameTable(firstValueName, newTable)) {
                        objManager.showTablesOnDatabase(tblShow, ObjectManager.getActualDatabase());
                        objManager.loadDatabaseTree(treeDatabases);
                    }
                } else {
                    JOptionPane.showMessageDialog(MANAGER, "The selected item is not a table.", "Cannot rename", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (e.getSource().equals(btnInsert)) {
                boolean created;
                switch (columnType) {
                    case "Database":
                        String databaseName = JOptionPane.showInputDialog(MANAGER, "New database name:", "New database");
                        if (databaseName != null) {
                            created = objManager.createDatabase(databaseName);

                            if (created) {
                                objManager.showDatabases(tblShow);
                                objManager.loadDatabaseTree(treeDatabases);
                            }
                        }

                        break;

                    case "Table":

                        if (SwingUtilities.isEventDispatchThread()) {
                            createTable();
                        } else {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    createTable();

                                }
                            });
                        }

                        break;
                    default:

                        insertRow(ObjectManager.getActualTable());
                        break;
                }

            } else if (e.getSource().equals(btnDelete)) {

                boolean deleted;

                switch (columnType) {

                    case "Database":
                        deleted = objManager.dropDatabase(firstValueName);
                        if (deleted) {
                            objManager.showDatabases(tblShow);
                            objManager.loadDatabaseTree(treeDatabases);

                            JOptionPane.showMessageDialog(MANAGER, "Database " + firstValueName + " was dropped correctly.", "Dropped successfully", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;

                    case "Table":
                        deleted = objManager.dropTable(firstValueName);
                        if (deleted) {
                            objManager.showTablesOnDatabase(tblShow, ObjectManager.getActualDatabase());
                            objManager.loadDatabaseTree(treeDatabases);

                            JOptionPane.showMessageDialog(MANAGER, "Table " + firstValueName + " was dropped correctly.", "Dropped successfully", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;

                    default:
                        int isDeleted = JOptionPane.showConfirmDialog(MANAGER, "You really want to delete the row with " + columnName + "=" + cellValue + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                        if (isDeleted == 0) {
                            objManager.deleteRow(new String[]{columnName, cellValue});
                            objManager.showRowsOnTable(tblShow, ObjectManager.getActualDatabase(), ObjectManager.getActualTable());
                        }
                        break;
                }
            }
            return false;
        }

        public void createTable() {

            final ArrayList<JCreateTablePanel> panelsList = new ArrayList<>();
            final JDialog containerDialog = new JDialog(MANAGER, "New table", true);

            final String tableName;

            JCreateTablePanel row = new JCreateTablePanel();

            JPanel rowContainer = new JPanel(new GridBagLayout());
            GridBagConstraints cs = new GridBagConstraints();

            JLabel lblTable = new JLabel();
            JButton btnCreateTable = new JButton("Create Table");

            Integer numberOfRows;
            Dimension rowContainerDimension;

            tableName = JOptionPane.showInputDialog(MANAGER, "Enter the new table name", "New table", JOptionPane.QUESTION_MESSAGE);

            if (tableName == null) {
                return;
            } else if (tableName.equals("")) {
                JOptionPane.showMessageDialog(MANAGER, "The name cannot be empty.", "Empty name", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (tableName.length() > 15) {
                JOptionPane.showMessageDialog(MANAGER, "The name cannot be longer than 15 characters.", "Length Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (ObjectManager.isNumeric(tableName)) {
                JOptionPane.showMessageDialog(MANAGER, "The name cannot be numeric.", "Length Error", JOptionPane.ERROR_MESSAGE);
            }

            containerDialog.setTitle(tableName);

            try {
                numberOfRows = (int) JOptionPane.showInputDialog(MANAGER, "Enter the number of fields", "Number of fields", JOptionPane.QUESTION_MESSAGE, null, new Integer[]{1, 2, 3, 4, 5, 6}, null);
            } catch (NullPointerException ex) {
                return;
            }

            cs.fill = GridBagConstraints.HORIZONTAL;
            cs.gridy = 1;

            for (int i = 0; i < numberOfRows; i++) {
                row = new JCreateTablePanel();

                cs.gridy = i;
                rowContainer.add(row, cs);

                containerDialog.setSize(row.getPreferredSize().width + 20, rowContainer.getPreferredSize().height + row.getPreferredSize().height);
                panelsList.add(row);
            }

            btnCreateTable.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (objManager.createTable(tableName, panelsList)) {
                        containerDialog.dispose();
                        objManager.showTablesOnDatabase(tblShow, ObjectManager.getActualDatabase());
                        objManager.loadDatabaseTree(treeDatabases);
                    }
                }
            });

            cs.gridy += 1;

            rowContainer.add(btnCreateTable, cs);

            rowContainer.setBorder(new LineBorder(Color.GRAY));

            rowContainerDimension = rowContainer.getPreferredSize();

            containerDialog.add(rowContainer);

            lblTable.setLocation(rowContainerDimension.width / 2, rowContainerDimension.height / 2);

            containerDialog.setSize(rowContainerDimension.width + 20, rowContainerDimension.height + row.getPreferredSize().height);

            containerDialog.setLocationRelativeTo(MANAGER);

            containerDialog.setResizable(false);
            containerDialog.setVisible(true);
        }

        /**
         * Shows the JCreateTable form and handles the insertion of a new row.
         *
         * @param tableName table where we will insert the row into.
         */
        public void insertRow(final String tableName) {
            final JDialog containerDialog = new JDialog(MANAGER, "INSERT:" + tableName, true);

            final ArrayList<JInsertRowPanel> panelsList = new ArrayList<>();

            JInsertRowPanel row = new JInsertRowPanel("");

            JButton btnInsertRow = new JButton("Insert");
            JPanel rowContainer = new JPanel(new GridBagLayout());
            GridBagConstraints cs = new GridBagConstraints();

            TreeMap fields = new TreeMap();
            try {
                fields = objManager.getFieldsOnTable(tableName);
            } catch (NullPointerException ex) {
                Logger.getLogger(ObjectManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            Dimension rowContainerDimension;

            cs.fill = GridBagConstraints.HORIZONTAL;
            cs.gridy = 1;

            int i = 0;
            for (Object field : fields.keySet().toArray()) {
                row = new JInsertRowPanel(field.toString() + " : " + fields.get((String) field));
                cs.gridy = i;
                rowContainer.add(row, cs);
                containerDialog.setSize(rowContainer.getPreferredSize().width + 20, rowContainer.getPreferredSize().height + row.getPreferredSize().height);
                panelsList.add(row);
                i++;
            }

            btnInsertRow.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (objManager.insertIntoTable(tableName, panelsList)) {
                        containerDialog.dispose();
                        objManager.showTablesOnDatabase(tblShow, ObjectManager.getActualDatabase());
                        objManager.loadDatabaseTree(treeDatabases);
                    }
                }
            });
            cs.gridy += 1;
            rowContainer.add(btnInsertRow, cs);
            containerDialog.setSize(rowContainer.getPreferredSize().width + 20, rowContainer.getPreferredSize().height + btnInsertRow.getPreferredSize().height);

            rowContainerDimension = rowContainer.getPreferredSize();

            containerDialog.add(rowContainer);
            containerDialog.setResizable(false);
            containerDialog.setLocationRelativeTo(MANAGER);
            containerDialog.setSize(rowContainerDimension.width + 20, rowContainerDimension.height + row.getPreferredSize().height);
            containerDialog.setVisible(true);
        }

    }
}
