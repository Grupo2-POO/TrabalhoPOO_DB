package menu;

import java.util.Scanner;

public class MenuPrincipal extends NossoMenu {
	
	public MenuPrincipal(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
	}

	@Override
	public void processarOpcao(int opcao) {
		switch (opcao) {
		case 1: menuPedido(); break;
		case 2: menuCliente(); break;
		case 3: menuProduto(); break; 
		}
		
	}

	private static void menuPedido() {
		MenuPedido menuPedido = new MenuPedido(ConstantesMenu.menuPedido, scanner);
		menuPedido.executarMenu();
		
	}
	
	private static void menuCliente() {
		
	}
	
	private static void menuProduto() {
		
	}

}