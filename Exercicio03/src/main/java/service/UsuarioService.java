package service;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        usuarioDAO = new UsuarioDAO();
    }

    // Lê o arquivo HTML usado com base da página
    private String carregarPagina() {

        String pagina = "";

        try {

            Scanner leitor = new Scanner(new File("form.html"), "UTF-8");

            while (leitor.hasNextLine()) {
                pagina += leitor.nextLine() + "\n";
            }

            leitor.close();

        } catch (Exception erro) {
            System.out.println("Erro ao carregar form.html: " + erro.getMessage());
        }

        return pagina;
    }

    // Monta o formulário para cadastrar ou atualizar um usuário
    private String montarFormulario(Usuario usuario, boolean editar) {

        String formulario = "";
        String acao;
        String titulo;
        String textoBotao;

        if (editar) {

            acao = "/usuario/atualizar/" + usuario.getId();
            titulo = "Atualizar usuário";
            textoBotao = "Atualizar";

        } else {

            acao = "/usuario/inserir";
            titulo = "Cadastrar usuário";
            textoBotao = "Cadastrar";
        }

        formulario += "<h2>" + titulo + "</h2>";

        formulario += "<form action=\"" + acao + "\" method=\"post\">";

        formulario += "<label>Nome:</label>";
        formulario += "<input type=\"text\" name=\"nome\" value=\""
                + usuario.getNome() + "\" required>";

        formulario += "<label>Usuário:</label>";
        formulario += "<input type=\"text\" name=\"usuario\" value=\""
                + usuario.getUsuario() + "\" required>";

        formulario += "<label>E-mail:</label>";
        formulario += "<input type=\"email\" name=\"email\" value=\""
                + usuario.getEmail() + "\" required>";

        formulario += "<label>Senha:</label>";
        formulario += "<input type=\"password\" name=\"senha\" value=\""
                + usuario.getSenha() + "\" required>";

        formulario += "<button type=\"submit\">" + textoBotao + "</button>";

        if (editar) {
            formulario += " <a href=\"/usuario/listar\">Cancelar</a>";
        }

        formulario += "</form>";

        return formulario;
    }

    // Monta a tabela com os usuários cadastraos no banco
    private String montarTabela() {

        List<Usuario> usuarios = usuarioDAO.listar();

        String tabela = "";

        tabela += "<table>";
        tabela += "<tr>";
        tabela += "<th>ID</th>";
        tabela += "<th>Nome</th>";
        tabela += "<th>Usuário</th>";
        tabela += "<th>E-mail</th>";
        tabela += "<th>Ações</th>";
        tabela += "</tr>";

        for (Usuario usuario : usuarios) {

            tabela += "<tr>";

            tabela += "<td>" + usuario.getId() + "</td>";
            tabela += "<td>" + usuario.getNome() + "</td>";
            tabela += "<td>" + usuario.getUsuario() + "</td>";
            tabela += "<td>" + usuario.getEmail() + "</td>";

            tabela += "<td>";
            tabela += "<a href=\"/usuario/" + usuario.getId() + "\">Detalhar</a> ";
            tabela += "<a href=\"/usuario/editar/" + usuario.getId() + "\">Editar</a> ";
            tabela += "<a href=\"/usuario/excluir/" + usuario.getId() + "\">Excluir</a>";
            tabela += "</td>";

            tabela += "</tr>";
        }

        tabela += "</table>";

        return tabela;
    }

    // Junta o formulário e a tabela na página HTML
    private String montarPagina(Usuario usuario, boolean editar, String mensagem) {

        String pagina = carregarPagina();

        String blocoMensagem = "";

        if (mensagem != null && !mensagem.isEmpty()) {
            blocoMensagem = "<p>" + mensagem + "</p>";
        }

        pagina = pagina.replace("<MENSAGEM>", blocoMensagem);

        pagina = pagina.replace(
            "<FORMULARIO-USUARIO>",
            montarFormulario(usuario, editar)
        );

        pagina = pagina.replace(
            "<LISTA-USUARIOS>",
            montarTabela()
        );

        return pagina;
    }

    // Mostra a página principal
    public Object listar(Request requisicao, Response resposta) {

        resposta.type("text/html; charset=UTF-8");

        Usuario usuario = new Usuario();

        return montarPagina(usuario, false, "");
    }

    // Recebe os dados do formulário e cadastra o usuário
    public Object inserir(Request requisicao, Response resposta) {

        String nome = requisicao.queryParams("nome");
        String nomeUsuario = requisicao.queryParams("usuario");
        String email = requisicao.queryParams("email");
        String senha = requisicao.queryParams("senha");

        Usuario usuario = new Usuario(
            nome,
            nomeUsuario,
            email,
            senha
        );

        boolean inseriu = usuarioDAO.inserir(usuario);

        resposta.type("text/html; charset=UTF-8");

        if (inseriu) {
            return montarPagina(
                new Usuario(),
                false,
                "Usuário cadastrado com sucesso."
            );
        }

        return montarPagina(
            new Usuario(),
            false,
            "Não foi possível cadastrar o usuário."
        );
    }

    // Mostra os dados de um usuário específico
    public Object detalhar(Request requisicao, Response resposta) {

        int id = Integer.parseInt(
            requisicao.params(":id")
        );

        Usuario usuario = usuarioDAO.buscar(id);

        resposta.type("text/html; charset=UTF-8");

        if (usuario == null) {

            resposta.status(404);

            return montarPagina(
                new Usuario(),
                false,
                "Usuário não encontrado."
            );
        }

        String pagina = carregarPagina();

        String detalhes = "";

        detalhes += "<h2>Dados do usuário</h2>";
        detalhes += "<p>ID: " + usuario.getId() + "</p>";
        detalhes += "<p>Nome: " + usuario.getNome() + "</p>";
        detalhes += "<p>Usuário: " + usuario.getUsuario() + "</p>";
        detalhes += "<p>E-mail: " + usuario.getEmail() + "</p>";

        detalhes += "<a href=\"/usuario/listar\">Voltar</a>";

        pagina = pagina.replace("<MENSAGEM>", "");
        pagina = pagina.replace("<FORMULARIO-USUARIO>", detalhes);
        pagina = pagina.replace("<LISTA-USUARIOS>", montarTabela());

        return pagina;
    }

    // Abre o formulrio com os dados atuais do usuário
    public Object editar(Request requisicao, Response resposta) {

        int id = Integer.parseInt(
            requisicao.params(":id")
        );

        Usuario usuario = usuarioDAO.buscar(id);

        resposta.type("text/html; charset=UTF-8");

        if (usuario == null) {

            resposta.status(404);

            return montarPagina(
                new Usuario(),
                false,
                "Usuário não encontrado."
            );
        }

        return montarPagina(
            usuario,
            true,
            ""
        );
    }

    // Recebe os dados alterados e aualiza o banco
    public Object atualizar(Request requisicao, Response resposta) {

        int id = Integer.parseInt(
            requisicao.params(":id")
        );

        String nome = requisicao.queryParams("nome");
        String nomeUsuario = requisicao.queryParams("usuario");
        String email = requisicao.queryParams("email");
        String senha = requisicao.queryParams("senha");

        Usuario usuario = new Usuario(
            id,
            nome,
            nomeUsuario,
            email,
            senha
        );

        boolean atualizou = usuarioDAO.atualizar(usuario);

        resposta.type("text/html; charset=UTF-8");

        if (atualizou) {

            return montarPagina(
                new Usuario(),
                false,
                "Usuário atualizado com sucesso."
            );
        }

        return montarPagina(
            new Usuario(),
            false,
            "Não foi possível atualizar o usuário."
        );
    }

    // Exclui o usuário escolhido
    public Object excluir(Request requisicao, Response resposta) {

        int id = Integer.parseInt(
            requisicao.params(":id")
        );

        boolean excluiu = usuarioDAO.excluir(id);

        resposta.type("text/html; charset=UTF-8");

        if (excluiu) {

            return montarPagina(
                new Usuario(),
                false,
                "Usuário excluído com sucesso."
            );
        }

        return montarPagina(
            new Usuario(),
            false,
            "Não foi possível excluir o usuário."
        );
    }
}