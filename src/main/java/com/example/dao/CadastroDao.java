package com.example.dao;

import com.example.Conexao.Conexao;
import com.example.Modelo.Cadastro;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CadastroDao {
    private final Connection connection;

    public CadastroDao() throws SQLException {
        Conexao cf = new Conexao();
        this.connection = cf.getConnection();
        criarTabela(); // Chama o método para criar a tabela ao instanciar a classe
        testarConexao();
    }

    public CadastroDao(Connection connection) {
        this.connection = connection;
        criarTabela(); // Chama o método para criar a tabela ao instanciar a classetestarConexao
        testarConexao();

    }

    private void criarTabela() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Cadastro (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nome VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL,
                endereco VARCHAR(255),
                data_Nasc DATE,
                telefone INT,
                senha INT
            );
        """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela 'Cadastro' criada (se não existia).");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabela 'Cadastro': " + e.getMessage(), e);
        }
    }

    public void adicionar(Cadastro cadastro) {
        String sql = "INSERT INTO Cadastro (nome, email, endereco, data_Nasc, telefone, senha) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cadastro.getNome());
            stmt.setString(2, cadastro.getEmail());
            stmt.setString(3, cadastro.getEndereco());
            stmt.setDate(4, new Date(cadastro.getDataNasc().getTimeInMillis())); // Certifique-se que Getdata_Nasc() retorna Calendar
            stmt.setLong(5, cadastro.getTelefone());
            stmt.setLong(6, cadastro.getSenha());

            stmt.execute();
            System.out.println("Cadastro adicionado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar cadastro: " + e.getMessage(), e);
        }
    }
    public void testarConexao() {
        try {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Conexão bem-sucedida!");
            } else {
                System.out.println("Falha na conexão.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao testar conexão: " + e.getMessage());
        }
    }

}
