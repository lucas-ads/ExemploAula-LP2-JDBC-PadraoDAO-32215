package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.DaoTarefa;
import dao.DaoUsuario;
import entidades.Tarefa;
import entidades.Usuario;

public class Programa {
	
	private static DaoTarefa daoTarefa = new DaoTarefa();
	private static DaoUsuario daoUsuario = new DaoUsuario();	
	
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
					atualizarTarefa();
					break;
				}
				case 3: {
					excluirTarefa();
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
				case 6: {
					cadastrarUsuario();
					break;
				}
				case 7: {
					atualizarDadosUsuario();
					break;
				}
				case 8: {
					atualizarSenhaUsuario();
					break;
				}
				case 9: {
					excluirUsuario();
					break;
				}
				case 10: {
					buscarUsuarioPorId();
					break;
				}
				case 11: {
					buscarUsuarioPorEmail();
					break;
				}
				case 12: {
					listarUsuarios();
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
		
		System.out.println("6 - Para cadastrar Usuario");
		System.out.println("7 - Para atualizar dados do Usuário");
		System.out.println("8 - Para atualizar senha do Usuário");
		System.out.println("9 - Para excluir Usuario");
		System.out.println("10 - Para buscar Usuario por ID");
		System.out.println("11 - Para buscar Usuario por Email");
		System.out.println("12 - Para listar Usuarios");
		
		
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
	
	public static void atualizarTarefa() throws SQLException{
		System.out.println("##### Atualização de Tarefa #####");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Digite o ID:");
		
		int id = Integer.parseInt(scanner.nextLine());
		
		Tarefa t = daoTarefa.buscarTarefa(id);
		
		if(t != null) {
			System.out.println("Descrição atual: " + t.getDescricao());
			System.out.println("Digite uma nova descrição ou pressione enter: ");
			
			String desc = scanner.nextLine();
			
			if(desc.length() > 0) {
				t.setDescricao(desc);
			}
			
			//############
			
			System.out.println("Prioridade atual: " + t.getPrioridade());
			System.out.println("Digite uma nova prioridade ou pressione enter: ");
			
			String prioridade = scanner.nextLine();
			
			if(prioridade.length() > 0) {
				t.setPrioridade( Integer.parseInt(prioridade) );
			}
			
			if(daoTarefa.atualizar(t)) {
				System.out.println("Tarefa atualizada com sucesso!");
			}else {
				System.out.println("Erro ao atualizar...");
			}
		}else {
			System.out.println("Tarefa não existe...");
		}
	}
	
	public static void excluirTarefa() throws SQLException {
		System.out.println("##### Exclusão de Tarefa #####");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Digite o ID:");
		
		int id = Integer.parseInt(scanner.nextLine());
		
		int retorno=daoTarefa.excluirTarefa(id);
		
		if(retorno == 1) {
			System.out.println("Tarefa excluída!");
		}else if(retorno == 0) {
			System.out.println("Tarefa não existe...");
		}else {
			System.out.println("Erro ao excluir, tente novamente mais tarde...");
		}
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
	
	public static void cadastrarUsuario() throws SQLException{
		System.out.println("##### Cadastrando Usuário #####");
		
		Usuario usuario = new Usuario();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Digite o e-mail:");
		usuario.setEmail( scanner.nextLine() );
		
		System.out.println("Digite sua senha:");
		usuario.setSenha( scanner.nextLine() );
		
		daoUsuario.cadastrar(usuario);
		
		System.out.println("ID do novo usuário: " + usuario.getId());
	}

	public static void atualizarDadosUsuario() throws SQLException{
		System.out.println("##### Atualização de Usuário #####");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Digite o ID:");
		
		int id = Integer.parseInt(scanner.nextLine());
		
		Usuario u = daoUsuario.buscarPorId(id);
		
		if(u != null) {
			System.out.println("Email atual: " + u.getEmail());
			System.out.println("Digite um novo email: ");
			
			String email = scanner.nextLine();
			
			if(email.length() > 0) {
				u.setEmail(email);
				if(daoUsuario.atualizarDados(u))
					System.out.println("Usuário atualizado com sucesso!");
				else
					System.out.println("Erro ao atualizar!");
			}else {
				System.out.println("Operação cancelada...");
			}
		}else {
			System.out.println("Usuário não existe...");
		}
	}

	public static void atualizarSenhaUsuario() throws SQLException{
		System.out.println("##### Atualização de Senha #####");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Digite o ID:");
		
		int id = Integer.parseInt(scanner.nextLine());
		
		Usuario u = daoUsuario.buscarPorId(id);
		
		if(u != null) {
			System.out.println("Digite uma nova senha ou pressione enter para cancelar: ");
			
			String senha = scanner.nextLine();
			
			if(senha.length() > 0) {
				u.setSenha(senha);
				if(daoUsuario.atualizarSenha(u)) {
					System.out.println("Usuário atualizado com sucesso!");
				}else {
					System.out.println("Erro ao atualizar...");
				}
			}else {
				System.out.println("Operação cancelada...");
			}
			
		}else {
			System.out.println("Usuário não existe...");
		}
	}
	
	public static void excluirUsuario() throws SQLException{
		System.out.println("##### Exclusão de Usuário #####");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Digite o ID:");
		
		int id = Integer.parseInt(scanner.nextLine());
		
		int retorno=daoUsuario.excluir(id);
		
		if(retorno == 1) {
			System.out.println("Usuário excluído!");
		}else if(retorno == 0) {
			System.out.println("Usuário não existe...");
		}else {
			System.out.println("Erro ao excluir, tente novamente mais tarde...");
		}
	}

	public static void buscarUsuarioPorId() throws SQLException{
		System.out.println("##### Buscando Usuário por ID #####");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Digite o ID:");
		
		int id = Integer.parseInt(scanner.nextLine());
		
		Usuario usuario = daoUsuario.buscarPorId(id);
		
		if(usuario != null) {
			System.out.println("ID: " + usuario.getId());
			System.out.println("Email: " + usuario.getEmail());
			System.out.println("Senha: " + usuario.getSenha() + "\n");
		}else {
			System.out.println("Não existe um usuário com este ID...");
		}
	}

	public static void buscarUsuarioPorEmail() throws SQLException{
		System.out.println("##### Buscando Usuário por Email #####");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Digite o E-mail:");
		
		String email = scanner.nextLine();
		
		Usuario usuario = daoUsuario.buscarPorEmail(email);
		
		if(usuario != null) {
			System.out.println("ID: " + usuario.getId());
			System.out.println("Email: " + usuario.getEmail());
			System.out.println("Senha: " + usuario.getSenha() + "\n");
		}else {
			System.out.println("Não existe um usuário com este ID...");
		}
	}

	public static void listarUsuarios() throws SQLException{
		System.out.println("##### Exibindo Usuários #####");
		
		List<Usuario> usuarios = daoUsuario.buscarUsuarios();
		
		for(int i=0; i < usuarios.size(); i++) {
			Usuario u = usuarios.get(i);
			
			System.out.println("ID: " + u.getId());
			System.out.println("Email: " + u.getEmail());
			System.out.println("Senha: " + u.getSenha() + "\n");
		}
	}

}
