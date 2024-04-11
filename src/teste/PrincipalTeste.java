package teste;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import classes.Cliente;
import classes.Pedido;
import classes.PedidoItens;
import database.ClienteDB;
import database.DB;
import database.PedidoDB;
import database.PedidoItensDB;
import database.ProdutoDB;
import filemanager.FileManager;
import menu.MenuBuscarProduto;

public class PrincipalTeste {
		
	public static void main(String[] args) {
		
		boolean hasConnectedToDb = connectToDatabase();
			if (hasConnectedToDb) {
				System.out.println("Conectado com sucesso!");
				
				// usando o CRUD, precisa instanciar os objetos (não são static mais)
				ClienteDB clienteDB = new ClienteDB();
				ProdutoDB produtoDB = new ProdutoDB();
				PedidoDB pedidoDB = new PedidoDB();
				PedidoItensDB pedidoItensDB = new PedidoItensDB();
				
//				Util.wait(10);

				ArrayList<Cliente> todosClientes = clienteDB.buscarTodos();
				for (int i = 0; i < todosClientes.size(); i++ ) {
					Cliente cliente = todosClientes.get(i);
					System.out.println(cliente.toString());
				}


//				ArrayList<Cliente> todosClientes = clienteDB.buscarTodos();
//				for (int i = 0; i < todosClientes.size(); i++ ) {
//					Cliente cliente = todosClientes.get(i);
//					System.out.println(cliente.toString());
//				}



//				
//				ArrayList<Produto> todosProdutos = produtoDB.buscarTodos();
//				// Exemplo de for each loop:
//				// p é o apelido de cada item no ArrayList todosProdutos
//				// : pode ser lido como "em" 
//				for (Produto p : todosProdutos) {
//					System.out.println(p.toString());
//				}
//				

//				ArrayList<Pedido> todosPedidos = pedidoDB.buscarTodos();
//				for (Pedido p : todosPedidos) {
//					System.out.println(p.toString());

				ArrayList<Pedido> todosPedidos = pedidoDB.buscarTodos();
				for (Pedido p : todosPedidos) {
					System.out.println(p.toString());
				}

				
				ArrayList<PedidoItens> relacaoPedidoItem = pedidoItensDB.buscarTodos();
				for(PedidoItens relacao : relacaoPedidoItem) {
					System.out.println(relacao.toString());
				}
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
