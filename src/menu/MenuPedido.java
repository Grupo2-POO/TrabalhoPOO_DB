package menu;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import classes.Cliente;
import classes.Pedido;
import classes.PedidoItem;
import classes.Produto;
import database.ClienteDB;
import database.PedidoDB;
import database.PedidoItemDB;
import database.ProdutoDB;
import util.Util;

public final class MenuPedido extends NossoMenu {
	
	PedidoItemDB pedidoItensDB;
	PedidoDB pedidoDB;
	ProdutoDB produtoDB;
	private ClienteDB clienteDB;
	
	ArrayList<Produto> produtosPedido; 
	
	

	public MenuPedido(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
		// TODO Auto-generated constructor stub
		pedidoItensDB = new PedidoItemDB();
		pedidoDB = new PedidoDB();
		produtoDB = new ProdutoDB();
		clienteDB = new ClienteDB();
		produtosPedido = new ArrayList<>();
	}

	@Override
	public void processarOpcao(int opcao) {
		switch (opcao) {

		case 1: cadastraPedidos(); break;
		case 2: Util.printMessage("Consulta de pedidos vai rolar aqui"); break;
		case 3: alterarPedidos(); break; 
		case 4: {
				// passar uma flag para o menu principal
				NossoMenu.sairMenuDerivado = true;
				break; 
			}
		}
	}
	
	private void cadastraPedidos() {
		
		incluirPedido();

		int continuarCadastrando = 0;
		
		while(continuarCadastrando < 1 || continuarCadastrando > 2) {
			continuarCadastrando = Integer.parseInt(Util.askIntegerInput("\nDeseja cadastrar mais um pedido?\n1 - SIM\n2 - NÃO", scanner));
		}
		
		if(continuarCadastrando == 1) {
			NossoMenu.sairMenuDerivado = false;
			cadastraPedidos();
		} else {
			NossoMenu.sairMenuDerivado = true;
		}
	}
	
	private void alterarPedidos() {
		
		int opcImprimir = 0;
		while(opcImprimir < 1 || opcImprimir > 3) {
			opcImprimir = Integer.parseInt(Util.askIntegerInput("\nComo deseja visualizar os pedidos?\n1 - Pedido + Cliente\n2 - Pedido + Cliente + Produtos?\n3 - Voltar ao Menu", scanner));

		}
		
		boolean mostrarProdutos = opcImprimir == 2;
		
		boolean temPedidos = mostrarPedidos(mostrarProdutos);
		
		if(!temPedidos) {
			NossoMenu.sairMenuDerivado = true;
			return;
		}
		
		MenuBuscarPedido menuBuscarPedido= new MenuBuscarPedido(ConstantesMenu.menuAlterarPedido, scanner);
		
		menuBuscarPedido.executarMenu();
		
		Pedido pedidoAlterar = menuBuscarPedido.getPedido();
		
		if(pedidoAlterar != null) {
		
			int opcAlteracao = 0;
			while(opcAlteracao < 1 || opcAlteracao > 3) {
				
				opcAlteracao = Integer.parseInt(Util.askIntegerInput("O que deseja alterar no pedido: "
						+ "\n 1 - Produtos "
						+ "\n 2 - Observação "
						+ "\n 3 - Cliente", scanner));

			}
			
			switch(opcAlteracao) {
			case 1: alterarProdutos(pedidoAlterar); break;
			case 2: alterarObservação(pedidoAlterar); break;
			case 3: alterarCliente(pedidoAlterar); break;
			default: break;
				
			}
		}
		
		int continuarAlterando = 0;
		while(continuarAlterando < 1 || continuarAlterando > 2) {
			continuarAlterando = Integer.parseInt(Util.askIntegerInput("\nDeseja alterar mais um pedido?\n1 - SIM\n2 - NÃO", scanner));
		}
		
		if(continuarAlterando == 1) {
			alterarPedidos();
		}
	}
	
