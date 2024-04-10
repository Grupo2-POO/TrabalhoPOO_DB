package menu;

import java.util.Scanner;

public class TesteMenu {

	private static Scanner scanner = new Scanner(System.in);		

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MenuPrincipal menuPrincipal = new MenuPrincipal(
				ConstantesMenu.menuPrincipal,
				scanner
				);
		
		menuPrincipal.executarMenu();
				

	}

}
