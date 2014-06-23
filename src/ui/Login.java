package ui;

import functions.LogFile;
import functions.Variables;
import db.PersistenceWrapper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Filip Veronel Enculescu
 *
 */
public class Login extends javax.swing.JFrame implements ActionListener {

    JavaDBManager mainFrame;

    public Login(JavaDBManager gu) {
        this.mainFrame = gu;
        initComponents();
        btnRegistrar.addActionListener(this);
        activeMain(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        pwsPass = new javax.swing.JPasswordField();
        btnRegistrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setAlwaysOnTop(true);

        jLabel2.setText("Username:");

        jLabel3.setText("Password:");

        btnRegistrar.setText("Log In");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(19, 19, 19)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(btnRegistrar)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabel3)
                            .add(jLabel2))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(txtUser)
                            .add(pwsPass, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(txtUser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(pwsPass, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(btnRegistrar)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField pwsPass;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables

    public void activeMain(boolean op) {
        if (op) {
            mainFrame.setEnabled(true);
            mainFrame.setVisible(true);
        } else {
            mainFrame.setEnabled(false);
            mainFrame.setVisible(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PersistenceWrapper inst = PersistenceWrapper.getInstance();

        if (e.getSource() == btnRegistrar) {
            String usuario = txtUser.getText();
            char[] cc = pwsPass.getPassword();
            String pass = new String(cc);
            String base;
            String tablas;

            if (usuario.equals("")) {
                JOptionPane.showMessageDialog(this, "Please, enter an user and a password.", "Warning", JOptionPane.WARNING_MESSAGE);

            } else if (inst.logIn(usuario, pass)) {
                activeMain(true);
                this.setVisible(false);

                if (usuario.equals("root")) {
                    Variables.isAdminActive = true;
                    mainFrame.showLog(true);
                } else {
                    Variables.isAdminActive = false;
                    mainFrame.showLog(false);
                }

                Variables.loginOK = true;
                Variables.activeUser = usuario;

                LogFile.updateLog("Session initialized");
                mainFrame.setTitle("DB Manager - User:" + usuario);
                mainFrame.init();
            } else {
                LogFile.updateLogError("Login Attempt", "Error Login user: " + usuario);
                JOptionPane.showMessageDialog(this, "Incorrect user or password", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

}
