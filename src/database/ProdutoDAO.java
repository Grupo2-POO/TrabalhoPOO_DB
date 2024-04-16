package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import classes.Cliente;
import classes.Produto;
import util.Util;

public class ProdutoDAO implements CRUD<Produto> {
	
	@Override
	public ArrayList<Produto> buscarTodos() {
		String sql = "SELECT * FROM produto";
		
		ArrayList<Produto> produtos = new ArrayList<Produto>();
	
		try (Connection connection = DAO.connect()) {
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
	public Produto executarConsultaCompleta(String sql) {

		try (Connection connection = DAO.connect()) {
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
	public void adicionar(String[] valores) {
		// TODO Auto-generated method stub
		
		// não precisa adicionar produtos ao database, certo?
		
	}
	
	@Override
	public void deletar() {
		// TODO Auto-generated method stub
		
		// não precisa deletar produtos do database,certo?
		
	}
	
	public void atualizarQuantidade(String idProduto, String qtProduto) {
		
		String sqlAlteracao = String.format(
				"update produto set quantidade = %s where idproduto = %s;"
				,qtProduto, idProduto);
		
		try(var conn = DAO.connect()){
			Statement statement = conn.createStatement();
			statement.executeUpdate(sqlAlteracao);
		} catch(SQLException e) {
			System.err.println(e);
		}
	}

	@Override
	public ArrayList<Produto> executarConsultaCompletaDeTodos(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
