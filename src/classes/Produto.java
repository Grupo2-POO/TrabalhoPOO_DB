package classes;

import util.Util;

public record Produto
		(int idProduto,
		double valorCusto,
		double valorVenda,
		int quantidade,
		String descricao,
		String categoria) 
{


	@Override
	public String toString() {
		return 	"\nidProduto: " 
				+ idProduto 
				+ "\n" 
				+ descricao
				+ "\nQuantidade no estoque:  " + quantidade + "\nCategoria: " + categoria + "\n";
	}

	public double getValorVenda() {
		return valorVenda;
	}

	public int getId() {
		return idProduto;
	}
}
