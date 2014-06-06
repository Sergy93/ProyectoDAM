package gestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;




public class Registro extends javax.swing.JFrame implements ActionListener{
    Gestor g;
    
    
    public Registro(Gestor gu) {
        this.g=gu;
        initComponents();
        activeMain(false);
        btnRegistrar.addActionListener(this);
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
        setTitle("Registro");
        setAlwaysOnTop(true);

        jLabel2.setText("Usuario:");

        jLabel3.setText("Contraseña:");

        btnRegistrar.setText("Registrar");

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
                .addContainerGap(34, Short.MAX_VALUE))
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

    public void activeMain(boolean op){
        if(op){
            g.setEnabled(true);
        }else{
            g.setEnabled(false);
        }
    }

    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
            if(e.getSource()==btnRegistrar){
                String usuario = txtUser.getText();
                char[] cc = pwsPass.getPassword();
                String pass =  new String(cc);
                String base;
                String tablas;
                
                if(usuario.equals("") || pass.equals("")){
                    JOptionPane.showMessageDialog(this, "Por favor, introduce un usuario y una contraseña.", "Aviso", JOptionPane.WARNING_MESSAGE);
                
                }else if(BD.verifyUser(usuario, pass)){
                    activeMain(true);
                    this.setVisible(false);
                    if(usuario.equals("admin")){Variables.isAdminActive=true;}
                    
                    System.out.println("Login completo.");
                    Variables.loginOK    = true;
                    Variables.activeUser = usuario;
                    
                    System.out.println("Usuario activo: "+Variables.activeUser);
                    BD.updateLog("Sesión inicializada");
                    g.setTitle("Gestor BD - Sesión "+usuario);
                }else{
                    System.out.println("Usuario o contraseña incorrectos.");
                    JOptionPane.showMessageDialog(this, "Usuario o Contraseña incorrectos", "Aviso", JOptionPane.WARNING_MESSAGE);
                }           
            }
    }











}
