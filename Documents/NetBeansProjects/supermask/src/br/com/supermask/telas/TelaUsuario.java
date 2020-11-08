/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.supermask.telas;

/**
 *
 * @author Vera
 */
import java.sql.*;
import br.com.supermask.dal.ModuloConexao;
import java.security.MessageDigest;
import javax.swing.JOptionPane;

public class TelaUsuario extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaUsuario
     */
    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    // CRUD - READ
    private void consultar() {
        String sql = "select * from usuario where idusuario=?";
        String senha = txtUsuSenha.getText();
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuId.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtUsuNome.setText(rs.getString(2));
                txtUsuSenha.setText(rs.getString(3));
                txtUsuFoto.setText(rs.getString(4));
                cboUsuPerfil.setSelectedItem(rs.getString(5));
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado");
                // As linhas abaixo "limpam" os campos
                txtUsuNome.setText(null);
                txtUsuSenha.setText(null);
                txtUsuFoto.setText(null);
                cboUsuPerfil.setSelectedItem(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // CRUD - CREATE
    private void adicionar() {
        String sql = "insert into usuario (nomeusuario,senha,foto,perfil) values(?,?,?,?)";
        String senha = txtUsuSenha.getText();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String s = senha;
            md.update(s.getBytes());
            byte[] hash = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0").append(Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }

            }
            String criptografado = hexString.toString();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuNome.getText());
            pst.setString(2, criptografado);
            pst.setString(3, txtUsuFoto.getText());
            pst.setString(4, cboUsuPerfil.getSelectedItem().toString());
            // Validação dos campos obrigatórios
            if (txtUsuNome.getText().isEmpty() || txtUsuSenha.getText().isEmpty() || txtUsuFoto.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {

                // A linha abaixo é usada para confirmar a inserção de dados na tabela
                // A linha abaixo atualiza a tabela usuario com os dados do formulário
                int adicionado = pst.executeUpdate();
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuSenha.setText(null);
                    txtUsuFoto.setText(null);
                    cboUsuPerfil.setSelectedItem(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // CRUD - UPDATE
    private void alterar() {
        String sql = "update usuario set nomeusuario=?, senha=?, foto=?, perfil=? where idusuario=?";
        String senha = txtUsuSenha.getText();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String s = senha;
            md.update(s.getBytes());
            byte[] hash = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0").append(Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }

            }
            String criptografado = hexString.toString();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuNome.getText());
            pst.setString(2, criptografado);
            pst.setString(3, txtUsuFoto.getText());
            pst.setString(4, cboUsuPerfil.getSelectedItem().toString());
            pst.setString(5, txtUsuId.getText());
             // Validação dos campos obrigatórios
            if (txtUsuNome.getText().isEmpty() || txtUsuSenha.getText().isEmpty() || txtUsuFoto.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {

                // A linha abaixo é usada para confirmar a alteração de dados na tabela
                // A linha abaixo atualiza a tabela usuario com os dados do formulário
                int adicionado = pst.executeUpdate();
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Informações atualizadas com sucesso!");
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuSenha.setText(null);
                    txtUsuFoto.setText(null);
                    cboUsuPerfil.setSelectedItem(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // CRUD - DELETE
    private void delete() {
        // A estrutura abaixo confirma a remoção do usuário
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usuário","ALERTA!", JOptionPane.YES_NO_OPTION);
        if (confirma==JOptionPane.YES_OPTION) {
            String sql = "delete from usuario where idusuario=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuId.getText());
                int apagado = pst.executeUpdate();
                if (apagado>0) {
                    JOptionPane.showMessageDialog(null, "Usuário deletado com sucesso!");
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuSenha.setText(null);
                    txtUsuFoto.setText(null);
                    cboUsuPerfil.setSelectedItem(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        txtUsuNome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cboUsuPerfil = new javax.swing.JComboBox<>();
        txtUsuSenha = new javax.swing.JPasswordField();
        txtUsuFoto = new javax.swing.JTextField();
        btnUsuCreate = new javax.swing.JButton();
        btnUsuRead = new javax.swing.JButton();
        btnUsuUpdate = new javax.swing.JButton();
        btnUsuDelete = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtUsuId = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Painel - FUNCIONÁRIOS");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/supermask/icones/logomaskicon.png"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(560, 510));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("Usuário*");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 192, -1, -1));
        getContentPane().add(txtUsuNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 188, 149, -1));

        jLabel3.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("Senha*");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(296, 192, -1, -1));

        jLabel4.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("Foto*");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 229, -1, -1));

        jLabel5.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        jLabel5.setText("Perfil");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(306, 229, -1, -1));
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 151, 124, -1));

        cboUsuPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Usuario" }));
        cboUsuPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboUsuPerfilActionPerformed(evt);
            }
        });
        getContentPane().add(cboUsuPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(344, 225, 149, -1));
        getContentPane().add(txtUsuSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(344, 188, 149, -1));
        getContentPane().add(txtUsuFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 225, 149, -1));

        btnUsuCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/supermask/icones/create.png"))); // NOI18N
        btnUsuCreate.setToolTipText("Adicionar");
        btnUsuCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuCreate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuCreateActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsuCreate, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 307, -1, -1));

        btnUsuRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/supermask/icones/read.png"))); // NOI18N
        btnUsuRead.setToolTipText("Pesquisar");
        btnUsuRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuRead.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuReadActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsuRead, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 307, -1, -1));

        btnUsuUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/supermask/icones/update.png"))); // NOI18N
        btnUsuUpdate.setToolTipText("Atualizar");
        btnUsuUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuUpdate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuUpdateActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsuUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(296, 307, -1, -1));

        btnUsuDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/supermask/icones/delete.png"))); // NOI18N
        btnUsuDelete.setToolTipText("Deletar");
        btnUsuDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuDelete.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuDeleteActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsuDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(413, 307, -1, -1));

        jLabel7.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        jLabel7.setText("ID");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 154, -1, -1));
        getContentPane().add(txtUsuId, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 150, 149, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/supermask/icones/elisefundo.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-320, -2, -1, 670));

        setBounds(0, 0, 560, 510);
    }// </editor-fold>//GEN-END:initComponents

    private void cboUsuPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboUsuPerfilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboUsuPerfilActionPerformed

    private void btnUsuCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuCreateActionPerformed
        // Chamando o métodos adicionar.
        adicionar();
    }//GEN-LAST:event_btnUsuCreateActionPerformed

    private void btnUsuReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuReadActionPerformed
        // Chamando o método consultar.
        consultar();
    }//GEN-LAST:event_btnUsuReadActionPerformed

    private void btnUsuUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuUpdateActionPerformed
        // Chamando o método alterar.
        alterar();
    }//GEN-LAST:event_btnUsuUpdateActionPerformed

    private void btnUsuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuDeleteActionPerformed
        // Chamando o método delete.
        delete();
    }//GEN-LAST:event_btnUsuDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUsuCreate;
    private javax.swing.JButton btnUsuDelete;
    private javax.swing.JButton btnUsuRead;
    private javax.swing.JButton btnUsuUpdate;
    private javax.swing.JComboBox<String> cboUsuPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtUsuFoto;
    private javax.swing.JTextField txtUsuId;
    private javax.swing.JTextField txtUsuNome;
    private javax.swing.JPasswordField txtUsuSenha;
    // End of variables declaration//GEN-END:variables
}
