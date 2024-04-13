package classes;

import util.Util;

public class Produto {
    private int idProduto;
    private double valorCusto;
    private double valorVenda;
    private int quantidadeEstoque;
    private String descricao;
    private int quantidadePedido;
   

	private String categoria;

    public Produto(int idProduto, double valorCusto, double valorVenda, int quantidade, String descricao, String categoria) {
        this.idProduto = idProduto;
        this.valorCusto = valorCusto;
        this.valorVenda = valorVenda;
        this.quantidadeEstoque = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "\nidProduto: " 
                + idProduto 
                + "\n" 
                + descricao
                + "\nQuantidade no estoque:  " + quantidadeEstoque + "\nCategoria: " + categoria + "\n";
    }

    public String toStringSemEstoque() {
        return "\nidProduto: " 
                + idProduto 
                + "\n" 
                + descricao
                + "\nCategoria: " + categoria + "\n";
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public int getId() {
        return idProduto;
    }

    public int getQuantidade() {
        return quantidadeEstoque;
    }

	public void setQuantidade(int i) {
		this.quantidadeEstoque = i;
		
	}
	
	 public String getDescricao() {
			return descricao;
	}

	public int getQuantidadePedido() {
		return quantidadePedido;
	}

	public void setQuantidadePedido(int quantidadePedido) {
		this.quantidadePedido += quantidadePedido;
	}


}
