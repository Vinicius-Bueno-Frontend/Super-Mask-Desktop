/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.supermask.telas;

import java.sql.*;
import br.com.supermask.dal.ModuloConexao;
import java.security.MessageDigest;
import javax.swing.JOptionPane;
// Importando recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Vera
 */
public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void adicionarCliente() {
        String sql = "insert into cliente (nomecliente,cpf,sexo,idcontato,idendereco,idusuario) values(?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliCpf.getText());
            pst.setString(3, cboCliSexo.getSelectedItem().toString());
            pst.setString(4, txtIdContato.getText());
            pst.setString(5, txtIdEndereco.getText());
            pst.setString(6, txtIdUsuario.getText());
            if (txtCliNome.getText().isEmpty() || txtCliCpf.getText().isEmpty() || cboCliSexo.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {

                // A linha abaixo é usada para confirmar a inserção de dados na tabela
                // A linha abaixo atualiza a tabela usuario com os dados do formulário
                int adicionado = pst.executeUpdate();
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
                    txtCliNome.setText(null);
                    txtCliCpf.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void adicionarContato() {
        String sql = "insert into contato (telefone,email) values(?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliTelefone.getText());
            pst.setString(2, txtCliEmail.getText());
            if (txtCliTelefone.getText().isEmpty() || txtCliEmail.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {

                // A linha abaixo é usada para confirmar a inserção de dados na tabela
                // A linha abaixo atualiza a tabela usuario com os dados do formulário
                int adicionado = pst.executeUpdate();
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Contato cadastrado com sucesso!");
                    txtCliTelefone.setText(null);
                    txtCliEmail.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void adicionarEndereco() {
        String sql = "insert into endereco (tipo,logradouro,numero,complemento,bairro,cep) values(?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cboCliTipo.getSelectedItem().toString());
            pst.setString(2, txtCliLogradouro.getText());
            pst.setString(3, txtCliNumero.getText());
            pst.setString(4, txtCliComplemento.getText());
            pst.setString(5, txtCliBairro.getText());
            pst.setString(6, txtCliCep.getText());
            if (cboCliTipo.getSelectedItem().toString().isEmpty() || txtCliLogradouro.getText().isEmpty() || txtCliNumero.getText().isEmpty() || txtCliComplemento.getText().isEmpty() || txtCliBairro.getText().isEmpty() || txtCliCep.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {

                // A linha abaixo é usada para confirmar a inserção de dados na tabela
                // A linha abaixo atualiza a tabela usuario com os dados do formulário
                int adicionado = pst.executeUpdate();
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Endereço cadastrado com sucesso!");
                    txtCliLogradouro.setText(null);
                    txtCliNumero.setText(null);
                    txtCliComplemento.setText(null);
                    txtCliBairro.setText(null);
                    txtCliCep.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void adicionarUsuario() {
        String sql = "insert into usuario (nomeusuario,senha,foto) values(?,?,?)";
        String senha = txtCliSenha.getText();
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
            pst.setString(1, txtCliUsuario.getText());
            pst.setString(2, criptografado);
            pst.setString(3, txtCliFoto.getText());
            // Validação dos campos obrigatórios
            if (txtCliUsuario.getText().isEmpty() || txtCliSenha.getText().isEmpty() || txtCliFoto.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {

                // A linha abaixo é usada para confirmar a inserção de dados na tabela
                // A linha abaixo atualiza a tabela usuario com os dados do formulário
                int adicionado = pst.executeUpdate();
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                    txtCliUsuario.setText(null);
                    txtCliSenha.setText(null);
                    txtCliFoto.setText(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pegaridusuario() {
        String sql = "SELECT MAX(idusuario) FROM usuario";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtIdUsuario.setText(rs.getString(1));
            } else {
                JOptionPane.showMessageDialog(null, "ID do Usuário não encontrado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pegaridcontato() {
        String sql = "SELECT MAX(idcontato) FROM contato";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtIdContato.setText(rs.getString(1));
            } else {
                JOptionPane.showMessageDialog(null, "ID do Contato não encontrado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pegaridendereco() {
        String sql = "SELECT MAX(idendereco) FROM endereco";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtIdEndereco.setText(rs.getString(1));
            } else {
                JOptionPane.showMessageDialog(null, "ID do Endereço não encontrado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Método para pesquisar clientes pelo nome com filtro
    private void pesquisarCliente() {
        String sql = "select * from cliente where nomecliente like ?";
        try {
            pst = conexao.prepareStatement(sql);
            // Passando o conteúdo da caixa de pesquisa para o ?
            // ATENÇÃO ao "%" que é a continuação da String sql
            pst.setString(1, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();
            // Usando a biblioteca rs2xml.jar para preencher a tabela
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // Método para setar os campos do formulário com o conteúdo da tabela
    public void setarCamposCliente() {
        int setar = tblClientes.getSelectedRow();
        txtCliId.setText(tblClientes.getModel().getValueAt(setar,0).toString());
        txtCliNome.setText(tblClientes.getModel().getValueAt(setar,1).toString());
        txtCliCpf.setText(tblClientes.getModel().getValueAt(setar,2).toString());
        cboCliSexo.setSelectedItem(tblClientes.getModel().getValueAt(setar, 3).toString());
        txtIdContato.setText(tblClientes.getModel().getValueAt(setar,4).toString());
        txtIdEndereco.setText(tblClientes.getModel().getValueAt(setar,5).toString());
        txtIdUsuario.setText(tblClientes.getModel().getValueAt(setar,6).toString());
        // A linha abaixo desabilita o botão adicionar
        btnCliAdicionar.setEnabled(false);
    }
    
    private void alterarCliente() {
        String sql ="update cliente set nomecliente=?, cpf=?, sexo=? where idcliente=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliCpf.getText());
            pst.setString(3, cboCliSexo.getSelectedItem().toString());
            pst.setString(4, txtCliId.getText());
             // Validação dos campos obrigatórios
            if (txtCliNome.getText().isEmpty() || txtCliCpf.getText().isEmpty() || cboCliSexo.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {

                // A linha abaixo é usada para confirmar a alteração de dados na tabela
                // A linha abaixo atualiza a tabela usuario com os dados do formulário
                int adicionado = pst.executeUpdate();
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Informações atualizadas com sucesso!");
                    txtCliNome.setText(null);
                    txtCliCpf.setText(null);
                    txtCliId.setText(null);
                    btnCliAdicionar.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void deletarCliente() {
        // A estrutura abaixo confirma a remoção do cliente.
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este cliente?","ALERTA!", JOptionPane.YES_NO_OPTION);
        if (confirma==JOptionPane.YES_OPTION) {
            String sql = "delete from cliente where idcliente=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCliId.getText());
                int apagado = pst.executeUpdate();
                if (apagado>0) {
                    JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso!");
                    txtCliId.setText(null);
                    txtCliNome.setText(null);
                    txtCliCpf.setText(null);
                    cboCliSexo.setSelectedItem(null);
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

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jSlider1 = new javax.swing.JSlider();
        jLabel23 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCliUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCliNumero = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCliTelefone = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCliEmail = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cboCliSexo = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCliLogradouro = new javax.swing.JTextField();
        txtCliCpf = new javax.swing.JTextField();
        cboCliTipo = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCliComplemento = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCliBairro = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtCliCep = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtCliNome = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtCliSenha = new javax.swing.JPasswordField();
        txtCliPesquisar = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        btnCliAdicionar = new javax.swing.JButton();
        btnCliAtualizar = new javax.swing.JButton();
        btnCliDeletar = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        txtCliFoto = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtIdContato = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtIdUsuario = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtIdEndereco = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        txtCliId = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

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

        jScrollPane3.setViewportView(jEditorPane1);

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/supermask/icones/dianafundo.png"))); // NOI18N

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Painel - CLIENTES");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/supermask/icones/logomaskicon.png"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(560, 485));

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel1.setText("ACESSO");

        jLabel2.setText("Usuário*");

        jLabel3.setText("Senha*");

        jLabel4.setText("Tipo*");

        jLabel5.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel5.setText("CONTATO");

        jLabel6.setText("Tel*");

        txtCliTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliTelefoneActionPerformed(evt);
            }
        });

        jLabel7.setText("E-Mail*");

        jLabel8.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel8.setText("ENDEREÇO");

        cboCliSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F" }));
        cboCliSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCliSexoActionPerformed(evt);
            }
        });

        jLabel9.setText("Foto*");

        jLabel10.setText("Logradouro*");

        cboCliTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rua", "Avenida", "Praça", "Alameda" }));

        jLabel11.setText("Nº*");

        jLabel12.setText("Com*");

        jLabel13.setText("Bairro*");

        jLabel14.setText("CEP*");

        txtCliCep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliCepActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel15.setText("DADOS ESSENCIAIS");

        jLabel16.setText("Nome*");

        txtCliNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliNomeActionPerformed(evt);
            }
        });

        jLabel17.setText("CPF*");

        txtCliSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliSenhaActionPerformed(evt);
            }
        });

        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        jButton2.setText("Pesquisar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnCliAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/supermask/icones/create.png"))); // NOI18N
        btnCliAdicionar.setToolTipText("Adicionar");
        btnCliAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCliAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliAdicionarActionPerformed(evt);
            }
        });

        btnCliAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/supermask/icones/update.png"))); // NOI18N
        btnCliAtualizar.setToolTipText("Atualizar");
        btnCliAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliAtualizarActionPerformed(evt);
            }
        });

        btnCliDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/supermask/icones/delete.png"))); // NOI18N
        btnCliDeletar.setToolTipText("Deletar");
        btnCliDeletar.setPreferredSize(new java.awt.Dimension(70, 70));
        btnCliDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliDeletarActionPerformed(evt);
            }
        });

        jLabel18.setText("Sexo*");

        jLabel19.setText("ID*");

        txtIdContato.setEnabled(false);

        jLabel20.setText("ID*");

        txtIdUsuario.setEnabled(false);

        jLabel21.setText("ID*");

        txtIdEndereco.setEnabled(false);

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblClientes);

        jLabel22.setText("ID*");

        txtCliId.setEnabled(false);
        txtCliId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliIdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel20)
                        .addGap(6, 6, 6)
                        .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2)
                        .addGap(4, 4, 4)
                        .addComponent(txtCliUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3)
                        .addGap(4, 4, 4)
                        .addComponent(txtCliSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel9)
                        .addGap(4, 4, 4)
                        .addComponent(txtCliFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel19)
                        .addGap(4, 4, 4)
                        .addComponent(txtIdContato, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(txtCliTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel7)
                        .addGap(4, 4, 4)
                        .addComponent(txtCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel21)
                        .addGap(4, 4, 4)
                        .addComponent(txtIdEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel4)
                        .addGap(4, 4, 4)
                        .addComponent(cboCliTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel10)
                        .addGap(4, 4, 4)
                        .addComponent(txtCliLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCliNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel12)
                        .addGap(4, 4, 4)
                        .addComponent(txtCliComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel13)
                        .addGap(4, 4, 4)
                        .addComponent(txtCliBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel14)
                        .addGap(4, 4, 4)
                        .addComponent(txtCliCep, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel15))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(20, 20, 20)
                                .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(4, 4, 4)
                                .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(112, 112, 112)
                        .addComponent(btnCliAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCliAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnCliDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(txtCliCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cboCliSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2))
                .addGap(11, 11, 11)
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCliUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCliSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCliFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jLabel5)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCliTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jLabel8)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel21))
                    .addComponent(txtIdEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel4))
                    .addComponent(cboCliTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel10))
                    .addComponent(txtCliLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel11))
                    .addComponent(txtCliNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCliComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCliBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCliCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel15)
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel22))
                            .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel16))
                            .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel17)
                                .addGap(16, 16, 16)
                                .addComponent(jLabel18))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(txtCliCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(cboCliSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCliDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCliAtualizar)
                            .addComponent(btnCliAdicionar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        setBounds(0, 0, 560, 485);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtCliSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliSenhaActionPerformed

    private void btnCliAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliAdicionarActionPerformed
        // Chamando as funções "adicionarUsuario, adicionarContato, adicionarEndereco e adicionarCliente"
        adicionarUsuario();
        pegaridusuario();
        adicionarContato();
        pegaridcontato();
        adicionarEndereco();
        pegaridendereco();
        adicionarCliente();

    }//GEN-LAST:event_btnCliAdicionarActionPerformed

    private void txtCliTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliTelefoneActionPerformed

    private void txtCliNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliNomeActionPerformed

    private void cboCliSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCliSexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCliSexoActionPerformed

    private void txtCliCepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliCepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliCepActionPerformed

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // Chamando o método "pesquisarCliente"
        pesquisarCliente();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased
// Evento que será usado para setar os campos da tabela (clicando com o mouse)
    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // Chamando o método "setarCampos"
        setarCamposCliente();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnCliAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliAtualizarActionPerformed
        // Chamando os métodos de alteração
        alterarCliente();
    }//GEN-LAST:event_btnCliAtualizarActionPerformed

    private void txtCliIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliIdActionPerformed

    private void btnCliDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliDeletarActionPerformed
        // Chamando o método de remoção.
        deletarCliente();
    }//GEN-LAST:event_btnCliDeletarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCliAdicionar;
    private javax.swing.JButton btnCliAtualizar;
    private javax.swing.JButton btnCliDeletar;
    private javax.swing.JComboBox<String> cboCliSexo;
    private javax.swing.JComboBox<String> cboCliTipo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliBairro;
    private javax.swing.JTextField txtCliCep;
    private javax.swing.JTextField txtCliComplemento;
    private javax.swing.JTextField txtCliCpf;
    private javax.swing.JTextField txtCliEmail;
    private javax.swing.JTextField txtCliFoto;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliLogradouro;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtCliNumero;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JPasswordField txtCliSenha;
    private javax.swing.JTextField txtCliTelefone;
    private javax.swing.JTextField txtCliUsuario;
    private javax.swing.JTextField txtIdContato;
    private javax.swing.JTextField txtIdEndereco;
    private javax.swing.JTextField txtIdUsuario;
    // End of variables declaration//GEN-END:variables
}