	private void alterarCliente(Pedido pedidoAlterar) {
		
		int opcao = 0;
        while (opcao < 1 || opcao > 3) {
            opcao = Integer.parseInt(Util.askIntegerInput("\n1 - Remover cliente\n2 - Substituir cliente\n3 - Sair", scanner));
        }
		
		if(opcao == 1) {
			Util.printMessage("A remoção do cliente significa a remoção do pedido!");
			int conf = 0;
	        while (conf < 1 || conf > 2) {
	            conf = Integer.parseInt(Util.askIntegerInput("\n1 - SIM\n2 - NÃO", scanner));
	        }
	        
	        if(conf == 1) {
	        	// remove o pedido e os pedidos itens
	        	pedidoItensDB.deletarPedidoItens(pedidoAlterar.getIdPedido());
	        	pedidoDB.excluirPedidoPorId(pedidoAlterar.getIdPedido());
	        	return;
	        }
	        
	        if(conf == 2) {
	        	alterarCliente(pedidoAlterar);
	        }
			
		} else if(opcao == 2) {
			
			Cliente novoCliente = incluirCliente(false);


			if(novoCliente != null) {
				if(novoCliente.getIdCliente() == pedidoAlterar.getIdCliente()) {
					Util.printMessage("Não dá pra substituir o mesmo cliente, né... que ideia");
					alterarCliente(pedidoAlterar);
				}
				
				pedidoItensDB.atualizarIdClientePedidoItens(pedidoAlterar.getIdPedido(), novoCliente.getIdCliente());
				pedidoDB.atualizarIdClientePedido(pedidoAlterar.getIdPedido(), novoCliente.getIdCliente());	
				
			}
			
		}
	}

	private void alterarObservação(Pedido pedidoAlterar) {
		
		String observacao = "";
		
		if(pedidoAlterar.getObservacaoPed().length() > 0) {
			Util.printMessage("Esta é a observação atual: ");
			Util.printMessage(pedidoAlterar.getObservacaoPed());

		}
		
		observacao = Util.pedeLinha("Insira sua nova observação ", scanner);
		
		pedidoDB.atualizarObservacaoPedido(pedidoAlterar.getIdPedido(), observacao);
		
	}
	
	private void mostrarPedido(Pedido pedido, boolean mostrarProdutos) {
		
		Cliente cliente = clienteDB.buscarUmPor("idcliente", pedido.getIdCliente() + "", "cliente");
		
		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		builder.append(pedido.toString());
		builder.append("\n");
		builder.append("Informações do Cliente:\n");
		builder.append(cliente.toString());
		
		if(mostrarProdutos) {
			builder.append("\n\n\nItens do Pedido:\n");
			
			ArrayList<PedidoItem> pedidoitem = pedidoItensDB.buscarTodosPor("idpedido", pedido.getIdPedido() + "", "pedidoitens");

			for(PedidoItem item : pedidoitem) {
				
				Produto produto = produtoDB.buscarUmPor("idproduto", item.getIdProduto() + "", "produto");
				builder.append("\n" + produto.toStringAlterarPedido());
				builder.append("\t" + item.toStringAlterarPedido());
			}
		}
		
		builder.append("\n==================================================");

		System.out.println(builder.toString());
		
	}
	
