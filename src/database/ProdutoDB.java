package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import classes.Cliente;
import classes.Produto;
import util.Util;

public class ProdutoDB implements CRUD<Produto> {
	
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

	@Override
	public ArrayList<Produto> buscarTodos() {
		String sql = "SELECT * FROM produto";
		
		ArrayList<Produto> produtos = new ArrayList<Produto>();
	
		try (Connection connection = DB.connect()) {
			Statement statement = connection.createStatement();
			// O var eh utilizado para declarar uma variavel sem precisar explicitar o tipo, normalmente utilizado dentro de funcoes de forma encapsulada
			var response = statement.executeQuery(sql);
			// executar a resposta enquanto tiver cliente dentro do array, usando o next 
			while (response.next()) { // String nome, String cpf, Date data_nascimento, int idCliente, String endereco, String telefone
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

	
	@Override
	public Produto buscarUmPor(String nomeAtributo, String valorAtributo) {
		
		String nomeTabela = Util.removerUltimosCaracteres(this.getClass().getSimpleName());
		String sql = String.format("SELECT * FROM %s WHERE %s='%s'", 
				nomeTabela,
				nomeAtributo,
				valorAtributo);
		
		return executarConsultaCompletaProduto(sql);
	}
	
	@Override
	public void adicionar(String sql) {
		// TODO Auto-generated method stub
		
		// não precisa adicionar produtos ao database, certo?
		
	}


	@Override
	public void deletar() {
		// TODO Auto-generated method stub
		
		// não precisa deletar produtos do database,certo?
		
	}
}
