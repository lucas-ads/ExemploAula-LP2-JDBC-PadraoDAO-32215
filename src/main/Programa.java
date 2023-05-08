package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.DaoTarefa;
import entidades.Tarefa;

public class Programa {
	
	private static DaoTarefa daoTarefa = new DaoTarefa();
	
	public static void main(String[] args) throws SQLException {
		
		Scanner scanner = new Scanner(System.in);
		int opcao;
		
		do{
			exibirMenu();
			
			opcao = Integer.parseInt(scanner.nextLine());
			
			switch (opcao) {
				case 1: {
					cadastrarTarefa();
					break;
				}
				case 2: {
					break;
				}
				case 3: {
					break;
				}
				case 4: {
					buscarTarefa();
					break;
				}
				case 5: {
					listarTarefas();
					break;
				}
				case 0: {
					System.out.println("Bye...");
					break;
				}
				default:
					System.out.println("Opção inválida!");
			}
		}while(opcao != 0);

	}
	
	public static void exibirMenu() {
		System.out.println("Digite:");
		System.out.println("1 - Para cadastrar tarefa");
		System.out.println("2 - Para editar tarefa");
		System.out.println("3 - Para excluir tarefa");
		System.out.println("4 - Para buscar tarefa");
		System.out.println("5 - Para listar tarefas");
		System.out.println("0 - Sair");
	}

	public static void cadastrarTarefa() throws SQLException {
		System.out.println("##### Cadastrando Tarefa #####");
		
		Tarefa t = new Tarefa();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Digite a tarefa:");
		t.setDescricao( scanner.nextLine() );
		
		System.out.println("Qual é a prioridade (de 1 a 5)?:");
		t.setPrioridade( Integer.parseInt(scanner.nextLine()) );
		
		daoTarefa.cadastrar(t);
		
		System.out.println("ID da nova tarefa: " + t.getId());
	}
	
	public static void buscarTarefa() throws SQLException {
		System.out.println("##### Buscando Tarefa por ID #####");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Digite o ID:");
		
		int id = Integer.parseInt(scanner.nextLine());
		
		Tarefa tarefa = daoTarefa.buscarTarefa(id);
		
		if(tarefa != null) {
			System.out.println("ID: " + tarefa.getId());
			System.out.println("Descrição: " + tarefa.getDescricao());
			System.out.println("Prioridade: " + tarefa.getPrioridade() + "\n");
		}else {
			System.out.println("Não existe uma tarefa com este ID...");
		}
	}
	
	public static void listarTarefas() throws SQLException {
		
		System.out.println("##### Exibindo Tarefas #####");
		
		List<Tarefa> tasks = daoTarefa.buscarTarefas();
		
		for(int i=0; i < tasks.size(); i++) {
			Tarefa t = tasks.get(i);
			
			System.out.println("ID: " + t.getId());
			System.out.println("Descrição: " + t.getDescricao());
			System.out.println("Prioridade: " + t.getPrioridade() + "\n");
		}
	}
	

}



/*Scanner scanner = new Scanner(System.in);

/*String sql = "delete from tarefas where prioridade = ?";

PreparedStatement ps = conexao.prepareStatement(sql);
ps.setInt(1, 5);

System.out.println("Affected rows:" + ps.executeUpdate());*/

/*String sql = "select * from tarefas";
PreparedStatement ps = conexao.prepareStatement(sql);

ResultSet result = ps.executeQuery();

while( result.next() ) {
	int id = result.getInt("id");
	String desc = result.getString("descricao");
	int prioridade = result.getInt("prioridade");
	
	System.out.println("Tarefa #"+id);
	System.out.println("Descrição: "+desc);
	System.out.println("Prioridade: "+prioridade+"\n");
}*/

/*String sql = "update tarefas set prioridade = ? where prioridade >= ?";

PreparedStatement ps = conexao.prepareStatement(sql);
ps.setInt(1, 5);
ps.setInt(2, 5);

System.out.println("Affected rows:" + ps.executeUpdate());
*/