	private void alterarProdutos(Pedido pedidoAlterar) {
		
		Produto produtoSelecionado = incluirProduto();
		
		if(produtoSelecionado != null) {
			
			int opcProduto = 0;
			while(opcProduto < 1 || opcProduto > 3) {
				
				opcProduto = Integer.parseInt(Util.askIntegerInput("Deseja "
						+ "\n 1 - Incluir no pedido "
						+ "\n 2 - Remover do pedido "
						+ "\n 3 - Voltar ao menu inicial",
						scanner));
			}
			
			if(opcProduto == 1) {
				int qtdMaxima = produtoSelecionado.getQuantidade();
		     
			    int quantidade = 0;
		        while (quantidade < 1 || quantidade > qtdMaxima) {
		            quantidade = Integer.parseInt(Util.askIntegerInput(
		            		"Estoque atual: "
		            		+ qtdMaxima 
		            		+  "\nInsira a quantidade de produtos.\n", 
		            		scanner));
		            
		
	                if(qtdMaxima == 0) {
		                System.out.println("\nDesculpe, agora que nossa IA percebeu que não temos mais esse produto!\nObrigado por nos avisar!");
		                break;
	                }		            
		            if (quantidade <= 0) {
		                System.out.println("Quantidade não pode ser negativa!");
		            }		            
		            if (quantidade > qtdMaxima) {
		                System.out.println("Quantidade não pode ser maior que o estoque!");
		            }
		        }
		        
		        produtoSelecionado.setQuantidade(qtdMaxima - quantidade);
		        
		        produtoSelecionado.setQuantidadePedido(quantidade);
		        
		        // Adiciona o pedido item
		        
		        String[] dadosPedidoItem = { 
		            String.valueOf(pedidoAlterar.getIdCliente()),
		            String.valueOf(produtoSelecionado.getIdProduto()),
		            String.valueOf(produtoSelecionado.getValorVenda()),
		            String.valueOf(calcularDesconto(produtoSelecionado.getValorVenda(), quantidade)),
		            String.valueOf(quantidade),
		            String.valueOf(pedidoAlterar.getIdPedido())
		        };
		        
		        pedidoItensDB.adicionar(dadosPedidoItem);
		        
		        
				
			} else if(opcProduto == 2) {
				
				// deleta o produto do pedido
				
				// pega os pedidos itens
				
				ArrayList<PedidoItem> pedidoitem = 
						pedidoItensDB.buscarTodosPor(
								"idpedido", 
								pedidoAlterar.getIdPedido() + "", 
								"pedidoitens");
				
				// pega os produtos a partir dos pedidos itens
								
				boolean produtoEstaNoPedido = false;
				
				for(PedidoItem item : pedidoitem) {
					
					Produto produto = produtoDB.buscarUmPor("idproduto", item.getIdProduto() + "", "produto");
					if(produtoSelecionado.getDescricao().equals(produto.getDescricao())) {
						
						produtoEstaNoPedido = true;
						
						// atualiza a quantidade
						
						produtoDB.atualizarQuantidade(
								String.valueOf(produtoSelecionado.getIdProduto()), 
								String.valueOf(produtoSelecionado.getQuantidade() + produto.getQuantidadePedido())
								);
						break;
					}
				}
				
				if(produtoEstaNoPedido) {
					pedidoItensDB.deletarPedidoItens(pedidoAlterar.getIdPedido(), produtoSelecionado.getIdProduto());
					
					Util.printMessage("Produto removido do pedido com sucesso!");

				}

			}

		}
	}
	
	private boolean mostrarPedidos(boolean mostrarProdutos) {

		ArrayList<Pedido> relacaoPedido = pedidoDB.buscarTodosPedidos();
		
		if(relacaoPedido.size() <= 0) {
			Util.printMessage("Não há nenhum pedido registrado!");
			return false;
		}
		
		for(Pedido pedido : relacaoPedido) {
			
			Cliente cliente = clienteDB.buscarUmPor("idcliente", pedido.getIdCliente() + "", "cliente");
			
			if(cliente == null) {
				// pedidos vazios
				continue;
			}
			
			StringBuilder builder = new StringBuilder();
			builder.append("\n");
			builder.append(pedido.toString());
			builder.append("\n");
			builder.append("Informações do Cliente:\n");
			builder.append(cliente.toString());
			
			
			if(mostrarProdutos) {
				builder.append("\n\n\nItens do Pedido:\n");
				
				ArrayList<PedidoItem> pedidoitem = pedidoItensDB.buscarTodosPor("idpedido", pedido.getIdPedido() + "", "pedidoitens");

				for(PedidoItem item : pedidoitem) {
					
					Produto produto = produtoDB.buscarUmPor("idproduto", item.getIdProduto() + "", "produto");
					builder.append("\n" + produto.toStringAlterarPedido());
					builder.append("\t" + item.toStringAlterarPedido());
				}
			}
			
		
			builder.append("\n==================================================");

			System.out.println(builder.toString());

		}
		return true;
	}
	
