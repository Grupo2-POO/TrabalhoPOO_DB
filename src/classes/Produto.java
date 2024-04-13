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
                + "\n"
                + "Só " + Util.converterMonetario(valorVenda)
                + "\nCategoria: " + categoria + "\n";
    }
    
    public String toStringQuantidadePedido() {
    	// Largura fixa para a descrição, 
    	// parece funcionar com a maioria dos produtos
        int descricaoWidth = 40; 

        // Formata a descrição com a largura fixa e alinhamento à esquerda
        String formattedDescricao = String.format("%-" + descricaoWidth + "s", descricao);

        // Formata a quantidade com largura fixa e alinhamento à direita
        String formattedQuantidade = String.format("%5d", quantidadePedido);

        String formattedValorUnitario = Util.converterMonetario(valorVenda);
        String formattedTotal = Util.converterMonetario(valorVenda * quantidadePedido);

        return String.format("\n%s %s X %s : %s\n", formattedDescricao, formattedQuantidade, formattedValorUnitario, formattedTotal);
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
