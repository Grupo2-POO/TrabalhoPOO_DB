package teste;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import classes.Cliente;
import classes.Pedido;
import classes.PedidoItens;
import classes.Produto;
import database.ClienteDB;
import database.DB;
import database.PedidoDB;
import database.PedidoItensDB;
import database.ProdutoDB;
import filemanager.FileManager;

public class PrincipalTeste {
		
	public static void main(String[] args) {
		
		boolean hasConnectedToDb = connectToDatabase();
			if (hasConnectedToDb) {
				System.out.println("Conectado com sucesso!");
				
//				Util.wait(10);
//				ArrayList<Cliente> todosClientes = ClienteDB.buscarTodosClientes();
//				for (int i = 0; i < todosClientes.size(); i++ ) {
//					Cliente cliente = todosClientes.get(i);
//					System.out.println(cliente.toString());
//				}
//				
//				ArrayList<Produto> todosProdutos = ProdutoDB.buscarTodosProdutos();
//				// Exemplo de for each loop:
//				// p é o apelido de cada item no ArrayList todosProdutos
//				// : pode ser lido como "em" 
//				for (Produto p : todosProdutos) {
//					System.out.println(p.toString());
//				}
//				
				ArrayList<Pedido> todosPedidos = PedidoDB.buscarTodosPedidos();
				for (Pedido p : todosPedidos) {
					System.out.println(p.toString());
				
				}
				
//				ArrayList<PedidoItens> relacaoPedidoItem = PedidoItensDB.relacaoPedidoItem();
//				for(PedidoItens relacao : relacaoPedidoItem) {
//					System.out.println(relacao.toString());
//				}
			}
			
	}
	
	
	public static boolean connectToDatabase() {
		
		try (var connection = DB.connect()) {
			
			System.out.println("Conectado ao PostgreSQL database com sucesso!");
			
			if (FileManager.confirmaExist()) {
				return true;
			}
			
			Statement statement = connection.createStatement();
			statement.executeUpdate(FileManager.readDBCreateFile());
			statement.executeUpdate(FileManager.readDBInsertFile());
			FileManager.createConfirmationFile();
			
			
		} catch (SQLException error) {
			System.err.println(error.getMessage());
		}
		
		return false;
	} 
}