	public Cliente incluirCliente(boolean novo) {
		if(novo) {
			Util.printMessage("Para efetuar um novo pedido, é necessário informar um cliente ");

		} else {
			Util.printMessage("Busque um novo cliente, por favor");

		}
		
		MenuBuscarCliente buscarCliente = new MenuBuscarCliente(
				ConstantesMenu.menuBuscarCliente, scanner);

		buscarCliente.executarMenu();
		
		Cliente cliente = buscarCliente.getCliente();
		
		while (cliente == null) {
			
			cliente = buscarCliente.getCliente();
			if(NossoMenu.sairMenuDerivado) {
				return null;
			}
			if(cliente == null) {
				Util.printMessage("Cliente não encontrado! Tente de novo!");
				buscarCliente.executarMenu();
			}
		} 
		
		Util.printMessage("Cliente encontrado: ");
		System.out.println(cliente.toString());
		
		return cliente;
	}
	
	public Produto incluirProduto() {
	
		Util.printMessage("\nAgora vem a seleção de produto por codigo");
		
		MenuBuscarProduto buscarProduto = new MenuBuscarProduto(
				ConstantesMenu.menuBuscarProduto, scanner);
		
		buscarProduto.executarMenu();
		
		Produto produto = buscarProduto.getProduto();
		if(produto != null) {
			
			Util.printMessage("\nEsse foi o produto selecionado:");
			System.out.println(produto.toStringSemEstoque());
		}
	
		return produto;		
	}
	
