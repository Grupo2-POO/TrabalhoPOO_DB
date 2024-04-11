package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import classes.Cliente;
import classes.Pedido;
import classes.PedidoItens;
import classes.Produto;

public class PedidoDB implements CRUD<Pedido>{
		
	@Override
	public ArrayList<Pedido> buscarTodos() {
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
		String sql = """
				SELECT 
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
				    public.pedido p
			    JOIN
				   public.pedidoitens pi ON pi.idpedidoitem = p.idpedidoitem
				JOIN 
				    public.produto pr ON pr.idproduto = pi.idproduto
				JOIN
				    public.cliente c ON pi.idcliente = c.idcliente;
				""";
		
		
		try (Connection connection = DB.connect()) {
			Statement statement = connection.createStatement();
			var response = statement.executeQuery(sql);
			while (response.next()) {
				
				Cliente cliente = new Cliente(
						response.getInt("idcliente"),
						response.getString("nome"),
						response.getString("cpf"),
						response.getDate("dtnascimento"),
						response.getString("endereco"),
						response.getString("telefone")
						);
				
				Produto produto = new Produto(
						response.getInt("idproduto"),
						response.getDouble("vlcusto"),
						response.getDouble("vlvenda"),
						response.getInt("quantidade"),
						response.getString("descricao"),
						response.getString("categoria")
						);
				
				PedidoItens pedidoItens = new PedidoItens(
						 produto.getValorVenda(),
						 response.getDouble("vldesconto"),
						 produto,
						 cliente,
						 produto.getId(),
						 response.getInt("qtproduto")
						);
				
				Pedido pedido = new Pedido(
						response.getInt("idpedido"),
						pedidoItens,
						produto.getValorVenda() * pedidoItens.getQuantidadeProduto(),
						response.getDate("dtemissao"),
						response.getDate("dtentrega"),
						response.getString("observacao")
						);
				
		
				
				pedidos.add(pedido);
						
			}
		} catch (SQLException error) {
			System.err.println(error.getMessage());
		}
		return pedidos;
	}

	@Override
	public Pedido buscarUmPor(String nomeAtributo, String valorAtributo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletar() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void adicionar(String sql) {
		// TODO Auto-generated method stub
		
	}
}