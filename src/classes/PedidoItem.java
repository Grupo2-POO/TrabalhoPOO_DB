package classes;

import java.text.DecimalFormat;

import util.Util;

public class PedidoItem{
	double valorUnitario;
	double valorDesconto;
	Produto produto;
	Cliente cliente;
	
	int idPedidoitem;
	int quantidadeProduto;
	int idProduto;
	int idCliente;
	
	
	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public int getIdCliente() {
		return idCliente;
	}


	public PedidoItem(double valorUnitario, double valorDesconto, Produto produto, Cliente cliente, int idPedidoitem,
			int quantidadeProduto, int idProduto, int idCliente) {
		super();
		this.valorUnitario = valorUnitario;
		this.valorDesconto = valorDesconto;
		this.produto = produto;
		this.cliente = cliente;
		this.idPedidoitem = idPedidoitem;
		this.quantidadeProduto = quantidadeProduto;
		this.idProduto = idProduto;
		this.idCliente = idCliente;
	}
	
	public PedidoItem(double valorUnitario, double valorDesconto, int idPedidoitem,
			int quantidadeProduto, int idProduto, int idCliente) {
		super();
		this.valorUnitario = valorUnitario;
		this.valorDesconto = valorDesconto;
		this.idPedidoitem = idPedidoitem;
		this.quantidadeProduto = quantidadeProduto;
		this.idProduto = idProduto;
		this.idCliente = idCliente;
	}

	public int getIdPedidoItem() {
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
				+ "\nValor Desconto: " + Util.DF.format((1.0 - valorDesconto) * 100) + "%"
				+ "\n";
	}
	
	public String toStringAlt() {
		return  "\nidPedidoitem: " 
				+ idPedidoitem
				+ "\nIdProduto: " + idProduto
				+ "\nQuantidade pedido: " + quantidadeProduto 
				+ "\nValor Unitário do Produto: " + Util.converterMonetario(valorUnitario)
				+ "\nValor Desconto: " + Util.DF.format((1.0 - valorDesconto) * 100) + "%"
				+ "\n";
	}
	
	public String toStringAlterarPedido() {
		return  
			"\n  Valor Desconto: " + Util.DF.format((1.0 - valorDesconto) * 100) + "%"
			+ "\n  Quantidade pedido: " + quantidadeProduto 
			+ "\n";
	}
	
	public String toStringSoProduto() {
		return  "" + produto
				+ "\n";
	}
	
	
	public double getValorTotal() {
		return valorUnitario * quantidadeProduto;
	}
	
	public double getValorDesconto() {
		return valorDesconto;
	}
	
	public Cliente getCliente() {
		return cliente;
	}


}
