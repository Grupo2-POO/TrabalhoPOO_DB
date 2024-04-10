package classes;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DB;

public class PedidoDB {
		
	public static ArrayList<Pedido> buscarTodosPedidos() {
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
		String sql = "SELECT * FROM pedido "
					+ "JOIN cliente on pedido.idcliente = cliente.idcliente;";
		try (Connection connection = DB.connect()) {
			Statement statement = connection.createStatement();
			var response = statement.executeQuery(sql);
			while (response.next()) {
				
				Cliente cliente = new Cliente(
						response.getInt("idcliente"),
						response.getString("nome"),
						response.getString("cpf"),
						response.getDate("dtnascimento"),
						response.getString("endereco"),
						response.getString("telefone")
						);
				
				Pedido pedido = new Pedido(
						response.getInt("idpedido"),
						cliente,
						response.getDate("dtemissao"),
						response.getDate("dtentrega"),
						response.getString("observacao")
						);
				
				pedidos.add(pedido);
						
			}
		} catch (SQLException error) {
			System.err.println(error.getMessage());
		}
		return pedidos;
		
	}
}