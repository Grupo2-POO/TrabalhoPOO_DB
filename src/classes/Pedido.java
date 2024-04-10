package classes;

import java.util.Date;

import util.Util;

public record Pedido
		(int idPedido,
		PedidoItens pedidoItens,
		double valorTotal,
		Date dtEmissao,
		Date dtEntrega,
		String observacaoPed)
{
	@Override
	public String toString() {
		return  Util.linhaSimples(20) + "\nidPedido: " + idPedido 
				+ pedidoItens
				+ "\nData de Emissao: " + Util.novaDT(dtEmissao)
				+ "\nData entrega: " + Util.novaDT(dtEntrega)
				+ "\nValor total: " + Util.converterMonetario(pedidoItens.getValorTotal())
				+ "\nObservacao: " + observacaoPed 
				+ "\n"
				+ Util.linhaSimples(20)
				+ "\n";
				
	}
}
