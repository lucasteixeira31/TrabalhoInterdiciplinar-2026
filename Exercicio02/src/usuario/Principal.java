package usuario;

import java.sql.*;
import java.util.*;

public class Principal {
		public static void main( String[] args) {
			UsuarioDAO dao = new UsuarioDAO();		
			Scanner entrada = new Scanner(System.in);
			
			boolean sair = false;
			
			do {
				System.out.println("Digite um numero para continuar:");
				System.out.println("1 - cadastrar novo usuario");
				System.out.println("2 - alterar dados de usuario existente");
				System.out.println("3 - excluir usuario");
				System.out.println("4 - listar usuarios");
				System.out.println("5 - sair");
	
	
				int n = Integer.parseInt(entrada.nextLine());

				
				if( n == 1) {
				
					System.out.println("Nome: ");
					String nome = entrada.nextLine();
				
					System.out.println("Usuario: ");
					String usuario = entrada.nextLine();
				
					System.out.println("Email: ");
					String email = entrada.nextLine();
				
					System.out.println("Senha: ");
					String senha = entrada.nextLine();
				
					Usuario novo = new Usuario(nome, usuario, email, senha);
				
					boolean inseriu = dao.inserir(novo);
						
					if (inseriu){
						System.out.println("Sucesso!");
					}else {
						System.out.println("Falha ao inserir");
					}
				}else if (n==2) {
					System.out.println("Digite o id do usuario que deseja editar");
					
					int id = Integer.parseInt(entrada.nextLine());
					
					System.out.println("Novo nome: ");
					String nome = entrada.nextLine();
				
					System.out.println("Novo usuario: ");
					String usuario = entrada.nextLine();
				
					System.out.println("Novo email: ");
					String email = entrada.nextLine();
				
					System.out.println("Nova senha: ");
					String senha = entrada.nextLine();
					
					Usuario alterado = new Usuario(id, nome, usuario, email, senha);
					boolean atualizou = dao.atualizar(alterado);
					
					if(atualizou) {
						System.out.println("usuario atualizado com sucesso");
					
					}else {
						System.out.println("falha ao atualizar usuario");
					}
					
				} else if(n == 3) {
					System.out.println("digite o id que deseja excluir:" );
					
					int del = Integer.parseInt(entrada.nextLine());
					boolean excluiu = dao.excluir(del);
					
					if(excluiu) {
						System.out.println("usuario excluido com sucesso");
					
					}else {
						System.out.println("falha ao excluir usuario");
					}
					
				}else if (n == 4) {
				
					List<Usuario> usuarios = dao.listar();
	
					if(usuarios == null) {
						System.out.println("Erro ao buscar usuarios");
					}else {
						for (Usuario u : usuarios) {
							System.out.println(u.getId() + " | " + u.getNome() + " | " + u.getUsuario() + " | " + u.getEmail());
						
						}
					}
					}else if (n == 5) {
						sair = true;	
					}else {
						System.out.println("opcao invalida");
					}
		
			}while (!sair);
		
		}
}
