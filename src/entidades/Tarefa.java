package entidades;

public class Tarefa {
	private int id;
	private String descricao;
	private int prioridade;
	private Usuario usuario;
	
	public Tarefa() {}
	
	public Tarefa(int id, String descricao, int prioridade, Usuario usuario) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.prioridade = prioridade;
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public int getPrioridade() {
		return prioridade;
	}
	
	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
