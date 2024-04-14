package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import classes.Cliente;
import classes.Pedido;
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
	public void adicionar(String[] valores) {
		
		String sql = String.format(
				"insert into %s"
				+ "(idcliente, idproduto, vlunitario, vldesconto, qtproduto) "
				+ "values('%s','%s', '%s', '%s', '%s');",
				"pedidoitens",
				valores[0],
				valores[1],
				valores[2],
				valores[3],
				valores[4]
				);
		
//		String sqlAlteracao = String.format("update produto set quantidade = (quantidade - %s) where idproduto = %s;", valores[4], valores[1]);
		
		try(var conn = DB.connect()){
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
//			statement.executeUpdate(sqlAlteracao);
		} catch(SQLException e) {
			System.err.println(e);
		}
	}
	
	public int adicionarPedidoItens(String[] valores) {
		String sql = String.format(
				"insert into %s"
				+ "(idcliente, idproduto, vlunitario, vldesconto, qtproduto) "
				+ "values('%s','%s', '%s', '%s', '%s');",
				"pedidoitens",
				valores[0],
				valores[1],
				valores[2],
				valores[3],
				valores[4]
				);
		
		String sqlAlteracao = String.format("update produto set quantidade = (quantidade - %s) where idproduto = %s;", valores[4], valores[1]);
		
		try(var conn = DB.connect()){
			
			Statement statement = conn.createStatement();
			
			var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int resposta = preparedStatement.executeUpdate();
			
			statement.executeUpdate(sqlAlteracao);
			
			if(resposta > 0) {
				ResultSet chavesInseridas = preparedStatement.getGeneratedKeys();
				if(chavesInseridas.next()) {
					return chavesInseridas.getInt(1);
				}
			}
		} catch(SQLException e) {
			System.err.println(e);
		}
		
		return -1;
	}

	@Override
	public void deletar() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public PedidoItens executarConsultaCompleta(String sql) {
		// TODO Auto-generated method stub
		return null;
	}


}
