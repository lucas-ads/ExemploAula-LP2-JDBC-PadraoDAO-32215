package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	private static Connection conexao = null;
	
	public static Connection getConexao() throws SQLException {
		
		if(conexao==null) {		
			String dbURL = "jdbc:mysql://localhost:3306/tasksdb32215";
			String username = "root";
			String password = "";
		
			ConnectionFactory.conexao = DriverManager.getConnection(dbURL, username, password);
		}
		
		return conexao;
	}
}
