package gestor;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class JCreateTablePanel extends JPanel {

    public JComboBox cmbRowType;
    public JLabel lblRowName;
    public JLabel lblRowType;
    public JTextField txtRowName;
    public JCheckBox chkNull;

    public JCreateTablePanel() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Layout Generation">                          
    private void initComponents() {

        lblRowName = new JLabel();
        lblRowType = new JLabel();
        txtRowName = new JTextField();
        cmbRowType = new JComboBox();

        setBorder(BorderFactory.createTitledBorder(""));

        lblRowName.setText("Row Name:");

        lblRowType.setText("Row Type:");

        cmbRowType.setModel(new DefaultComboBoxModel(new String[]{"INT(10)", "VARCHAR(50)", "BOOLEAN", "DATE"}));

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblRowType, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblRowName, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(cmbRowType, 0, 100, Short.MAX_VALUE)
                                .addComponent(txtRowName))
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblRowName)
                                .addComponent(txtRowName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblRowType)
                                .addComponent(cmbRowType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>        

}
