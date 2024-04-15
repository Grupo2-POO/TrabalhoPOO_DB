package menu;

import java.util.ArrayList;
import java.util.Scanner;

import classes.Cliente;
import classes.Pedido;
import classes.PedidoItem;
import classes.Produto;
import database.ClienteDB;
import database.PedidoDB;
import database.PedidoItemDB;
import database.ProdutoDB;
import util.Util;

public class MenuBuscarPedido extends NossoMenu {
	
	private Pedido pedido;
	private PedidoDB pedidoDB;
	private ClienteDB clienteDB;
	private ProdutoDB produtoDB;
	PedidoItemDB pedidoItensDB;
	private boolean alterandoPedidos, sair;

	public MenuBuscarPedido(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
		
		pedidoDB = new PedidoDB();
		clienteDB = new ClienteDB();
		produtoDB = new ProdutoDB();
		pedidoItensDB = new PedidoItemDB();
	}
	
	@Override
	public void executarMenu() {
		alterandoPedidos = true;
		while(alterandoPedidos) {
			sair = buscaPorCodigo() <= 0;
			if(!sair) {
				super.executarMenu();
			} else {
				break;
			}
		}
		
	}

	@Override
	public void processarOpcao(int opcao) {
		switch (opcao) {
		case 2: executarMenu(); break;
		case 1: {
			alterandoPedidos = false;
			break; 
			}
		default: break;
		}
		
	}
	
	private int buscaPorCodigo() {
		int codigo = -1;
		while(codigo <= -1) {
			codigo = Integer.parseInt(Util.askIntegerInput("Informe o Código do Pedido ou Digite 0 para sair:", scanner).trim());
			if(codigo == 0) {
				break;
			}
		}
		
		if(codigo > 0) {
			System.out.println("Buscando o Pedido de Código: " + codigo);
			String cdString = codigo + "";
			
			pedido = pedidoDB.buscarUmPor("idpedido", cdString.trim(), "pedido");
			
			if(pedido != null) {
		
				int opcImprimir = 0;
				while(opcImprimir < 1 || opcImprimir > 3) {
					opcImprimir = Integer.parseInt(Util.askIntegerInput("\nPedido encontrado! Como deseja visualizá-lo?\n1 - Pedido + Cliente\n2 - Pedido + Cliente + Produtos?\n3 - Voltar ao Menu", scanner));

				}
				boolean mostrarProdutos = opcImprimir == 2;
				mostrarPedido(pedido, mostrarProdutos);
				
			} else {
				System.out.println("pedido não encontrado!\n");
			}
		}
		
		return codigo;
	}

	public Pedido getPedido() {
		return pedido;
	}
	
	private void mostrarPedido(Pedido pedido, boolean mostrarProdutos) {
		
		Cliente cliente = clienteDB.buscarUmPor("idcliente", pedido.getIdCliente() + "", "cliente");
		
		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		builder.append(pedido.toString());
		builder.append("\n");
		builder.append("Informações do Cliente:\n");
		builder.append(cliente.toString());
		
		if(mostrarProdutos) {
			builder.append("\n\n\nItens do Pedido:\n");
			
			ArrayList<PedidoItem> pedidoitem = pedidoItensDB.buscarTodosPor("idpedido", pedido.getIdPedido() + "", "pedidoitens");

			for(PedidoItem item : pedidoitem) {
				
				Produto produto = produtoDB.buscarUmPor("idproduto", item.getIdProduto() + "", "produto");
				builder.append("\n" + produto.toStringAlterarPedido());
				builder.append("\t" + item.toStringAlterarPedido());
			}
		}
		
		builder.append("\n==================================================");

		System.out.println(builder.toString());
		
	}

}