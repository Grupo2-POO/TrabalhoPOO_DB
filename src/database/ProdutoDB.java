package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import classes.Cliente;
import classes.Produto;

public class ProdutoDB {
	
	public static ArrayList<Produto> buscarTodosProdutos() {
		
		String sql = "SELECT * FROM produto";
		
		ArrayList<Produto> produtos = new ArrayList<Produto>();
		
		produtos.add(executarConsultaCompletaProduto(sql));
		
		return produtos;
	}
	
	public static Produto buscaProdutoPorCodigo(int codigo) {
	    
	    String sql = "SELECT * FROM produto WHERE idproduto='" + codigo + "'";
	    
	    return executarConsultaCompletaProduto(sql);
	}
	
	public static Produto buscaProdutoPorNome(String nome) {
	    
	    String sql = "SELECT * FROM produto WHERE descricao '%" + nome + "%'";
	    
	    return executarConsultaCompletaProduto(sql);
	}

	private static Produto executarConsultaCompletaProduto(String sql) {
		try (Connection connection = DB.connect()) {
	        Statement statement = connection.createStatement();
	        var response = statement.executeQuery(sql);
	        if (response.next()) {
	        	Produto produto = new Produto(
						response.getInt("idproduto"),
						response.getDouble("vlcusto"),
						response.getDouble("vlvenda"),
						response.getInt("quantidade"),
						response.getString("descricao"),
						response.getString("categoria")
						);
	        	
	        	return produto;
	        } else {
	        	return null;
	        }
	        
	    } catch (SQLException error) {
//	        System.err.println(error.getMessage());
	        return null;
	    }
	}
}
