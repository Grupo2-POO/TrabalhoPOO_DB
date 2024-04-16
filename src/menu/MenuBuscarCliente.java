package menu;

import java.util.Scanner;

import classes.Cliente;
import database.ClienteDAO;
import util.Util;

public final class MenuBuscarCliente extends NossoMenu {
	
	private Cliente cliente;
	private ClienteDAO clienteDB;

	public MenuBuscarCliente(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
		clienteDB = new ClienteDAO();
	}

	@Override
	public void processarOpcao(int opcao) {
		switch (opcao) {
		case 1: buscaPorCPF(); break;
		case 2: buscaPorNome(); break;
		case 3: buscaPorCodigo(); break; 
		case 4: {
				NossoMenu.sairMenuDerivado = true;
				break; 
			}
		default: break;
		}
	
	}
	
	private void buscaPorCPF() {
		String cpf = "";
		while(cpf.length() < 1 || cpf.length() > 14) {
			cpf = Util.validateCPF("Informe o CPF:", scanner).trim();
		}
		System.out.println("Buscando o CPF: " + cpf);
		cliente = clienteDB.buscarUmPor("cpf", cpf, "cliente");
		
	}

	private void buscaPorNome() {
		String nome = "";
		while(nome.length() < 3 || nome.length() > 100) {
			nome = Util.pedeLinha("Informe o Nome:", scanner).trim();
			if(nome.length() < 3) {
				System.out.println("Nome muito curto para pesquisa!");
			}
		}
		System.out.println("Buscando o Nome: " + nome);
		cliente = clienteDB.buscarUmPor("nome", nome, "cliente");
	}
	
	public void buscaPorCodigo() {
		String codigo = "";
		while(codigo.length() < 1) {
			codigo = Util.askIntegerInput("Informe o Código:", scanner).trim();
		}
		System.out.println("Buscando o Código: " + codigo);
		cliente = clienteDB.buscarUmPor("idcliente", codigo, "cliente");
		
	}
	
	
	public Cliente getCliente() {
		return cliente;
	}
	
	

}
