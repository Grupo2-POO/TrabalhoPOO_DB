package classes;

import java.util.Date;

import util.Util;

public class Pedido {
    private int idPedido;
    private PedidoItens pedidoItens;
    private double valorTotal;
    private Date dtEmissao;
    private Date dtEntrega;
    private String observacaoPed;
    private int idCliente;

    
    public Pedido(int idPedido, double valorTotal, Date dtEmissao, Date dtEntrega, String observacaoPed, int idCliente) {
        this.idPedido = idPedido;
        this.valorTotal = valorTotal;
        this.dtEmissao = dtEmissao;
        this.dtEntrega = dtEntrega;
        this.observacaoPed = observacaoPed;
        this.idCliente = idCliente;
    }


    public Pedido() {
		// TODO Auto-generated constructor stub
	}


	@Override
    public String toString() {
        return Util.linhaSimples(20) + "\nidPedido: " + idPedido
                + "\nidCliente: " + idCliente 
                + "\nData de Emissao: " + Util.novaDT(dtEmissao)
                + "\nData entrega: " + Util.novaDT(dtEntrega)
                + "\nValor total: " + Util.converterMonetario(valorTotal)
                + "\nObservacao: " + observacaoPed
                + "\n"
                + Util.linhaSimples(20)
                + "\n";
    }

    // Getters and setters if needed
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public PedidoItens getPedidoItens() {
        return pedidoItens;
    }

    public void setPedidoItens(PedidoItens pedidoItens) {
        this.pedidoItens = pedidoItens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getDtEmissao() {
        return dtEmissao;
    }

    public void setDtEmissao(Date dtEmissao) {
        this.dtEmissao = dtEmissao;
    }

    public Date getDtEntrega() {
        return dtEntrega;
    }

    public void setDtEntrega(Date dtEntrega) {
        this.dtEntrega = dtEntrega;
    }

    public String getObservacaoPed() {
        return observacaoPed;
    }

    public void setObservacaoPed(String observacaoPed) {
        this.observacaoPed = observacaoPed;
    }
}
