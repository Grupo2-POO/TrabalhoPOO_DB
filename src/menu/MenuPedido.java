package menu;

import java.util.ArrayList;
import java.util.Scanner;

import classes.Cliente;
import classes.PedidoItens;
import classes.Produto;
import database.PedidoDB;
import database.PedidoItensDB;
import util.Util;

public final class MenuPedido extends NossoMenu {
	
	PedidoItensDB pedidoItensDB;
	PedidoDB pedidoDB;

	public MenuPedido(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
		// TODO Auto-generated constructor stub
		pedidoItensDB = new PedidoItensDB();
		pedidoDB = new PedidoDB();
	}

	

	@Override
	public void processarOpcao(int opcao) {
		switch (opcao) {

		case 1: cadastraPedidos(); break;
		case 2: Util.printMessage("Consulta de pedidos vai rolar aqui"); break;
		case 3: alterarPedidos(); break; 
		case 4: {
				MenuBuscarPedidoItens MenuBuscarPedidoItens = new MenuBuscarPedidoItens(ConstantesMenu.menuBuscarPedidoItens, scanner);
				MenuBuscarPedidoItens.executarMenu();
				break; 
			}
		}
	}
	
	private void cadastraPedidos() {
		incluirPedido();
	}
	
	private void alterarPedidos() {
		mostrarPedidos();
		
		System.out.println("Informe o codigo do pedido que deseja alterar: ");
		int opc;
		
		MenuBuscarPedidoItens MenuBuscarPedidoItens = new MenuBuscarPedidoItens(ConstantesMenu.menuBuscarPedidoItens, scanner);
		MenuBuscarPedidoItens.executarMenu();
	}
	
	private void mostrarPedidos() {
		ArrayList<PedidoItens> relacaoPedidoItem = pedidoItensDB.buscarTodos();
		for(PedidoItens relacao : relacaoPedidoItem) {
			System.out.println(relacao.toString());
		}
	}
	
	public Cliente incluirCliente() {
		Util.printMessage("Para efetuar um novo pedido, é necessário informar um cliente ");
		
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
		
		return cliente;
	}
	
	public Produto incluirProduto() {
		
		Util.printMessage("\nAgora vem a seleção de produto por codigo");
		
		MenuBuscarProduto buscarProduto = new MenuBuscarProduto(
				ConstantesMenu.menuBuscarProduto, scanner);
		
		buscarProduto.executarMenu();
		
		Produto produto = buscarProduto.getProduto();
		// mostrar opcao de adicionar mais do mesmo produto
		Util.printMessage("\nEsse foi o produto selecionado:");

		System.out.println(produto.toString());
		
		return produto;		
	}
	
	public void incluirPedido() {
		Cliente cliente = incluirCliente();
		
		boolean maisUM = false;
		
		do {
			Produto produto = incluirProduto();
			
			double vlDesconto = 0.0;
			
			int qtdExtra = 1;
			
			int qtdMaxima = produto.getQuantidade();
			
			while(qtdExtra <= 1 || qtdExtra > qtdMaxima) {
				
				qtdExtra = Integer.parseInt(Util.askIntegerInput("\nInsira a quantidade de produtos: \n", scanner));
				
				if(qtdExtra <= 0) {
					System.out.println("Quantidade não pode ser negativa!");
				}
				
				if(qtdExtra > qtdMaxima) {
					System.out.println("Quantidade não pode ser maior que o estoque!");
				}
			}
			
			String[] valores = { 
					String.valueOf(cliente.getIdCliente()),
					String.valueOf(produto.getId()),
					String.valueOf(produto.getValorVenda()),
					String.valueOf(vlDesconto),
					String.valueOf(qtdExtra)
					
			};
					
				//String observacao = Util.pedeLinha("\nobservacao: \n", scanner);
				String observacao = "nd";
				pedidoItensDB.adicionar(valores);
				int idpedidoitem = pedidoItensDB.adicionarPedidoItens(valores);
				pedidoDB.adicionarPedido(idpedidoitem, observacao);
			
			int opc =  Integer.parseInt(Util.askIntegerInput("\nDeseja adicionar mais produtos?\n1 - SIM\n2 - NÃO", scanner));
			if(opc == 1) {
				maisUM = true;
			}else if(opc == 2) {
				maisUM = false;
			}else {
				System.out.println("nd.....");
			}
					
		}while(maisUM);
		
		
	}
}
