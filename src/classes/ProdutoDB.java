package classes;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DB;

public class ProdutoDB {
	
	public static ArrayList<Produto> buscarTodosProdutos() {
		ArrayList<Produto> produtos = new ArrayList<Produto>();
		String sql = "SELECT * FROM produto";
		
		try (Connection connection = DB.connect()) {
			Statement statement = connection.createStatement();
			var response = statement.executeQuery(sql);
			while (response.next()) {
				Produto produto = new Produto(
						response.getInt("idproduto"),
						response.getDouble("vlcusto"),
						response.getDouble("vlvenda"),
						response.getInt("quantidade"),
						response.getString("descricao"),
						response.getString("categoria")
						);
			
				produtos.add(produto);
			}
		} catch (SQLException error) {
			System.err.println(error.getMessage());
		}
				
		return produtos;
	}
}
