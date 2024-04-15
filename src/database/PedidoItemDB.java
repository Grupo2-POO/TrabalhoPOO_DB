package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import classes.Cliente;
import classes.Pedido;
import classes.PedidoItem;
import classes.Produto;
import util.Util;

public class PedidoItemDB implements CRUD<PedidoItem>{
	
	@Override
	public ArrayList<PedidoItem> buscarTodos() {
		
		ArrayList<PedidoItem> relacao = new ArrayList<PedidoItem>();
		
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

				PedidoItem pedidoItem = new PedidoItem(
						 produto.getValorVenda(),
						 response.getDouble("vldesconto"),
						 produto,
						 cliente,
						 // CONFERIR ISSO A ID DO PRODUTO
						 response.getInt("idpedidoitem"),
						 response.getInt("qtproduto"),
						 produto.getIdProduto(),
						 cliente.getIdCliente()
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
	    String sql = "INSERT INTO pedidoitens (idcliente, idproduto, vlunitario, vldesconto, qtproduto, idpedido) " +
	                 "VALUES (?, ?, ?, ?, ?, ?)";
	    
	    try (var conn = DB.connect(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
	        preparedStatement.setInt(1, Integer.parseInt(valores[0]));
	        preparedStatement.setInt(2, Integer.parseInt(valores[1]));
	        preparedStatement.setDouble(3, Double.parseDouble(valores[2]));
	        preparedStatement.setDouble(4, Double.parseDouble(valores[3]));
	        preparedStatement.setInt(5, Integer.parseInt(valores[4]));
	        preparedStatement.setInt(6, Integer.parseInt(valores[5]));
	        
	        preparedStatement.executeUpdate();
	    } catch (SQLException | NumberFormatException e) {
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
	
	public void atualizarIdClientePedidoItens(int idPedido, int novoIdCliente) {
	    String sql = "UPDATE pedidoitens SET idcliente = ? WHERE idpedido = ?";
	    
	    try (var conn = DB.connect(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
	        preparedStatement.setInt(1, novoIdCliente);
	        preparedStatement.setInt(2, idPedido);
	        
	        int rowsUpdated = preparedStatement.executeUpdate();
	        
	        if (rowsUpdated > 0) {
	            System.out.println("ID do cliente nos itens do pedido atualizado com sucesso.");
	        } else {
	            System.out.println("Nenhum item do pedido foi atualizado. Verifique o ID do pedido.");
	        }
	    } catch (SQLException e) {
	        System.err.println(e);
	    }
	}

	
	public void deletarPedidoItens(int idpedido) {
	    String sql = "DELETE FROM pedidoitens WHERE idpedido = ?";
	    
	    try (var conn = DB.connect()) {
	        PreparedStatement preparedStatement = conn.prepareStatement(sql);
	        preparedStatement.setInt(1, idpedido);
	     
	        
	        int rowsDeleted = preparedStatement.executeUpdate();
	        
	        if (rowsDeleted > 0) {
	            System.out.println("O item do pedido foi excluído com sucesso.");
	        } else {
	            System.out.println("Nenhum item do pedido foi excluído. Verifique os IDs do pedido e do produto.");
	        }
	    } catch (SQLException e) {
	        System.err.println(e);
	    }
	}
	
	public void deletarPedidoItens(int idpedido, int idproduto) {
	    String sql = "DELETE FROM pedidoitens WHERE idpedido = ? AND idproduto = ?";
	    
	    try (var conn = DB.connect()) {
	        PreparedStatement preparedStatement = conn.prepareStatement(sql);
	        preparedStatement.setInt(1, idpedido);
	        preparedStatement.setInt(2, idproduto);
	        
	        int rowsDeleted = preparedStatement.executeUpdate();
	        
	        if (rowsDeleted > 0) {
	            System.out.println("O item do pedido foi excluído com sucesso.");
	        } else {
	            System.out.println("Nenhum item do pedido foi excluído. Verifique os IDs do pedido e do produto.");
	        }
	    } catch (SQLException e) {
	        System.err.println(e);
	    }
	}
	
	@Override
	public PedidoItem executarConsultaCompleta(String sql) {
		try (Connection connection = DB.connect()) {
	        Statement statement = connection.createStatement();
	        var response = statement.executeQuery(sql);
	        if (response.next()) {
	        	PedidoItem pedidoitens = new PedidoItem(
						response.getDouble("vlunitario"),
						response.getDouble("vldesconto"),
						response.getInt("idpedidoitem"),
						response.getInt("qtproduto"),
						response.getInt("idproduto"),
						response.getInt("idcliente")
						);
	        	
	        	
	        	return pedidoitens;
	        } else {
	        	return null;
	        }
	        
	    } catch (SQLException error) {
//	        System.err.println(error.getMessage());
	        return null;
	    }
	}

	@Override
	public ArrayList<PedidoItem> executarConsultaCompletaDeTodos(String sql) {
		ArrayList<PedidoItem> todos = new ArrayList<PedidoItem>();
		try (Connection connection = DB.connect()) {
	        Statement statement = connection.createStatement();
	        var response = statement.executeQuery(sql);

	        while (response.next()) {

	        	PedidoItem pedidoitens = new PedidoItem(
						response.getDouble("vlunitario"),
						response.getDouble("vldesconto"),
						response.getInt("idpedidoitem"),
						response.getInt("qtproduto"),
						response.getInt("idproduto"),
						response.getInt("idcliente")
						);
	        	
	        	
	        	todos.add(pedidoitens);
	        }
	        
	    } catch (SQLException error) {
//	        System.err.println(error.getMessage());
	        return null;
	    }
		return todos;
	}


}
