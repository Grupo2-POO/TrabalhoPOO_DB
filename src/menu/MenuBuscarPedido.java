package menu;

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
	private Cliente cliente;
	private ClienteDB clienteDB;
	private Produto produto;
	private ProdutoDB produtoDB;
	private PedidoItem pedidoitem;
	private PedidoItemDB pedidoitemDB;
	private boolean alterandoPedidos, sair;

	public MenuBuscarPedido(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
		
		pedidoDB = new PedidoDB();
		clienteDB = new ClienteDB();
		produtoDB = new ProdutoDB();
		pedidoitemDB = new PedidoItemDB();
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
			
			pedidoitem = pedidoitemDB.buscarUmPor("idcliente", pedido.getIdCliente() + "", "pedidoitens");
			produto = produtoDB.buscarUmPor("idproduto", pedidoitem.getIdProduto() + "", "produto");
			cliente = clienteDB.buscarUmPor("idcliente", pedido.getIdCliente() + "", "cliente");
			
			if(pedido != null) {
				System.out.println(pedido.toString());
			} else {
				System.out.println("pedido não encontrado!\n");
			}
		}
		
		return codigo;
	}

	public Pedido getPedido() {
		return pedido;
	}

}