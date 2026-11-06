package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;

public class UsuarioDAO {

    // Dados para conectar no banco 
    private String url = "jdbc:postgresql://localhost:5432/exercicio02";
    private String usuarioBanco = "postgres";
    private String senhaBanco = System.getenv("DB_PASSWORD");

    // Abre a conexão com o PostgreSQL
    public Connection conectar() {

        try {
            Connection conexao = DriverManager.getConnection(
                url,
                usuarioBanco,
                senhaBanco
            );

            return conexao;

        } catch (SQLException erro) {
            System.out.println("Erro ao conectar no banco: " + erro.getMessage());
            return null;
        }
    }

    // Insere um novo usuário na tabela
    public boolean inserir(Usuario usuario) {

        String sql =
            "INSERT INTO usuario (nome, usuario, email, senha) VALUES (?, ?, ?, ?)";

        Connection conexao = conectar();

        if (conexao == null) {
            return false;
        }

        try {

            PreparedStatement comando = conexao.prepareStatement(sql);

            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getUsuario());
            comando.setString(3, usuario.getEmail());
            comando.setString(4, usuario.getSenha());

            comando.executeUpdate();

            comando.close();
            conexao.close();

            return true;

        } catch (SQLException erro) {
            System.out.println("Erro ao inserir usuário: " + erro.getMessage());
            return false;
        }
    }

    // Busca um usuário pelo id
    public Usuario buscar(int id) {

        String sql = "SELECT * FROM usuario WHERE id = ?";

        Connection conexao = conectar();

        if (conexao == null) {
            return null;
        }

        try {

            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setInt(1, id);

            ResultSet resultado = comando.executeQuery();

            Usuario usuario = null;

            if (resultado.next()) {

                usuario = new Usuario(
                    resultado.getInt("id"),
                    resultado.getString("nome"),
                    resultado.getString("usuario"),
                    resultado.getString("email"),
                    resultado.getString("senha")
                );
            }

            resultado.close();
            comando.close();
            conexao.close();

            return usuario;

        } catch (SQLException erro) {
            System.out.println("Erro ao buscar usuário: " + erro.getMessage());
            return null;
        }
    }

    // Busca todos os usuários cadastrados
    public List<Usuario> listar() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuario ORDER BY id";

        Connection conexao = conectar();

        if (conexao == null) {
            return usuarios;
        }

        try {

            PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {

                Usuario usuario = new Usuario(
                    resultado.getInt("id"),
                    resultado.getString("nome"),
                    resultado.getString("usuario"),
                    resultado.getString("email"),
                    resultado.getString("senha")
                );

                usuarios.add(usuario);
            }

            resultado.close();
            comando.close();
            conexao.close();

        } catch (SQLException erro) {
            System.out.println("Erro ao listar usuários: " + erro.getMessage());
        }

        return usuarios;
    }

    // Atualiza os dados de um usuário existente
    public boolean atualizar(Usuario usuario) {

        String sql =
            "UPDATE usuario SET nome = ?, usuario = ?, email = ?, senha = ? WHERE id = ?";

        Connection conexao = conectar();

        if (conexao == null) {
            return false;
        }

        try {

            PreparedStatement comando = conexao.prepareStatement(sql);

            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getUsuario());
            comando.setString(3, usuario.getEmail());
            comando.setString(4, usuario.getSenha());
            comando.setInt(5, usuario.getId());

            int linhasAlteradas = comando.executeUpdate();

            comando.close();
            conexao.close();

            return linhasAlteradas > 0;

        } catch (SQLException erro) {
            System.out.println("Erro ao atualizar usuário: " + erro.getMessage());
            return false;
        }
    }

    // Exclui um usuário pelo id
    public boolean excluir(int id) {

        String sql = "DELETE FROM usuario WHERE id = ?";

        Connection conexao = conectar();

        if (conexao == null) {
            return false;
        }

        try {

            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setInt(1, id);

            int linhasAlteradas = comando.executeUpdate();

            comando.close();
            conexao.close();

            return linhasAlteradas > 0;

        } catch (SQLException erro) {
            System.out.println("Erro ao excluir usuário: " + erro.getMessage());
            return false;
        }
    }
}