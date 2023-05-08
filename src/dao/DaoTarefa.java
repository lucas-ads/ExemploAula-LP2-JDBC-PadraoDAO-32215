package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.Tarefa;

public class DaoTarefa {
	
	public Connection getConexao() throws SQLException {
		String dbURL = "jdbc:mysql://localhost:3306/tasksdb32215";
		String username = "root";
		String password = "";
		
		Connection conexao = DriverManager.getConnection(dbURL, username, password);
		
		return conexao;
	}
	
	public void cadastrar(Tarefa tarefa) throws SQLException {
		
		Connection conexao = this.getConexao();
		
		String sql = "insert into tarefas (descricao, prioridade) values (? , ?)";
		
		PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, tarefa.getDescricao() );
		ps.setInt(2, tarefa.getPrioridade() );
		
		ps.executeUpdate();
		
		ResultSet result = ps.getGeneratedKeys();
		
		if(result.next())
			tarefa.setId( result.getInt(1) );
	}
	
	public Tarefa buscarTarefa(int idBuscado) throws SQLException{
		Connection conexao = this.getConexao();
		
		String sql = "select * from tarefas where id = ?";
		
		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setInt(1, idBuscado );
		
		ResultSet result = ps.executeQuery();
		
		if( result.next() ) {
			int id = result.getInt("id");
			String desc = result.getString("descricao");
			int prioridade = result.getInt("prioridade");
			
			return new Tarefa(id, desc, prioridade);
		}
		
		return null;
	}
	
	public List<Tarefa> buscarTarefas() throws SQLException{
		Connection conexao = this.getConexao();
		String sql = "select * from tarefas";
		PreparedStatement ps = conexao.prepareStatement(sql);
		ResultSet resultSet = ps.executeQuery();
		
		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		
		while( resultSet.next() ) {
			int id = resultSet.getInt("id");
			String desc = resultSet.getString("descricao");
			int prioridade = resultSet.getInt("prioridade");
			
			Tarefa tarefa = new Tarefa(id, desc, prioridade);
			
			tarefas.add(tarefa);
		}
		
		return tarefas;
	}
}
