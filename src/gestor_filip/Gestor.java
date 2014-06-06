package gestor;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.lang.Object;

        
        
        


public class Gestor extends javax.swing.JFrame implements ActionListener, TreeSelectionListener{
    DefaultTreeModel modelo;
    DefaultMutableTreeNode root;
    
    public Gestor() {
        initComponents();
        BD.Init();
        
        btnDelete.addActionListener(this);
        btnInsert.addActionListener(this);
        btnLimpiar.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnSelect.addActionListener(this);
        btnEjecutar.addActionListener(this);
        
        mnSalir.addActionListener(this);
        mnSobre.addActionListener(this);
        mnSql.addActionListener(this);
        mnVerLog.addActionListener(this);
        mnCerrarSesion.addActionListener(this);
        mnUsuarios.addActionListener(this);
        if(Variables.isAdminActive){mnVerLog.setEnabled(true);}
        //if(!Variables.loginOK) {launchLogin();}
        txtPane.setContentType("text/html");
        centerFrame();
        
        
        //Init arbol
        arbol.addTreeSelectionListener(this);     
        root =  new DefaultMutableTreeNode("BD");
        refreshTree();
    }


    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu3 = new javax.swing.JMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        arbol = new javax.swing.JTree();
        jComboBox1 = new javax.swing.JComboBox();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        btnSelect = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnEjecutar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtPane = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnUsuarios = new javax.swing.JMenuItem();
        mnCerrarSesion = new javax.swing.JMenuItem();
        mnSalir = new javax.swing.JMenuItem();
        mnSobre = new javax.swing.JMenu();
        mnSql = new javax.swing.JMenuItem();
        mnVerLog = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        jMenu3.setText("jMenu3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestor BD");
        setResizable(false);

