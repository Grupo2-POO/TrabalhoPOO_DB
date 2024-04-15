package menu;

import java.util.ArrayList;
import java.util.Scanner;

import classes.Cliente;
import classes.Pedido;
import database.ClienteDB;

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
		case 2: menuProduto(); break;
		case 3: {
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
	
	
	private static void menuProduto() {
		MenuProduto menuProduto = new MenuProduto(ConstantesMenu.menuProduto, scanner);
		menuProduto.executarMenu();
	}

}