package menu;

import java.util.Scanner;

public class MenuPrincipal extends NossoMenu {
	
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
		}
		
	}

	private static void menuPedido() {
		MenuPedido menuPedido = new MenuPedido(ConstantesMenu.menuPedido, scanner);
		menuPedido.executarMenu();
		
	}
	
	private static void menuCliente() {
		
	}
	
	private static void menuProduto() {
		MenuProduto menuProduto = new MenuProduto(ConstantesMenu.menuProduto, scanner);
		menuProduto.executarMenu();
	}

}