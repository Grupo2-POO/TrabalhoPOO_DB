package menu;

import java.util.Scanner;

import classes.PedidoItens;
import classes.Produto;
import database.PedidoItensDB;
import util.Util;

public class MenuBuscarPedidoItens extends NossoMenu {
	
	private PedidoItens pedidoitens;
	private PedidoItensDB pedidoitensDB;
	private boolean selecionandoPedidos;

	public MenuBuscarPedidoItens(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
		// TODO Auto-generated constructor stub
		
		pedidoitensDB = new PedidoItensDB();
	}
	
	@Override
	public void executarMenu() {
		selecionandoPedidos = true;
		while(selecionandoPedidos) {
			buscaPorCodigo();
			super.executarMenu();
		}
		
	}

	@Override
	public void processarOpcao(int opcao) {
		switch (opcao) {

		case 2: executarMenu(); break;
		case 1: {
			selecionandoPedidos = false;
			break; 
			}
		default: break;
		}
		
	}
	
	private void buscaPorCodigo() {
		int codigo = -1;
		while(codigo == -1) {
			codigo = Integer.parseInt(Util.askIntegerInput("Informe o Código do Pedido ou Digite 0 para sair:", scanner).trim());
			if(codigo == 0) {
				break;
			}
		}
		
		if(codigo > 0) {
			System.out.println("Buscando o Pedido de Código: " + codigo);
			String cdString = codigo + "";
			
			pedidoitens = pedidoitensDB.buscarUmPor("idpedidoitens", cdString.trim(), "pedidoitens");
			
			if(pedidoitens != null) {
				System.out.println(pedidoitens.toString());
				
			} else {
				System.out.println("pedido não encontrado!\n");
			}
		}
	}

	public PedidoItens getPedidoItens() {
		return pedidoitens;
	}

}