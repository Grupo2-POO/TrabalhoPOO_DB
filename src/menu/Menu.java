package menu;

import java.util.ArrayList;
import java.util.Scanner;

import classes.PedidoItens;
import classes.PedidoItensDB;
import util.Util;

public class Menu {
	
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static void executarMenu() {
		
		mostrarMenu();
		//int opcao = pegarOpcao();
		processarOpcao(pegarOpcao());
	}
		
	public static void mostrarMenu() {
 		
		System.out.println("\n\t- GERADOR DE PEDIDOS 2MIL! -");
		System.out.println(Util.linhaSimples(40) + "\n" +
						"  ° Escolha uma opção: \t \n" +
							Util.linhaSimples(40) + "\n" +
						" 1- Menu pedido\t\n" +
							Util.linhaSimples(40) + "\n" +
						" 2- Menu cliente\t\n" +
							Util.linhaSimples(40) + "\n" +
						" 3- Menu produto\t\n" + 
							Util.linhaSimples(40) + "\n" 
						
							);
		
	}
	
	private static int pegarOpcao() {
		int opcao = 0;
		while (opcao < 1 || opcao > 4) {
				opcao = Util.pedirOpcaoInt("Digite uma opção válida", scanner);
		}
		return opcao;
		
	}
	
	private static void processarOpcao(int opcao) {
		switch (opcao) {
		case 1: menuPedido(); break;
		case 2: menuCliente(); break;
		case 3: menuProduto(); break; 
		}
		
		
	}

	private static void menuPedido() {
		executarMenuPedido();
	}
	
	private static void menuCliente() {
//		ArrayList<Cliente> todosClientes = ClienteDB.buscarTodosClientes();
//		for (int i = 0; i < todosClientes.size(); i++ ) {
//			Cliente cliente = todosClientes.get(i);
//			System.out.println(cliente.toString());
//		}
//		
//		ArrayList<Produto> todosProdutos = ProdutoDB.buscarTodosProdutos();
//		// Exemplo de for each loop:
//		// p é o apelido de cada item no ArrayList todosProdutos
//		// : pode ser lido como "em" 
//		for (Produto p : todosProdutos) {
//			System.out.println(p.toString());
//		}
//		
//		ArrayList<Pedido> todosPedidos = PedidoDB.buscarTodosPedidos();
//		for (Pedido p : todosPedidos) {
//			System.out.println(p.toString());
//		
//		}
		
		ArrayList<PedidoItens> relacaoPedidoItem = PedidoItensDB.relacaoPedidoItem();
		for(PedidoItens relacao : relacaoPedidoItem) {
			System.out.println(relacao.toString());
		}
	}
	
	private static void menuProduto() {
		System.out.println("será??");
	}
	
	
	/**
	 * MENU PEDIDO = VEJAM, PODEMOS USAR UMA CLASSE ABSTRATA PARA OS MENUS
	 */
		
	public static  void executarMenuPedido() {
		// essa é a executar o menu pedido
		mostrarMenuPedido();
		//
		processarOpcaoPedido(pegarOpcaoPedido());
	}
	
	private static void mostrarMenuPedido() {
		System.out.println("\n\t- == MENU DE PEDIDOS == -");
		System.out.println(Util.linhaSimples(40) + "\n" +
						"  ° Escolha uma opção: \t \n" +
							Util.linhaSimples(40) + "\n" +
						" 1- Cadastrar pedido\t\n" +
							Util.linhaSimples(40) + "\n" +
						" 2- Consultar pedido\t\n" +
							Util.linhaSimples(40) + "\n" +
						" 3- Alterar pedido\t\n" + 
							Util.linhaSimples(40) + "\n" +
						" 4- Voltar ao menu inicial\t\n" + 
							Util.linhaSimples(40) + "\n" 
						
							);
	}
	
	private static void processarOpcaoPedido(int opcaoPedido) {
		switch (opcaoPedido) {
		case 1: Util.printMessage("Cadastro de pedidos vai rolar aqui"); break;
		case 2: Util.printMessage("Consulta de pedidos vai rolar aqui"); break;
		case 3: Util.printMessage("Alteração de pedidos vai rolar aqui"); break; 
		case 4: {
			Util.printMessage("Voltando ao menu inicial de pedidos vai rolar aqui"); 
			// executa o menu inicial
			executarMenu();
			
			break; 
		}
		}
	}
	
	private static int pegarOpcaoPedido() {
		int opcaoPedido = 0;
		while(opcaoPedido < 1 || opcaoPedido > 4) {
			opcaoPedido = Util.pedirOpcaoInt("Digite uma opção válida" , scanner);
		}
		return opcaoPedido;
	}
	
	
	
	
	
}
