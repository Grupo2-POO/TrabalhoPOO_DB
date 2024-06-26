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
				p.vltotal,
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
			    pi.vldesconto,
			    pi.idpedidoitem
				FROM 
				    public.pedido p
				JOIN
				    public.cliente c ON p.idcliente = c.idcliente
			    JOIN
				   public.pedidoitens pi ON pi.idcliente = c.idcliente
				JOIN 
				    public.produto pr ON pr.idproduto = pi.idproduto;
			
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
				
				PedidoItem pedidoItens = new PedidoItem(
						 produto.getValorVenda(),
						 response.getDouble("vldesconto"),
						 produto,
						 cliente,
						 response.getInt("idpedidoitem"),
						 response.getInt("qtproduto"),
						 produto.getIdProduto(),
						 cliente.getIdCliente()
						);
				
				Pedido pedido = new Pedido(
						response.getInt("idpedido"),
						response.getDouble("vltotal"),
						response.getDate("dtemissao"),
						response.getDate("dtentrega"),
						response.getString("observacao"),
						pedidoItens
						);
				
				pedidos.add(pedido);
						
			}
		} catch (SQLException error) {
			System.err.println(error.getMessage());
		}
		return pedidos;
	}
	
	public ArrayList<Pedido> buscarTodosPedidos() {
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
		String sql = "SELECT * FROM pedido";
		try (Connection connection = DB.connect()) {
			Statement statement = connection.createStatement();
			// O var eh utilizado para declarar uma variavel sem precisar explicitar o tipo, normalmente utilizado dentro de funcoes de forma encapsulada
			var response = statement.executeQuery(sql);
			// executar a resposta enquanto tiver cliente dentro do array, usando o next 
			while (response.next()) { // String nome, String cpf, Date data_nascimento, int idCliente, String endereco, String telefone
				Pedido pedido = new Pedido(
						response.getInt("idpedido"),
						response.getDouble("vltotal"),
						response.getDate("dtemissao"),
						response.getDate("dtentrega"),
						response.getString("observacao"),
						response.getInt("idcliente")
						);
				pedidos.add(pedido);
				
			}
			
		} catch (SQLException error) {
			System.err.println(error.getMessage());
		}
		return pedidos;
	}

	
	@Override
	public void deletar() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void adicionar(String[] valores) {

		String sql = String.format(
				"insert into %s"
				+ "(idcliente, vltotal, observacao) "
				+ "values('%s','%s', '%s');",
				
				"pedido",
				valores[0],
				valores[1],
				valores[2]
				);
		try(var conn = DB.connect()){
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
		} catch(SQLException e) {
			System.err.println(e);
		}
	
	}
	
	public void adicionarPedido(int idpedidoitem, String observacao) {
		
		System.out.println(" ID: " + idpedidoitem);
		
		String sql = String.format("insert into public.pedido (idpedidoitem, observacao) "
				+ "values ('%d', '%s')", idpedidoitem, observacao);
		
		try(var conn = DB.connect()){
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
		} catch(SQLException e) {
			System.err.println(e);
		}
	}
	
	public void atualizarObservacaoPedido(int idpedido, String novaObservacao) {
	    String sql = "UPDATE public.pedido SET observacao = ? WHERE idpedido = ?";
	    
	    try (var conn = DB.connect(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
	        preparedStatement.setString(1, novaObservacao);
	        preparedStatement.setInt(2, idpedido);
	        
	        int rowsUpdated = preparedStatement.executeUpdate();
	        
	        if (rowsUpdated > 0) {
	            System.out.println("Observação do pedido atualizada com sucesso.");
	        } else {
	            System.out.println("Nenhum pedido foi atualizado. Verifique o ID do pedido.");
	        }
	    } catch (SQLException e) {
	        System.err.println(e);
	    }
	}
	
	public void atualizarIdClientePedido(int idPedido, int novoIdCliente) {
	    String sql = "UPDATE public.pedido SET idcliente = ? WHERE idpedido = ?";
	    
	    try (var conn = DB.connect(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
	        preparedStatement.setInt(1, novoIdCliente);
	        preparedStatement.setInt(2, idPedido);
	        
	        int rowsUpdated = preparedStatement.executeUpdate();
	        
	        if (rowsUpdated > 0) {
	            System.out.println("ID do cliente no pedido atualizado com sucesso.");
	        } else {
	            System.out.println("Nenhum pedido foi atualizado. Verifique o ID do pedido.");
	        }
	    } catch (SQLException e) {
	        System.err.println(e);
	    }
	}


	
	public void atualizarPedido(int idpedido, String[] valores) {
		
	    String sql = String.format("UPDATE public.pedido SET "
	    		+ "observacao = " + "'%s',"
	    		+ "idcliente = " + "'%s',"
	    		+ "vltotal = " + "'%s' "
	    		+ "WHERE idpedido = %d", 
	    		valores[2],
	    		valores[0],
	    		valores[1],
	    		idpedido);
		
		try(var conn = DB.connect()){
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
		} catch(SQLException e) {
			System.err.println(e);
		}
	}
	
	public int adicionarPedido(String observacao) {
			
			String sql = String.format("insert into public.pedido (observacao) "
					+ "values ('%s')", observacao);
			
			try(var conn = DB.connect()){
				var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				int resposta = preparedStatement.executeUpdate();
				
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
	public Pedido executarConsultaCompleta(String sql) {
		Pedido pedido = new Pedido();
		try (Connection connection = DB.connect()) {
	        Statement statement = connection.createStatement();
	        var response = statement.executeQuery(sql);
	        if (response.next()) {
	        		pedido = new Pedido(
						response.getInt("idpedido"),
						response.getDouble("vltotal"),
						response.getDate("dtemissao"),
						response.getDate("dtentrega"),
						response.getString("observacao"),
						response.getInt("idcliente")
						);
	 
	        } else {
	            return null;
	        }
	        
	    } catch (SQLException error) {
//	        System.err.println(error.getMessage());
	        return null;
	    }
		return pedido;
		
	}
	
	public void excluirPedidoPorId(int idPedido) {
	    String sql = "DELETE FROM public.pedido WHERE idpedido = ?";
	    
	    try (var conn = DB.connect(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
	        preparedStatement.setInt(1, idPedido);
	        
	        int rowsAffected = preparedStatement.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("Pedido com ID " + idPedido + " excluído com sucesso.");
	        } else {
	            System.out.println("Nenhum pedido foi excluído. Verifique o ID do pedido.");
	        }
	    } catch (SQLException e) {
	        System.err.println(e);
	    }
	}

	
	public void excluirPedidosComCamposNulosOuVazios() {
	    String sql = "DELETE FROM public.pedido WHERE ";
	    
	    // Verificar se qualquer coluna (observacao, idcliente, vltotal) é nula ou vazia
	    sql += "idcliente IS NULL ";
	    sql += "OR vltotal IS NULL ";
	    sql += "OR dtemissao IS NULL ";
	    sql += "OR dtentrega IS NULL ";
	    
	    try (var conn = DB.connect()){
	        Statement statement = conn.createStatement();
	        int rowsAffected = statement.executeUpdate(sql);
	        
	        if(rowsAffected >= 1) {
	        	 String string = rowsAffected > 1 ? " pedidos vazios excluídos " : "pedido vazio excluído ";
	        	 System.out.println(rowsAffected + string + "com sucesso.");
	        }
	        
	    } catch(SQLException e) {
	        System.err.println(e);
	    }
	}


	@Override
	public ArrayList<Pedido> executarConsultaCompletaDeTodos(String sql) {
		// TODO Auto-generated method stub
		return null;
	}
}