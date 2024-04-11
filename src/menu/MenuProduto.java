package menu;

import java.util.Scanner;

import classes.Cliente;
import classes.PedidoItens;
import classes.Produto;
import util.Util;

public final class MenuProduto extends NossoMenu {

	public MenuProduto(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processarOpcao(int opcao) {
		switch (opcao) {

		case 1: buscarProdutos(); break;
		case 2: {
				MenuPrincipal menuPrincipal = new MenuPrincipal(ConstantesMenu.menuPrincipal, scanner);
				menuPrincipal.executarMenu();
				break; 
			}
		}
		
	}
	
	private void buscarProdutos() {
		MenuBuscarProduto buscarProduto = new MenuBuscarProduto(
				ConstantesMenu.menuBuscarProduto, scanner);

		buscarProduto.executarMenu();
	}

}