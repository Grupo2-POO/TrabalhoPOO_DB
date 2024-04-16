package menu;

import java.util.Scanner;
import classes.Produto;
import database.ProdutoDAO;
import util.Util;

public class MenuBuscarProduto extends NossoMenu {
	
	private Produto produto;
	private ProdutoDAO produtoDB;
	private boolean selecionandoProdutos, sair;

	public MenuBuscarProduto(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
		// TODO Auto-generated constructor stub
		
		produtoDB = new ProdutoDAO();
	}
	
	@Override
	public void executarMenu() {
		selecionandoProdutos = true;
		while(selecionandoProdutos) {
			buscaPorCodigo();
			super.executarMenu();
			
		}
		
	}

	@Override
	public void processarOpcao(int opcao) {
		switch (opcao) {

		case 2: executarMenu(); break;
		case 1: {
			selecionandoProdutos = false;
			break; 
			}
		default: break;
		}
		
	}
	
	public void buscaPorCodigo() {
		int codigo = -1;
		while(codigo <= -1) {
			codigo = Integer.parseInt(Util.askIntegerInput("Informe o Código do Produto:", scanner).trim());
		}
		
		if(codigo > 0) {
			System.out.println("Buscando o Produto de Código: " + codigo);
			// todo busca produto por codigo
			String cdString = codigo + "";
			
			produto = produtoDB.buscarUmPor("idproduto", cdString.trim(), "produto");
			
			if(produto != null) {
				System.out.println(produto.toStringSemEstoque());
				
			} else {
				System.out.println("Produto não encontrado!\n");
			}
		}
	}

	public Produto getProduto() {
		return produto;
	}

}
