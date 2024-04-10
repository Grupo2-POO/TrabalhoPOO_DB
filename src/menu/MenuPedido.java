package menu;

import java.util.Scanner;

import util.Util;

public final class MenuPedido extends NossoMenu {

	public MenuPedido(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processarOpcao(int opcao) {
		switch (opcao) {
		case 1: cadastrarPedidos(); break;
		case 2: consultarPedidos(); break;
		case 3: alterarPedidos(); break; 
		case 4: {
				Util.printMessage("Voltando ao menu inicial de pedidos vai rolar aqui"); 
				// executa o menu inicial
				
				MenuPrincipal menuPrincipal = new MenuPrincipal(ConstantesMenu.menuPrincipal, scanner);
				menuPrincipal.executarMenu();
				break; 
			}
		}
		
	}
	
	public void cadastrarPedidos() {
		
		
	}
	public void consultarPedidos() {
		
	}
	
	public void alterarPedidos() {
		
	}
}
