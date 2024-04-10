package menu;

import java.util.Scanner;

import util.Util;

public class MenuBuscarCliente extends NossoMenu {

	public MenuBuscarCliente(String[] constantes, Scanner scanner) {
		super(constantes, scanner);

	}

	@Override
	public void processarOpcao(int opcao) {
		switch (opcao) {
		case 1: Util.printMessage("Busca por cpf"); break;
		case 2: Util.printMessage("Busca por nome"); break;
		case 3: Util.printMessage("Busca por código"); break; 
		case 4: {
				Util.printMessage("Voltando ao menu inicial de pedidos vai rolar aqui"); 
				// executa o menu inicial
				
				MenuPrincipal menuPrincipal = new MenuPrincipal(ConstantesMenu.menuPrincipal, scanner);
				menuPrincipal.executarMenu();
				break; 
			}
		}
		
	}

}
