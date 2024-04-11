package menu;

import java.util.Scanner;
import classes.Produto;
import database.ProdutoDB;
import util.Util;

public class MenuBuscarProduto extends NossoMenu {
	
	private Produto produto;

	public MenuBuscarProduto(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void executarMenu() {
		buscaPorCodigo();
		
		super.executarMenu();
		
	}

	@Override
	public void processarOpcao(int opcao) {
		switch (opcao) {

		case 1: buscaPorCodigo(); break;
		case 2: {
				Util.printMessage("Voltando ao menu inicial de pedidos vai rolar aqui"); 
				break; 
			}
		}
		
	}
	
	private void buscaPorCodigo() {
		int codigo = -1;
		while(codigo == -1) {
			codigo = Integer.parseInt(Util.askIntegerInput("Informe o Código do Produto ou Digite 0 para sair:", scanner).trim());
			if(codigo == 0) {
				break;
			}
		}
		
		if(codigo > 0) {
			System.out.println("Buscando o Produto de Código: " + codigo);
			// todo busca produto por codigo
			produto = ProdutoDB.buscaProdutoPorCodigo(codigo);
			
			if(produto != null) {
				System.out.println(produto.toString());
				
			} else {
				System.out.println("Produto não encontrado!\n");
			}
		}
	}

	public Produto getProduto() {
		return produto;
	}

}
