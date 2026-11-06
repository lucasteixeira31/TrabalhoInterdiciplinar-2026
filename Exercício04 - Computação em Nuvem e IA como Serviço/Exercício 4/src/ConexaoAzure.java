
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConexaoAzure {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://ti2-postgres-04.postgres.database.azure.com:5432/postgres?sslmode=require";
        String usuario = "adminexercicio04";
        String senha = System.getenv("AZURE_POSTGRES_PASSWORD");

        if (senha == null || senha.isBlank()) {
            System.err.println(
                "Configure AZURE_POSTGRES_PASSWORD no Eclipse."
            );
            return;
        }

        try (Connection conexao =
                DriverManager.getConnection(url, usuario, senha);
             Statement comando = conexao.createStatement()) {

            System.out.println("Conexao com PostgreSQL Azure realizada!");

            // 1. Criar a tabela
            String criarTabela = """
                CREATE TABLE IF NOT EXISTS livros (
                    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    titulo VARCHAR(120) NOT NULL,
                    autor VARCHAR(100) NOT NULL,
                    ano_publicacao INTEGER NOT NULL
                )
                """;

            comando.executeUpdate(criarTabela);
            System.out.println("Tabela livros verificada/criada!");

            // 2. Inserir os livros sem duplicar registros
            String inserirLivros = """
                INSERT INTO livros (titulo, autor, ano_publicacao)
                SELECT dados.titulo, dados.autor, dados.ano_publicacao
                FROM (VALUES
                    ('Dom Casmurro', 'Machado de Assis', 1899),
                    ('O Cortico', 'Aluisio Azevedo', 1890),
                    ('Vidas Secas', 'Graciliano Ramos', 1938)
                ) AS dados(titulo, autor, ano_publicacao)
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM livros
                    WHERE titulo = dados.titulo
                      AND autor = dados.autor
                      AND ano_publicacao = dados.ano_publicacao
                )
                """;

            int inseridos = comando.executeUpdate(inserirLivros);
            System.out.println("Livros inseridos: " + inseridos);

            // 3. Consultar os registros
            String sql = """
                SELECT id, titulo, autor, ano_publicacao
                FROM livros
                ORDER BY id
                """;

            System.out.println("\nSELECT - LIVROS CADASTRADOS:");
            System.out.println("----------------------------------------");

            try (ResultSet resultado = comando.executeQuery(sql)) {
                while (resultado.next()) {

                    int id = resultado.getInt("id");
                    String titulo = resultado.getString("titulo");
                    String autor = resultado.getString("autor");
                    int ano = resultado.getInt("ano_publicacao");

                    System.out.println(
                        id + " | " + titulo + " | "
                        + autor + " | " + ano
                    );
                }
            }

            System.out.println("----------------------------------------");
            System.out.println("Consulta SQL executada com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
