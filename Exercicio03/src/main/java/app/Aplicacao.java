package app;

import static spark.Spark.*;

import service.UsuarioService;

public class Aplicacao {

    private static UsuarioService usuarioService = new UsuarioService();

    public static void main(String[] args) {

        // Define a porta em que aaplicação será executada
        port(6789);

        // Define a pasta dos arquivos públicos da aplicação
        staticFiles.location("/public");

        // Abre a página principal com a lista de usuários
        get("/", (requisicao, resposta) -> {
            resposta.redirect("/usuario/listar");
            return null;
        });

        // Mostra o formulrio e os usuários cadastrados
        get("/usuario/listar",
            (requisicao, resposta) ->
                usuarioService.listar(requisicao, resposta)
        );

        // Recebe os dados do formulário para cadastrar um usuári
        post("/usuario/inserir",
            (requisicao, resposta) ->
                usuarioService.inserir(requisicao, resposta)
        );

        // Mostra os dados de um usuário
        get("/usuario/:id",
            (requisicao, resposta) ->
                usuarioService.detalhar(requisicao, resposta)
        );

        // Abre o formulário para editar um usuário
        get("/usuario/editar/:id",
            (requisicao, resposta) ->
                usuarioService.editar(requisicao, resposta)
        );

        // Recebe os dados alterados do formulário
        post("/usuario/atualizar/:id",
            (requisicao, resposta) ->
                usuarioService.atualizar(requisicao, resposta)
        );

        // Exclui um usuário
        get("/usuario/excluir/:id",
            (requisicao, resposta) ->
                usuarioService.excluir(requisicao, resposta)
        );
    }
}