package classes;

import util.Util;

public record PedidoItens
		(double valorUnitario,
		double valorDesconto,
		Produto produto,
		Cliente cliente,
		int idPedidoitem,
		int quantidadeProduto) 
{
	public int getId() {
		return idPedidoitem;
	}

	public int getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public String toString() {
		return  "\nidPedidoitem: " 
				+ idPedidoitem + cliente + "\n"
				+ produto + "\nQuantidade pedido: " + quantidadeProduto 
				+ "\nValor Unitário do Produto: " + Util.converterMonetario(valorUnitario)
				+ "\nValor Desconto: " + Util.converterMonetario(valorDesconto)
				+ "\n";
	}
	
	public double getValorTotal() {
		return valorUnitario * quantidadeProduto;
	}

}
