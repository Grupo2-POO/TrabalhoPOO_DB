package menu;

import java.util.Scanner;

import classes.Pedido;
import database.PedidoDB;
import util.Util;

public class MenuBuscarPedido extends NossoMenu {
	
	private Pedido pedido;
	private PedidoDB pedidoDB;
	private boolean alterandoPedidos, sair;

	public MenuBuscarPedido(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
		
		pedidoDB = new PedidoDB();
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