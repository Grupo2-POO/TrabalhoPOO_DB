package database;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import classes.Cliente;
import classes.PedidoItens;
import classes.Produto;
import util.Util;

public class PedidoItensDB implements CRUD<PedidoItens>{
	
	@Override
	public ArrayList<PedidoItens> buscarTodos() {
		
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
				    public.produto pr ON pi.idproduto = pr.idproduto
				JOIN
				    public.cliente c ON pi.idcliente = c.idcliente;
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
						 // CONFERIR ISSO A ID DO PRODUTO
						 response.getInt("idpedidoitem"),
						 response.getInt("qtproduto")
						);
				
				relacao.add(pedidoItem);
			}
			
		}catch(Exception e) {
			System.err.println(e);
		}
		return relacao;
	}

	@Override
	public PedidoItens buscarUmPor(String nomeAtributo, String valorAtributo) {
		// TODO Auto-generated method stub
		String nomeTabela = Util.removerUltimosCaracteres(this.getClass().getSimpleName());
		
		String sql = String.format("SELECT * FROM %s WHERE %s='%s'", 
				nomeTabela,
				nomeAtributo,
				valorAtributo);
		
		return null;
	}
	
	@Override
	public void adicionar(String sql) {
		
		try(var conn = DB.connect()){
			Statement statement = conn.createStatement();

			statement.executeUpdate(sql);
		} catch(SQLException e) {
			System.err.println(e);
		}
		
	}

	@Override
	public void deletar() {
		// TODO Auto-generated method stub
		
	}
}
