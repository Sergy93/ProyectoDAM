package gestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GestionUsuarios extends javax.swing.JFrame implements ActionListener, ListSelectionListener {

    Gestor g;
    DefaultListModel miModelo;

    public GestionUsuarios(Gestor gu) {
        this.g = gu;
        initComponents();
        BD.Init();
        pnlAdmin.setVisible(false);
        btnCambiar.addActionListener(this);
        btnNuevoUsuario.addActionListener(this);
        btnEliminar.addActionListener(this);
        btnSalir.addActionListener(this);
        this.resize(410, 200);

        if (Variables.isAdminActive) {
            this.resize(412, 488);
            pnlAdmin.setVisible(true);
            miModelo = new DefaultListModel();
            lstLista.setModel(miModelo);
            lstLista.addListSelectionListener(this);
            miModelo.addElement("vero");
            llenarTabla();
            btnEliminar.setEnabled(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        pwsPass = new javax.swing.JPasswordField();
        btnCambiar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        pnlAdmin = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstLista = new javax.swing.JList();
        btnNuevoUsuario = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setTitle("Gestion Usuarios");
        setAlwaysOnTop(true);
        setResizable(false);

        jLabel2.setText("Usuario:");

        jLabel3.setText("Contraseña:");

        btnCambiar.setText("Cambiar");

        jLabel1.setText("CAMBIO SESION");

        jLabel4.setText("USUARIOS/CONTRASEÑA");

        /*
        lstLista.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        */
        jScrollPane1.setViewportView(lstLista);

        btnNuevoUsuario.setText("+");

        btnEliminar.setText("-");

        btnSalir.setText("Salir");

        org.jdesktop.layout.GroupLayout pnlAdminLayout = new org.jdesktop.layout.GroupLayout(pnlAdmin);
        pnlAdmin.setLayout(pnlAdminLayout);
        pnlAdminLayout.setHorizontalGroup(
            pnlAdminLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1)
            .add(pnlAdminLayout.createSequentialGroup()
                .add(2, 2, 2)
                .add(btnNuevoUsuario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnEliminar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(btnSalir))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, pnlAdminLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jLabel4)
                .add(79, 79, 79))
        );
        pnlAdminLayout.setVerticalGroup(
            pnlAdminLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlAdminLayout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 210, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 10, Short.MAX_VALUE)
                .add(pnlAdminLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnNuevoUsuario)
                    .add(btnEliminar)
                    .add(btnSalir)))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(42, 42, 42)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(btnCambiar)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel3)
                                    .add(jLabel2))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(txtUser)
                                    .add(pwsPass, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 240, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(org.jdesktop.layout.GroupLayout.LEADING, pnlAdmin, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .add(layout.createSequentialGroup()
                        .add(153, 153, 153)
                        .add(jLabel1)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(16, 16, 16)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(txtUser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(pwsPass, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnCambiar)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlAdmin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevoUsuario;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstLista;
    private javax.swing.JPanel pnlAdmin;
    private javax.swing.JPasswordField pwsPass;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables

    //Tratamiento botones
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btnAccion = (JButton) e.getSource();

        //Boton salir
        if (btnAccion == btnSalir) {
            this.setVisible(false);
        }

        //Boton cambiar sesion
        if (btnAccion == btnCambiar) {
            String usuario = txtUser.getText();
            char[] cc = pwsPass.getPassword();
            String pass = new String(cc);

            if (usuario.equals("") || pass.equals("")) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce un usuario y contraseña.", "Aviso", JOptionPane.WARNING_MESSAGE);
                BD.updateLogError("Intento Registro fallido", "");
            } else if (BD.verifyUser(usuario, pass)) {
                this.setVisible(false);
                if (usuario.equals("admin")) {
                    Variables.isAdminActive = true;
                } else {
                    Variables.isAdminActive = false;
                }

                System.out.println("Login completo.");
                Variables.loginOK = true;
                Variables.activeUser = usuario;

                System.out.println("Usuario activo: " + Variables.activeUser);
                BD.updateLog("Sesión inicializada ");
                g.setTitle("Gestor BD - Sesión " + usuario);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o Contraseña incorrectos", "Aviso", JOptionPane.WARNING_MESSAGE);
                BD.updateLogError("Intento Registro fallido", "");
            }
        }

        //Boton nuevo usuario
        if (btnAccion == btnNuevoUsuario) {
            JTextField username = new JTextField();
            JTextField password = new JPasswordField();
            ImageIcon icon = new ImageIcon("icon.png");

            Object[] message = {"Nombre Usuario:", username, "Contraseña:", password};

            int option = JOptionPane.showConfirmDialog(this, message, "Nuevo Usuario", JOptionPane.OK_CANCEL_OPTION, WIDTH, icon);
            if (option == JOptionPane.OK_OPTION) {
                if (!username.getText().equals("") && !password.getText().equals("")) {
                    String nombre = username.getText();
                    String pass = password.getText();

                    BD.nuevoUsuario(nombre, pass);
                    BD.updateLog("Creación usuario: " + nombre);
                    llenarTabla();
                }
            }
        }

        //Boton eliminar
        if (btnAccion == btnEliminar) {
            int n = lstLista.getSelectedIndex();
            String user = (String) miModelo.getElementAt(n);
            String[] u = user.split("/");

            ImageIcon icon = new ImageIcon("icon.png");

            int option = JOptionPane.showConfirmDialog(this, "Desea eliminar al usuario: " + u[0].trim(), "Advertencia", JOptionPane.OK_CANCEL_OPTION, WIDTH, icon);
            if (option == JOptionPane.OK_OPTION) {
                BD.eliminarUsuario(u[0].trim());
                BD.updateLog("Eliminación usuario: " + u[0].trim());
                llenarTabla();
            }
        }

    }

    //Tratamiento lista usuarios
    public void valueChanged(ListSelectionEvent e) {
        if (miModelo.isEmpty()) {
            btnEliminar.setEnabled(false);
        } else {
            btnEliminar.setEnabled(true);
        }

        if (lstLista.isSelectionEmpty()) {
            btnEliminar.setEnabled(false);
        } else {
            btnEliminar.setEnabled(true);
        }

    }

    private void llenarTabla() {
        miModelo.removeAllElements();

        ArrayList str = BD.llenarTablaUsuarios();

        for (int i = 0; i < str.size(); i++) {
            miModelo.addElement(str.get(i));
        }
        System.out.println("Llenar tabla completo.");
    }

}
