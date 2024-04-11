package menu;

import java.util.Scanner;

import classes.Cliente;
import classes.PedidoItens;
import classes.Produto;
import util.Util;

public final class MenuPedido extends NossoMenu {

	public MenuPedido(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processarOpcao(int opcao) {
		switch (opcao) {

		case 1: cadastraPedidos(); break;
		case 2: Util.printMessage("Consulta de pedidos vai rolar aqui"); break;
		case 3: Util.printMessage("Alteração de pedidos vai rolar aqui"); break; 

		case 4: {
				Util.printMessage("Voltando ao menu inicial de pedidos vai rolar aqui"); 
				// executa o menu inicial
				
				MenuPrincipal menuPrincipal = new MenuPrincipal(ConstantesMenu.menuPrincipal, scanner);
				menuPrincipal.executarMenu();
				break; 
			}
		}
		
	}
	
	private void cadastraPedidos() {
		
		Util.printMessage("Para efetuar um novo pedido, precisamos de um cliente ");
		
		MenuBuscarCliente buscarCliente = new MenuBuscarCliente(
				ConstantesMenu.menuBuscarCliente, scanner);

		buscarCliente.executarMenu();
		
		Cliente cliente = buscarCliente.getCliente();
		
		while (cliente == null) {
			
			cliente = buscarCliente.getCliente();
			if(cliente == null) {
				Util.printMessage("Cliente não encontrado! Tente de novo!");
				buscarCliente.executarMenu();
			}
		} 
		
		Util.printMessage("Cliente encontrado: ");
		System.out.println(cliente.toString());
		
		//
		Util.printMessage("\nAgora vem a seleção de produto por codigo");
		
		MenuBuscarProduto buscarProduto = new MenuBuscarProduto(
				ConstantesMenu.menuBuscarProduto, scanner);
		
		buscarProduto.executarMenu();
		
		Produto produto = buscarProduto.getProduto();
		
		// cria um pedido item a adicionar ao database
		PedidoItens pedidoItem = new PedidoItens(
				 produto.getValorVenda(),
				 0.0, // tem que perguntar se tem desconto?
				 produto,
				 cliente,
				 // CONFERIR ISSO A ID DO PRODUTO não pode ser a idPedidoItems?
				 produto.getId(),
				 0 // tem que perguntar a quantidade do produto?
				);
				
		
		Util.printMessage("\nEsse foi o produto selecionado:");

		
		System.out.println(produto.toString());

		
		
		
	}

}
