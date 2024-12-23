package com.jelisavacluka554.rmt_client;

import com.jelisavacluka554.rmt_common.domain.Application;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

/**
 *
 * @author luka
 */
public class DialogApplicationData extends javax.swing.JDialog {

    /**
     * Creates new form DialogApplicationData
     */
    private Application apl;
    public DialogApplicationData(java.awt.Frame parent, boolean modal, Application apl) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(this);
        this.apl = apl;
        writeToTextArea();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtApplication = new javax.swing.JTextArea();
        btnSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Application info");
        setResizable(false);

        txtApplication.setEditable(false);
        txtApplication.setColumns(20);
        txtApplication.setRows(5);
        txtApplication.setFocusable(false);
        jScrollPane1.setViewportView(txtApplication);

        btnSave.setText("Save...");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            // TODO add your handling code here:
            saveTextToFile(txtApplication);
        } catch (IOException ex) {
            Logger.getLogger(DialogApplicationData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnSaveActionPerformed

    private static void saveTextToFile(JTextArea textArea) throws IOException {
        String text = textArea.getText();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As");
        

        int result = fileChooser.showSaveDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            
            if (!file.getName().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(text);
                System.out.println("Text saved to " + file.getAbsolutePath());
            }
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtApplication;
    // End of variables declaration//GEN-END:variables

    private void writeToTextArea() {
        txtApplication.append("EUROPEAN UNION ENTRY APPLICATION [" + apl.getId() + "]\n");
        txtApplication.append("======================================\n");
        txtApplication.append("Applicant:\t" + apl.getUser().getLastName() + ", " + apl.getUser().getFirstName() + "\n");
        txtApplication.append("JMBG:\t" + apl.getUser().getJmbg() + "\n");
        txtApplication.append("Passport:\t" + apl.getUser().getPassport() + " (SRB)\n");
        txtApplication.append("Born on:\t" + apl.getUser().getBirthday() + "\n");
        txtApplication.append("======================================\n");
        txtApplication.append("Appl. date:\t" + apl.getDateOfApplication() + "\n");
        txtApplication.append("Entry date:\t" + apl.getDateOfEntry()+ "\n");
        txtApplication.append("Duration:\t" + apl.getDuration()+ " DAYS\n");
        txtApplication.append("Visiting:\t" + apl.getItems() + "\n");
        txtApplication.append("Transport:\t" + apl.getTransport() + "\n");
        txtApplication.append("======================================\n");
         txtApplication.append("Status:\t"+apl.getStatus()+"\n");
        txtApplication.append("======================================\n");
        if(apl.getUser().getAge() < 18 || apl.getUser().getAge() > 70)
            txtApplication.append("APPLICATION IS FREE."+"\n");
        else
            txtApplication.append("THIS APPLICATION MUST BE PAID AT LEAST 2\n DAYS BEFORE ENTRY!"+"\n");
        
        
    }
}
