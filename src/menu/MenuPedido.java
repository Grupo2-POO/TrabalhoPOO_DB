package menu;

import java.util.Scanner;

import classes.Cliente;
import util.Util;

public final class MenuPedido extends NossoMenu {

	public MenuPedido(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processarOpcao(int opcao) {
		switch (opcao) {

		case 1: cadastraPedidos(); break;
		case 2: Util.printMessage("Consulta de pedidos vai rolar aqui"); break;
		case 3: Util.printMessage("Alteração de pedidos vai rolar aqui"); break; 

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

	private void cadastraPedidos() {
		
		@SuppressWarnings("unused")
		Cliente clienteNovo = new Cliente();
		
		Util.printMessage("Para efetuar um novo pedido, precisamos de um cliente ");
		
		MenuBuscarCliente buscarCliente = new MenuBuscarCliente(ConstantesMenu.menuBuscarCliente, scanner);
		buscarCliente.executarMenu();
		
				
	}

}
