package classes;

import util.Util;

public record PedidoItens
		(double valorTotal,
		double valorDesconto,
		Produto produto,
		Pedido pedido,
		int idPedidoitem,
		int quantidade_produto) 
{
	public String toString() {
		return  Util.linhaSimples(20) + "\nidPedidoitem: " 
				+ idPedidoitem + pedido + "\n"
				+ produto + "\nQuantidade pedido: " + quantidade_produto + "\nValor total do pedido: " + Util.converterMonetario(valorTotal)
				+ "\nValor Desconto: " + Util.converterMonetario(valorDesconto) + "\n" + Util.linhaSimples(20) + "\n";
	}
}
