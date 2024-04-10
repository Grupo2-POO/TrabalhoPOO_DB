package classes;

import java.util.Date;

import util.Util;

public record Pedido
		(int idPedido,
		Cliente cliente,
		Date dtEmissao,
		Date dtEntrega,
		String observacaoPed)
{
	@Override
	public String toString() {
		return "\nidPedido: " + idPedido + cliente + "\nData de Emissao: " + Util.novaDT(dtEmissao) + "\nData entrega: " + Util.novaDT(dtEntrega)
				+ "\nObservacao: " + observacaoPed + "\n";
	}
}
