package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.Tarefa;
import entidades.Usuario;

public class DaoUsuario {
	
	public void cadastrar(Usuario usuario) throws SQLException {
		
		Connection conexao = ConnectionFactory.getConexao();
		
		String sql = "insert into usuarios (email, senha) values (? , SHA2(?, 256) )";
		
		PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, usuario.getEmail() );
		ps.setString(2, usuario.getSenha() );
		
		ps.executeUpdate();
		
		ResultSet result = ps.getGeneratedKeys();
		
		if(result.next())
			usuario.setId( result.getInt(1) );
	}
	
	public boolean atualizarDados(Usuario usuario) throws SQLException{
		Connection conexao = ConnectionFactory.getConexao();
		
		String sql = "update usuarios set email = ? where id = ?";
		
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setString(1, usuario.getEmail() );
		ps.setInt(2, usuario.getId());
		
		if(ps.executeUpdate()==1)
			return true;
		
		return false;
	}
	
	public boolean atualizarSenha(Usuario usuario) throws SQLException{
		Connection conexao = ConnectionFactory.getConexao();
		
		String sql = "update usuarios set senha = SHA2(?, 256) where id = ?";
		
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setString(1, usuario.getSenha() );
		ps.setInt(2, usuario.getId());
		
		if(ps.executeUpdate()==1)
			return true;
		
		return false;
	}
	
	public int excluir(int id) throws SQLException{
		Connection conexao = ConnectionFactory.getConexao();
		
		String sql = "delete from usuarios where id = ?";
		
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setInt(1, id);
		
		int excluidos=0;
		
		try {
			excluidos = ps.executeUpdate();
		}catch(SQLException e) {
			return -1;
		}
		
		if(excluidos==1)
			return 1;
		
		return 0;
	}
	
	public Usuario buscarPorId(int idBuscado) throws SQLException{
		Connection conexao = ConnectionFactory.getConexao();
		
		String sql = "select * from usuarios where id = ?";
		
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setInt(1, idBuscado);
		
		ResultSet result = ps.executeQuery();
		
		if( result.next() ) {
			int id = result.getInt("id");
			String email = result.getString("email");
			String senha = result.getString("senha");
			
			return new Usuario(id, email, senha);
		}
		
		return null;
	}
	
	public Usuario buscarPorEmail(String emailBuscado) throws SQLException{
		Connection conexao = ConnectionFactory.getConexao();
		
		String sql = "select * from usuarios where email = ?";
		
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setString(1, emailBuscado);
		
		ResultSet result = ps.executeQuery();
		
		if( result.next() ) {
			int id = result.getInt("id");
			String email = result.getString("email");
			String senha = result.getString("senha");
			
			return new Usuario(id, email, senha);
		}
		
		return null;
	}
	
	public List<Usuario> buscarUsuarios() throws SQLException{
		Connection conexao = ConnectionFactory.getConexao();
		String sql = "select * from usuarios";
		PreparedStatement ps = conexao.prepareStatement(sql);
		ResultSet resultSet = ps.executeQuery();
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		while( resultSet.next() ) {
			int id = resultSet.getInt("id");
			String email = resultSet.getString("email");
			String senha = resultSet.getString("senha");
			
			Usuario usuario = new Usuario(id, email, senha);
			
			usuarios.add(usuario);
		}
		
		return usuarios;
	}
}
