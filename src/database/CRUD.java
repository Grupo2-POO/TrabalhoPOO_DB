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
		
		return executarConsultaCompleta(sql);
		
	}
	
	public T executarConsultaCompleta(String sql);
	
	public void deletar();
}
