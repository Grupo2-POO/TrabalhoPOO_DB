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
		
		if(nomeAtributo.equals("idpedidoitens")) {
			sql = "select * from public.pedidoitens pi "
					+ "join pedido pe on pe.idpedidoitens = pi.idpedidoitens "
					+ "join cliente cl on cl.idcliente = pi.idcliente "
					+ "join produto pr on pr.idproduto = pi.idproduto;";
		}
		
		return executarConsultaCompleta(sql);
		
	}
	
	public T executarConsultaCompleta(String sql);
	
	public void deletar();
}