	public void incluirPedido() {
		
		Cliente cliente = incluirCliente(true);

		int idPedido = pedidoDB.adicionarPedido("");
		
		if(NossoMenu.sairMenuDerivado) {
			pedidoDB.excluirPedidosComCamposNulosOuVazios();
			return;
		}
	    // altera o cliente
		
		int opcao = 0;
        while (opcao < 1 || opcao > 2) {
            opcao = Integer.parseInt(Util.askIntegerInput("\nConfirmar cliente?\n1 - SIM\n2 - NÃO", scanner));
        }
        
        if (opcao == 2) {
        	// pra fazer
        	// deletar o pedido vazio que foi criado
			pedidoDB.excluirPedidosComCamposNulosOuVazios();

        	incluirPedido();
        }
        
	    boolean maisUM;
	    do {
	        // pega o produto selecionado a partir do database
	    	Produto produto = incluirProduto();
	    	if(produto != null) {
	    		// confere se já adicionamos esse produto
		    	boolean novoProduto = true;
		    	for(Produto p : produtosPedido) {
		    		if((p.getDescricao().equals(produto.getDescricao()))) {
		    			produto = p;
		    			novoProduto = false;
		    			break;
		    		}
		    	}
		    	
		    	int qtdMaxima = produto.getQuantidade();
		        
		        // confere se o produto já foi adicionado na lista
		        // de produtos desse pedido
			    int quantidade = 0;
		        while (quantidade < 1 || quantidade > qtdMaxima) {
		            quantidade = Integer.parseInt(Util.askIntegerInput(
		            		"Estoque atual: "
		            		+ qtdMaxima 
		            		+  "\nInsira a quantidade de produtos.\n", 
		            		scanner));
		            
		            // lidar melhor com isso?
	                if(qtdMaxima == 0) {
		                System.out.println("\nDesculpe, agora que nossa IA percebeu que não temos mais esse produto!\nObrigado por nos avisar!");
		                break;

	                }
		            
		            if (quantidade <= 0) {
		                System.out.println("Quantidade não pode ser negativa!");
		            }
		            
		            if (quantidade > qtdMaxima) {
		                System.out.println("Quantidade não pode ser maior que o estoque!");
		            }
		        }
		        
		        // atualiza a quantidade no estoque do produto, na lista temporária
		        produto.setQuantidade(qtdMaxima - quantidade);
		        
		        produto.setQuantidadePedido(quantidade);
		        
		        // guarda o produto temporariamente, se necessario
		        if(novoProduto) {
		        	produtosPedido.add(produto);
		        }
		       
		        // Adiciona o pedido item
		        String[] dadosPedidoItem = { 
		            String.valueOf(cliente.getIdCliente()),
		            String.valueOf(produto.getIdProduto()),
		            String.valueOf(produto.getValorVenda()),
		            String.valueOf(calcularDesconto(produto.getValorVenda(), quantidade)),
		            String.valueOf(quantidade),
		            String.valueOf(idPedido)
		        };
		        
		        pedidoItensDB.adicionar(dadosPedidoItem);
	    	
	    	}
	    	
	        // Pergunta se quer adicionar mais produtos
	        int opc = 0;
	        while (opc < 1 || opc > 2) {
	            opc = Integer.parseInt(Util.askIntegerInput("\nDeseja adicionar mais produtos?\n1 - SIM\n2 - NÃO", scanner));
	        }
	        
	        maisUM = (opc == 1);

	    } while (maisUM);
	    
	    if(produtosPedido.size() > 0) {
	    	// pega observação
		    String observacao = pegarObservacaoPedido();
		    
		    double valorTotal = calcularValorTotalPedido();
		    
		    // finaliza o pedido
		    String[] valoresAtributosPedido = { 
		        String.valueOf(cliente.getIdCliente()),
		        String.valueOf(valorTotal),
		        observacao    
		    };
		    
		    // adiciona pedido no db
		    
		    pedidoDB.atualizarPedido(idPedido, valoresAtributosPedido);
		    
		    // Atualiza a quantidade dos produtos (estoque) no db a partir da lista
		    for (Produto produto : produtosPedido) {
		    	// aqui o getQuantidade é a quantidade do produto que sobrou no estoque
		        produtoDB.atualizarQuantidade(String.valueOf(produto.getIdProduto()), String.valueOf(produto.getQuantidade()));
		    }
		    
			Util.printMessage("\nPedido adicionado com sucesso!");
			
			pedidoDB.excluirPedidosComCamposNulosOuVazios();

			this.executarMenu();
	    }
	}
	
	private double calcularDesconto(double valor, int quantidade) {
		
        double desconto = (valor * quantidade <= 100.0)? 0.98 :
			(valor * quantidade <= 250)? 0.95 :
			(valor * quantidade <= 500)? 0.93 :
			(valor * quantidade > 500)? 0.90 : 1.0;
        
        return desconto;
	}
	
	private double calcularValorTotalPedido() {
		// calcula o valor total, a partir dos produtos na lista do pedido
	    double valorTotal = 0;
	  
	    for (Produto produto : produtosPedido) {
	    	
	    	// a quantidade aqui é a quantidade que o usuario escolheu 
	    	
	    	valorTotal += ((produto.getValorVenda() * produto.getQuantidadePedido())
	    			* calcularDesconto(produto.getValorVenda(), produto.getQuantidadePedido()));
	    }
	    
	    return valorTotal;
	}

	private String pegarObservacaoPedido() {
		//String observacao = Util.pedeLinha("\nobservacao: \n", scanner);
		String observacao = "";
		int obsEscolha = 0;
		while(obsEscolha < 1 || obsEscolha > 2) {
			obsEscolha = Integer.parseInt(Util.askIntegerInput("\nDeseja adicionar uma observação?\n1 - SIM\n2 - NÃO", scanner));
		}
		
		if(obsEscolha == 1) {
			observacao = Util.pedeLinha("Insira sua observação ", scanner);	
		}
		
		return observacao;
	}
 
}
