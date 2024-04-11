package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import classes.Cliente;
import util.Util;

public class ClienteDB implements CRUD<Cliente> {
	
	private static Cliente executarConsultaCompletaCliente(String sql) {
			Cliente cliente = new Cliente();
			try (Connection connection = DB.connect()) {
		        Statement statement = connection.createStatement();
		        var response = statement.executeQuery(sql);
		        if (response.next()) {
		            cliente = new Cliente(
		                response.getInt("idcliente"),
		                response.getString("nome"),
		                response.getString("cpf"),
		                response.getDate("dtnascimento"),
		                response.getString("endereco"),
		                response.getString("telefone")
		            );
		        } else {
		            return null;
		        }
		        
		    } catch (SQLException error) {
//		        System.err.println(error.getMessage());
		        return null;
		    }
			return cliente;
	}

	@Override
	public ArrayList<Cliente> buscarTodos() {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		String sql = "SELECT * FROM cliente";
		try (Connection connection = DB.connect()) {
			Statement statement = connection.createStatement();
			// O var eh utilizado para declarar uma variavel sem precisar explicitar o tipo, normalmente utilizado dentro de funcoes de forma encapsulada
			var response = statement.executeQuery(sql);
			// executar a resposta enquanto tiver cliente dentro do array, usando o next 
			while (response.next()) { // String nome, String cpf, Date data_nascimento, int idCliente, String endereco, String telefone
				Cliente cliente = new Cliente(
						response.getInt("idcliente"),
						response.getString("nome"),
						response.getString("cpf"),
						response.getDate("dtnascimento"),
						response.getString("endereco"),
						response.getString("telefone")
						);
				clientes.add(cliente);
				
			}
			
		} catch (SQLException error) {
			System.err.println(error.getMessage());
		}
		return clientes;
	}

	
	@Override
	public Cliente buscarUmPor(String nomeAtributo, String valorAtributo) {
		
		String nomeTabela = Util.removerUltimosCaracteres(this.getClass().getSimpleName());
		
		String sql = String.format("SELECT * FROM %s WHERE %s='%s'", 
				nomeTabela,
				nomeAtributo,
				valorAtributo);
		return executarConsultaCompletaCliente(sql);
		
	}

	@Override
	public void deletar() {
		// Não precisa deletar clientes
		
	}


	@Override
	public void adicionar(String sql) {
		// TODO Auto-generated method stub
		
	}


	
	

}
