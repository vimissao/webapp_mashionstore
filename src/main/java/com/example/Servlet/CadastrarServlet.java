package com.example.Servlet;

import com.example.Modelo.Cadastro;
import com.example.dao.CadastroDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;

@WebServlet("/Cadastrar")
public class CadastrarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String endereco = request.getParameter("endereco");
        String dataNascStr = request.getParameter("data_Nasc");
        String telefoneStr = request.getParameter("telefone").trim();
        String senhaStr = request.getParameter("senha").trim();

        // Debugging: imprimir os valores recebidos
        System.out.println("Telefone: '" + telefoneStr + "'");
        System.out.println("Senha: '" + senhaStr + "'");

        // Validação básica
        if (nome == null || email == null || endereco == null || dataNascStr == null || telefoneStr == null || senhaStr == null ||
                nome.isEmpty() || email.isEmpty() || endereco.isEmpty() || dataNascStr.isEmpty() || telefoneStr.isEmpty() || senhaStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Todos os campos são obrigatórios.");
            return;
        }

        // Verificação se telefone e senha são numéricos
        if (!telefoneStr.matches("\\d+") || !senhaStr.matches("\\d+")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Telefone e senha devem ser números.");
            return;
        }

        // Conversão e validação
        long telefone;
        long senha;
        Calendar dataNasc;

        try {
            telefone = Integer.parseInt(telefoneStr);
            senha = Integer.parseInt(senhaStr);
            // Formato de data: altere o padrão conforme necessário
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dataNascStr);

            dataNasc = Calendar.getInstance();
            dataNasc.setTime(date);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Erro de conversão: " + e.getMessage());
            return;
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Formato de data inválido. Use dd/MM/yyyy.");
            return;
        }

        Cadastro cadastro = new Cadastro();
        cadastro.setNome(nome);
        cadastro.setEmail(email);
        cadastro.setEndereco(endereco);
        cadastro.setDataNasc(dataNasc); // Agora usando o objeto Date
        cadastro.setTelefone(telefone);
        cadastro.setSenha(senha); // Considere hashear a senha

        try {
            CadastroDao cadastroDao = new CadastroDao();
            cadastroDao.adicionar(cadastro);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Cadastro realizado com sucesso!");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro ao cadastrar: " + e.getMessage());
        }
    }
}
