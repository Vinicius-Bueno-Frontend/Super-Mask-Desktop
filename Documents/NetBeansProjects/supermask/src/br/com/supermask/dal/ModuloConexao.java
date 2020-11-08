package br.com.supermask.dal;

import java.sql.*;

/**
 *
 * @author Vinicius Bueno Silva
 */
public class ModuloConexao {
    // Método de Conexão com o Banco

    public static Connection conector(){
        java.sql.Connection conexao = null;
        // a linha abaixo "chama" o driver
        String driver = "com.mysql.jdbc.Driver";
        // Armazenando informações referente ao banco
        String url="jdbc:mysql://localhost:3306/themasked";
        String user="root";
        String password = "";
        // Estabelecendo a conexão com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            // a linha abaixo serve para esclarecer o erro
            System.out.println(e);
            return null;
        }
    }
}