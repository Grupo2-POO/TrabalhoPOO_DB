package classes;

import java.sql.Statement;
import java.util.ArrayList;

import database.DB;

public class PedidoItensDB{
	public static ArrayList<PedidoItens> relacaoPedidoItem(){
		ArrayList<PedidoItens> relacao = new ArrayList<PedidoItens>();
		
		String sql = """
				SELECT 
			    pi.idpedidoitem,
			    p.idpedido,
				p.dtemissao,
				p.dtentrega,
				p.observacao,
			    c.idcliente,
			    c.nome,
			    c.cpf,
			    c.dtnascimento,
			    c.endereco,
			    c.telefone,
			    pr.idproduto,
			    pr.descricao,
			    pr.vlcusto,
			    pr.quantidade,
			    pr.vlvenda,
			    pr.categoria,
			    pi.vlunitario,
			    pi.qtproduto,
				pi.vldesconto
				FROM 
				    public.pedidoitens pi
			    JOIN
				    public.pedido p ON pi.idpedido = p.idpedido
				JOIN 
				    public.produto pr ON p.idproduto = pr.idproduto
				JOIN
				    public.cliente c ON p.idcliente = c.idcliente;
				""";
		
		try(var conn = DB.connect()){
			Statement statement = conn.createStatement();
			
			var response = statement.executeQuery(sql);
			
			int qtd = 0;
			
			while(response.next()) {
				Produto produto = new Produto(
						response.getInt("idproduto"),
						response.getDouble("vlcusto"),
						response.getDouble("vlvenda"),
						response.getInt("quantidade"),
						response.getString("descricao"),
						response.getString("categoria")
						);
				
				Cliente cliente = new Cliente(
						response.getInt("idcliente"),
						response.getString("nome"),
						response.getString("cpf"),
						response.getDate("dtnascimento"),
						response.getString("endereco"),
						response.getString("telefone")
						
						);

				
				PedidoItens pedidoItem = new PedidoItens(
														 produto.getValorVenda(),
														 response.getDouble("vldesconto"),
														 produto,
														 cliente,
														 produto.getId(),
														 response.getInt("qtproduto")
														);
				relacao.add(pedidoItem);
			}
			
		}catch(Exception e) {
			System.err.println(e);
		}
		return relacao;
	}
}
