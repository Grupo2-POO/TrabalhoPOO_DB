package database;

import java.util.ArrayList;

import util.Util;

// essa é a interface que todos os ClasseDB vão ter

public interface CRUD <T> {
	
	public void adicionar(String[] valores);
	
	public ArrayList<T> buscarTodos();
	
	public default T buscarUmPor(String nomeAtributo, String valorAtributo, String nomeTabela) {
				
		String sql = String.format("SELECT * FROM %s WHERE %s='%s'", 
				nomeTabela,
				nomeAtributo,
				valorAtributo);
		
		if(nomeAtributo.equals("nome")) {
			sql = "select * from " + nomeTabela + " where nome ilike '%" + valorAtributo + "%'"; 
		}
		
		if(nomeAtributo.equals("idpedido")) {
			sql = "select * from public.pedido p "
					+ "join cliente cl on cl.idcliente = p.idcliente;";
		}
		
		if(nomeAtributo.equals("idcliente") && (nomeTabela.equals("cliente"))) {
			sql = "select * from public.cliente where idcliente = " + valorAtributo + ";";
		}
		
		return executarConsultaCompleta(sql);
		
	}
	
	public default ArrayList<T> buscarTodosPor(String nomeAtributo, String valorAtributo, String nomeTabela) {
		
		String sql = String.format("SELECT * FROM %s WHERE %s='%s'", 
				nomeTabela,
				nomeAtributo,
				valorAtributo);
		
		
		return executarConsultaCompletaDeTodos(sql);
		
	}
	
	public ArrayList<T> executarConsultaCompletaDeTodos(String sql);

	public T executarConsultaCompleta(String sql);
	
	public void deletar();
}
