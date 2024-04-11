package database;

import java.util.ArrayList;

// essa é a interface que todos os ClasseDB vão ter


public interface CRUD <T> {
	// Create
	public void adicionar(String sql);
	
	public ArrayList<T> buscarTodos();
	
	public T buscarUmPor(String nomeAtributo, String valorAtributo);
	
	public void deletar();
}
