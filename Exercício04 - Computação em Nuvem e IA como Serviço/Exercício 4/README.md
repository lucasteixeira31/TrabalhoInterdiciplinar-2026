# Exercício 4 — TI2 | Projeto Eclipse

Projeto Eclipse `Exercicio04`, configurado para Java 17. Esta pasta é o pacote de entrega exigido para o Exercício 4.

## Importar no Eclipse

1. Se o projeto antigo ainda aparecer no workspace como quebrado, selecione `Exercicio04` e use **Delete**. Desmarque **Delete project contents on disk**.
2. Use **File → Import → General → Existing Projects into Workspace**.
3. Selecione esta pasta `Exercício 4` como root directory, marque `Exercicio04` e conclua a importação.
4. Confirme que o Eclipse tem um JDK 17 configurado. O `.classpath` usa `JavaSE-17`.

O projeto utiliza o driver PostgreSQL JDBC 42.7.14 armazenado em `lib/postgresql-42.7.14.jar`; o classpath é relativo ao projeto.

## Produto 1 — Conta no Azure

A captura da conta autenticada está em `evidencias/produto1_conta_azure.png`.

## Produto 2 — PostgreSQL no Azure

O script `banco.sql` cria a tabela `livros`, insere os registros e executa o SELECT. As evidências do recurso e do resultado estão em `evidencias/produto2_servidor_azure.png` e `evidencias/produto2_select_postgresql.png`.

Para executar `ConexaoAzure`, configure a variável de ambiente `AZURE_POSTGRES_PASSWORD` na configuração local de execução do Eclipse. O endpoint e usuário usados estão no código; não coloque a senha em arquivos versionados.

## Produto 3 — Azure AI Language, análise de sentimentos

Execute `AnaliseSentimentosAzure` como aplicação Java. Configure as variáveis de ambiente `AZURE_LANGUAGE_ENDPOINT` e `AZURE_LANGUAGE_KEY` na configuração local de execução do Eclipse. Pode-se informar o texto de entrada em **Arguments → Program arguments**; sem argumento, a aplicação usa uma frase de exemplo.

As evidências do recurso e da chamada estão em `evidencias/produto3_recurso_azure.png` e `evidencias/produto3_execucao_java.png`.

Não versione arquivos `.launch`, `.env` ou qualquer arquivo que contenha credenciais.
