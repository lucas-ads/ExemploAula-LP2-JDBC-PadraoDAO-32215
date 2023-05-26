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

public class DaoTarefa {
	
	public void cadastrar(Tarefa tarefa) throws SQLException {
		
		Connection conexao = ConnectionFactory.getConexao();
		
		String sql = "insert into tarefas (descricao, prioridade, usuario_id) values (? , ? , ?)";
		
		PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, tarefa.getDescricao() );
		ps.setInt(2, tarefa.getPrioridade() );
		ps.setInt(3, tarefa.getUsuario().getId() );
		
		ps.executeUpdate();
		
		ResultSet result = ps.getGeneratedKeys();
		
		if(result.next())
			tarefa.setId( result.getInt(1) );
	}
	
	public boolean atualizar(Tarefa tarefa) throws SQLException{
		Connection conexao = ConnectionFactory.getConexao();
		
		String sql = "update tarefas set descricao = ?, prioridade = ? where id = ?";
		
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setString(1, tarefa.getDescricao() );
		ps.setInt(2, tarefa.getPrioridade() );
		ps.setInt(3, tarefa.getId());
		
		if(ps.executeUpdate()==1)
			return true;
		
		return false;
	}
	
	public int excluirTarefa(int id) throws SQLException{
		Connection conexao = ConnectionFactory.getConexao();
		
		String sql = "delete from tarefas where id = ?";
		
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
	
	public Tarefa buscarTarefa(int idBuscado) throws SQLException{
		Connection conexao = ConnectionFactory.getConexao();
		
		String sql = "select * from tarefas where id = ?";
		
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setInt(1, idBuscado );
		
		ResultSet result = ps.executeQuery();
		
		if( result.next() ) {
			int id = result.getInt("id");
			String desc = result.getString("descricao");
			int prioridade = result.getInt("prioridade");
			int usuarioId = result.getInt("usuario_id");
			
			DaoUsuario du = new DaoUsuario();
			
			Usuario u = du.buscarPorId(usuarioId);
			
			return new Tarefa(id, desc, prioridade, u);
		}
		
		return null;
	}
	
	public List<Tarefa> buscarTarefas() throws SQLException{
		Connection conexao = ConnectionFactory.getConexao();
		String sql = "select * from tarefas";
		PreparedStatement ps = conexao.prepareStatement(sql);
		ResultSet resultSet = ps.executeQuery();
		
		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		
		while( resultSet.next() ) {
			int id = resultSet.getInt("id");
			String desc = resultSet.getString("descricao");
			int prioridade = resultSet.getInt("prioridade");
			int usuarioId = resultSet.getInt("usuario_id");
			
			DaoUsuario du = new DaoUsuario();
			
			Usuario u = du.buscarPorId(usuarioId);
			
			Tarefa tarefa = new Tarefa(id, desc, prioridade, u);
			
			tarefas.add(tarefa);
		}
		
		return tarefas;
	}
}
