package usuario;

import java.util.*;
import java.sql.*;

//Classe responsável pelo acesso aos dados da tabela usuario.
//Implementa as operações CRUD utilizando JDBC para realizar a comunicação entre a aplicação Java e o PostgreSQL.
public class UsuarioDAO {
	
	// Dados necessários para conexão com o PostgreSQL
	private String url = "jdbc:postgresql://localhost:5432/exercicio02";
	private String user = "postgres";
	private String pssw = System.getenv("DB_PASSWORD");
	
	//Abre uma conexão com o banco de dados PostgreSQL.
	public Connection conectar () {
		try {
	
			return DriverManager.getConnection(url, user, pssw);
			
		}catch (SQLException e) {
			
			System.out.println(e.getMessage());
			return null;
			
		}
		
	}
			//Busca todos os usuários cadastrados no banco de dados.
			//Cada registro encontrado no ResultSet é convertido em um objeto Usuario e adicionado à lista.
			public List<Usuario> listar (){
				
				List<Usuario> usuarios = new ArrayList<>();
				
				Connection con = conectar();
				
				String sql = "SELECT * FROM usuario";
					
				try {
					
					PreparedStatement stm = con.prepareStatement(sql);
					
					ResultSet rs = stm.executeQuery();
					
					while(rs.next()) {
						int id = rs.getInt("id");
						String nome = rs.getString("nome");
						String usuario = rs.getString("usuario");
						String email = rs.getString("email");
						String senha = rs.getString("senha");
						
						Usuario u = new Usuario(id, nome, usuario, email, senha);
						
						usuarios.add(u);
						}
					
				}catch(SQLException e) {
					
					System.out.println(e.getMessage());
					
					return null;
				}				
				return usuarios;
			}
			
			//Insere um novo usuário no banco de dados.
			//O identificador não é informado, pois é gerado automaticamente pelo PostgreSQL.
			public boolean inserir (Usuario u) {
				
				
				String sql = "INSERT INTO usuario ( nome, usuario, email, senha ) VALUES (?, ?, ?, ?)";
				Connection con = conectar();
				
				try {
					PreparedStatement stm = con.prepareStatement(sql);
					stm.setString(1, u.getNome());
					stm.setString(2, u.getUsuario());
					stm.setString(3, u.getEmail());
					stm.setString(4, u.getSenha());
					
					stm.executeUpdate();
					
					return true;
					
				}catch(SQLException e){
					System.out.println(e.getMessage());
					return false;		
				}				
			}
			
			//Exclui um usuário a partir de seu identificador.
			public boolean excluir(int id) {
				String sql = "DELETE FROM usuario WHERE id = ?";
				Connection con = conectar();
				
				try { 
					PreparedStatement stm = con.prepareStatement(sql);
					stm.setInt(1, id);
					int linhas = stm.executeUpdate();
					
					return linhas > 0;
					
				}catch(SQLException e){
					System.out.println(e.getMessage());
					
					return false;
				}
			}
			
			//Atualiza os dados de um usuário existente.
			//O id do objeto Usuario é utilizado para identificar qual registro deverá ser alterado no banco.
			public boolean atualizar(Usuario u) {
				String sql = "UPDATE usuario SET nome = ? , usuario = ? , email = ? , senha = ? WHERE id = ?";
				Connection con = conectar();
				
				try {
					PreparedStatement stm = con.prepareStatement(sql);
					
					stm.setString(1, u.getNome());	
					stm.setString(2, u.getUsuario());
					stm.setString(3, u.getEmail());
					stm.setString(4, u.getSenha());
					stm.setInt(5, u.getId());
					
					int linhas = stm.executeUpdate();
					
					return linhas > 0; 
					
				}catch(SQLException e) {
					System.out.println(e.getMessage());
					
					return false;
				}
			}
			
			
			
			
			
			
			
			
			
			
			
			
}
