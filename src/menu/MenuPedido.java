package menu;

import java.util.Scanner;

import classes.Cliente;
import classes.PedidoItens;
import classes.Produto;
import database.PedidoItensDB;
import util.Util;

public final class MenuPedido extends NossoMenu {
	
	PedidoItensDB pedidoItensDB;

	public MenuPedido(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
		// TODO Auto-generated constructor stub
		pedidoItensDB = new PedidoItensDB();
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
		// mostrar opcao de adicionar mais do mesmo produto
		
		Util.printMessage("\nDeseja adicionar mais desse produto?");
		
		Util.printMessage("\nEsse foi o produto selecionado:");

		System.out.println(produto.toString());
		
		int qtd = 1;
		
		int qtdMaxima = produto.getQuantidade();
		
		while(qtd <= 1 || qtd > qtdMaxima) {
			
			qtd = Integer.parseInt(Util.askIntegerInput("\nInsira a quantidade extra\n", scanner));
			
			if(qtd <= 0) {
				System.out.println("Quantidade não pode ser negativa!");
			}
			
			if(qtd > qtdMaxima) {
				System.out.println("Quantidade não pode ser maior que o estoque!");
			}
		}
		
		// todo processar o desconto?
		double vlDesconto = 0.0;
		
		// cria um pedido item a adicionar ao database
		
		String sql = String.format(
				"insert into %s"
				+ "(idcliente, idproduto, vlunitario, vldesconto, qtproduto) "
				+ "values('%s','%s', '%s', '%s', '%s');",
				"pedidoitens",
				cliente.getIdCliente(),
				produto.getId(),
				produto.getValorVenda(),
				vlDesconto,
				qtd
				);
		
		Util.printMessage(
				"\nAdicionando o pedidoitem com " 
						+ qtd + " " + produto.descricao() + "\nNão estamos dando nenhum desconto ainda");

		// adiciona o pedido item
		pedidoItensDB.adicionar(sql);
		
	}

}