        jScrollPane1.setViewportView(arbol);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ver estructura", "Ver contenido" }));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Bases", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 771, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Info", jPanel4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 771, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Importar", jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 771, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Exportar", jPanel6);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 771, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Backup", jPanel7);

        btnSelect.setText("SELECT");

        btnUpdate.setText("UPDATE");

        btnDelete.setText("DELETE");

        btnInsert.setText("INSERT");

        btnLimpiar.setText("Limpiar");

        btnEjecutar.setText("Ejecutar");

        jScrollPane3.setViewportView(txtPane);

        jMenu1.setText("Archivo");

        mnUsuarios.setText("Usuarios");
        jMenu1.add(mnUsuarios);

        mnCerrarSesion.setText("Cerrar sesion");
        jMenu1.add(mnCerrarSesion);

        mnSalir.setText("Salir");
        jMenu1.add(mnSalir);

        jMenuBar1.add(jMenu1);

        mnSobre.setText("Ayuda");

        mnSql.setText("Sql page");
        mnSobre.add(mnSql);

        mnVerLog.setText("Log (Admin)");
        mnSobre.add(mnVerLog);

        jMenuItem6.setText("Sobre");
        mnSobre.add(jMenuItem6);

        jMenuBar1.add(mnSobre);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSelect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInsert)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEjecutar))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSelect)
                            .addComponent(btnUpdate)
                            .addComponent(btnDelete)
                            .addComponent(btnInsert)
                            .addComponent(btnLimpiar)
                            .addComponent(btnEjecutar)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
 public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Gestor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gestor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gestor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gestor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gestor().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTree arbol;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEjecutar;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnSelect;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JMenuItem mnCerrarSesion;
    private javax.swing.JMenuItem mnSalir;
    private javax.swing.JMenu mnSobre;
    private javax.swing.JMenuItem mnSql;
    private javax.swing.JMenuItem mnUsuarios;
    private javax.swing.JMenuItem mnVerLog;
    private javax.swing.JTextPane txtPane;
    // End of variables declaration//GEN-END:variables
    private void centerFrame(){//Centrando el Frame
        Dimension dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int hei = this.getHeight();
        int wid = this.getWidth();       
        int x = ((int) dim.getWidth()-wid)/2;
        int y = ((int) dim.getHeight()-hei)/2;       
        this.setLocation(x, y);
    }
    
     
       //Tratamiento botones
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        
        if(o==mnSalir){
            System.exit(0);
        }
        
        if(o==mnSobre){
               Sobre s= new Sobre();
               s.setLocationRelativeTo(null);
               s.setVisible(true);
        }
        
        if(o==mnSql){
               webSql();
        }
        
        if(o==mnUsuarios){
            launchAdminUsers();
        }
        
        if(o==mnCerrarSesion){
            System.out.println("Sesión cerrada.");
            launchLogin();
        }
        
        if(o==mnVerLog){  //falta poner restriccion admin
            readLog();
        }
        
        
        if(o==btnInsert){     
            txtPane.setText("INSERT INTO  `table`  (`column_1`,  `column_2`)   VALUES  ([value_1],  [value_2])");
        }
        
        if(o==btnDelete){ 
            txtPane.setText("DELETE FROM `table` WHERE x=y");
        }
        
        if(o==btnUpdate){ 
            txtPane.setText("UPDATE `table` SET `column_1`=[value-1],`column_2`=[value-2] WHERE x=y");
        }
        
        if(o==btnSelect){ 
            txtPane.setText("SELECT `column_1`, `column_2` FROM `table` WHERE x=y");
        }
        
        if(o==btnLimpiar){ 
            txtPane.setText("");
        }
        
        
        if (o == btnEjecutar) {
            String sql = txtPane.getText();
            sql = sql.replaceAll("\\<.*?>", "").trim();
            System.out.println(sql);

            if (isTableSelected() && sql != "") {
                String base = getSelectedBase();

                sql = BD.ejecutar_cmd(sql, base);
                txtPane.setText(sql);
            } else {
                JOptionPane.showMessageDialog(this, "No hay ninguna tabla seleccionada \n O el cuadro de texto esta vacio.");
            }
        }
    }




    
    
     
    //*******************************************//
    //****************  Metodos *****************//
    public void webSql(){
          try{
                Desktop.getDesktop().browse(new URL("https://dev.mysql.com/doc/refman/5.0/es/sql-syntax.html").toURI());
            }catch (URISyntaxException | IOException e){
                e.printStackTrace();
            }
    }
    
    
    public void launchLogin(){
        Registro l= new Registro(this);
        l.setLocationRelativeTo(null);
        l.setVisible(true);
        l.toFront();
    }
    
    
    public void launchAdminUsers(){
         GestionUsuarios g= new GestionUsuarios(this);
         g.setLocationRelativeTo(null);
         g.setVisible(true);
         g.toFront();
    }
    
    
    public void readLog(){
          txtPane.removeAll();
          String linea;
          String log="";
            
          try{ //leemos el archivo con un FileReader y un BufferedReader
               BufferedReader br =  new BufferedReader(new FileReader("LOG.txt"));
               
               while((linea= br.readLine())!=null){
                   //System.out.println(linea);
                   log = log + linea+"\n";               
               }
           
                br.close();
                
           //captamos la excepcion si hay
           }catch(IOException e){
                System.out.println("No se ha creado el archivo");
                e.printStackTrace();
           }
           txtPane.setText(log);
    }


    
   
    
    
    
    //Metodo para rellenar el arbol con las bases de datos disponibles
    public void refreshTree(){
        DefaultMutableTreeNode base;
        DefaultMutableTreeNode nodo;      
        ArrayList<String> bases = BD.getListDB();
        ArrayList<String> tablas;

        for(String temp: bases){ //recorremos bases de datos
            base  =  new DefaultMutableTreeNode(temp);
            
            tablas = BD.getTablesDB(temp);
            for(String temp2: tablas){
                nodo = new DefaultMutableTreeNode(temp2);
                base.add(nodo);
            }
            
            root.add(base);
        } 
        modelo = new DefaultTreeModel(root);
        arbol.setModel(modelo);
        System.out.println("Rellenado arbol completo...");
    }
    
    
    //Método que avisa cada vez que se hace una seleccion en el arbol
    public void valueChanged(TreeSelectionEvent e) {
        
    }


    //Método que devuelve el nombre de la base de datos seleccionada.
    public static String getSelectedBase(){
        String s="";      
        if(!arbol.isSelectionEmpty()){   
                TreePath d = arbol.getSelectionPath(); 
                s = d.getPathComponent(1).toString();
                System.out.println(s);
        }else{
            System.out.println("No hay ninguna base de datos seleccionada.");
        }  
        return s;
    }
    
    
    //Método que devulve el nombre de la tabla seleccionada
    public static String getSelectedTable(){
        String s = "";    
        if(!arbol.isSelectionEmpty()){            
            TreePath d = arbol.getSelectionPath();  
            if(d.getPathCount()>2){
                s = d.getPathComponent(2).toString();
                System.out.println(s);
            }else{
                System.out.println("No hay ninguna tabla seleccionada.");
            }
        }else{
            System.out.println("No hay ninguna base de datos seleccionada.");
        }      
        return s;
    }
    
    
    //Método que confirma si esta seleccionado alguna base - boolean
    public static boolean isBaseSelected(){   
        if(!arbol.isSelectionEmpty()){
            System.out.println("Base seleccionada.");
            return true;
        }else{
            System.out.println("Ninguna base de datos seleccionada.");
            return false;
        }
    }
    
 
    //Método que confirma si esta seleccionado alguna tabla - boolean
    public static boolean isTableSelected(){   
        if(!arbol.isSelectionEmpty()){            
            TreePath d = arbol.getSelectionPath();  
            if(d.getPathCount()>2){
                System.out.println("Tabla seleccionada.");
                return true;
            }else{
                System.out.println("No hay ninguna tabla seleccionada.");
                return false;
            }
        }else{
            System.out.println("Ninguna base de datos seleccionada.");
            return false;
        }
    }
    
    
    
    
    
    
    
    
    
    
    
}//Class Gestor