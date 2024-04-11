package menu;

import java.util.Scanner;

import classes.Cliente;
import classes.ClienteDB;
import util.Util;

public class MenuBuscarCliente extends NossoMenu {
	
	private Cliente cliente;

	public MenuBuscarCliente(String[] constantes, Scanner scanner) {
		super(constantes, scanner);

	}

	@Override
	public void processarOpcao(int opcao) {
		switch (opcao) {
		case 1: buscaPorCPF(); break;
		case 2: buscaPorNome(); break;
		case 3: buscaPorCodigo(); break; 
		case 4: {
				Util.printMessage("Voltando ao menu inicial de pedidos vai rolar aqui"); 
				// executa o menu inicial
				
				MenuPrincipal menuPrincipal = new MenuPrincipal(ConstantesMenu.menuPrincipal, scanner);
				menuPrincipal.executarMenu();
				break; 
			}
		}
	}
	
	private void buscaPorCPF() {
		String cpf = "";
		while(cpf.length() < 1 || cpf.length() > 14) {
			cpf = Util.askIntegerInput("Informe o CPF:", scanner).trim();
		}
		System.out.println("Buscando o CPF: " + cpf);
		cliente = ClienteDB.buscaClientePorCPF(cpf);
		
	}

	private void buscaPorNome() {
		String nome = "";
		while(nome.length() < 1 || nome.length() > 100) {
			nome = Util.pedeLinha("Informe o Nome:", scanner).trim();
		}
		System.out.println("Buscando o Nome: " + nome);
		cliente = ClienteDB.buscaClientePorNome(nome);
	}
	
	private void buscaPorCodigo() {
		int codigo = -1;
		while(codigo == -1) {
			codigo = Integer.parseInt(Util.askIntegerInput("Informe o Código:", scanner).trim());
		}
		System.out.println("Buscando o Código: " + codigo);
		cliente = ClienteDB.buscaClientePorCodigo(codigo);
		
	}
	
	
	public Cliente getCliente() {
		return cliente;
	}
	
	

}
