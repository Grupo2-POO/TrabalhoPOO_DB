package menu;

import java.util.Scanner;

import classes.Cliente;
import util.Util;

public class MenuPrincipal extends NossoMenu {
	// menu principal passa o valor do NossoMenu.sairMenuDerivado para o loop principal
	// se tiver saido de um menu derivado (que não seja o principal, 
	// deve repetir o menu principal
	
	public static boolean sairMenuDerivado;

	public MenuPrincipal(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
	}
	
	@Override
	public void processarOpcao(int opcao) {
		switch (opcao) {
		case 1: menuPedido(); break;
		case 2: menuCliente(); break;
		case 3: menuProduto(); break;
		case 4: {
			NossoMenu.sairMenuDerivado = false;
			break;
		}
		default: break;
		}
		
	}

	private static void menuPedido() {
		MenuPedido menuPedido = new MenuPedido(ConstantesMenu.menuPedido, scanner);
		menuPedido.executarMenu();
		
	}
	
	private static void menuCliente() {
		MenuBuscarCliente buscarCliente = new MenuBuscarCliente(
				ConstantesMenu.menuBuscarCliente, scanner);

		buscarCliente.executarMenu();
		
		Cliente cliente = buscarCliente.getCliente();
		
		while (cliente == null) {
			
			cliente = buscarCliente.getCliente();
			if(NossoMenu.sairMenuDerivado) {
				return;
			}
			if(cliente == null) {
				Util.printMessage("\nCliente não encontrado! Tente de novo!");
				buscarCliente.executarMenu();
			}
		} 
		
		Util.printMessage(
				"-----------------------------------------\n" +
				"Cliente encontrado: "
				);
		System.out.println(cliente.toString());
	}
	
	private static void menuProduto() {
		MenuProduto menuProduto = new MenuProduto(ConstantesMenu.menuProduto, scanner);
		menuProduto.executarMenu();
	}

}