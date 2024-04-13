package menu;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import classes.Cliente;
import classes.PedidoItens;
import classes.Produto;
import database.PedidoDB;
import database.PedidoItensDB;
import database.ProdutoDB;
import util.Util;

public final class MenuPedido extends NossoMenu {
	
	PedidoItensDB pedidoItensDB;
	PedidoDB pedidoDB;
	ProdutoDB produtoDB;
	
	ArrayList<Produto> produtosPedido; 

	public MenuPedido(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
		// TODO Auto-generated constructor stub
		pedidoItensDB = new PedidoItensDB();
		pedidoDB = new PedidoDB();
		produtoDB = new ProdutoDB();
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
		
		mostrarPedidos();
		MenuBuscarPedidoItens MenuBuscarPedidoItens = new MenuBuscarPedidoItens(ConstantesMenu.menuAlterarPedido, scanner);
		MenuBuscarPedidoItens.executarMenu();
		
		int continuarAlterando = 0;
		while(continuarAlterando < 1 || continuarAlterando > 2) {
			continuarAlterando = Integer.parseInt(Util.askIntegerInput("\nDeseja alterar mais um pedido?\n1 - SIM\n2 - NÃO", scanner));
		}
		
		if(continuarAlterando == 1) {
			alterarPedidos();
		}
	}
	
	private void mostrarPedidos() {
		ArrayList<PedidoItens> relacaoPedidoItem = pedidoItensDB.buscarTodos();
		for(PedidoItens relacao : relacaoPedidoItem) {
			System.out.println(relacao.toString());
		}
	}
	
	public Cliente incluirCliente() {
		Util.printMessage("Para efetuar um novo pedido, é necessário informar um cliente ");
		
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
	   
		Cliente cliente = incluirCliente();
		
		if(NossoMenu.sairMenuDerivado) {
			return;
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
		    	
		        double vlDesconto = 0.0;
		       
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
		            String.valueOf(produto.getId()),
		            String.valueOf(produto.getValorVenda()),
		            String.valueOf(vlDesconto),
		            String.valueOf(quantidade)
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
		    
		    // confirma o cliente
		    int opc = 0;
	        while (opc < 1 || opc > 2) {
	            opc = Integer.parseInt(Util.askIntegerInput(
	            		"\nConfirma o pedido de"
	            		+ criarRelatorioResumidoProdutosPedido()
	            		+ "\npara " + cliente.getNome() + " ?"
	            		+ "\n1 - SIM\n2 - NÃO", scanner));
	        }
	        
	        if(opc == 2) {
	        	
	        	// pergunta se deseja trocar de cliente?
	        }
		  
		    // finaliza o pedido
		    String[] valoresAtributosPedido = { 
		        String.valueOf(cliente.getIdCliente()),
		        String.valueOf(valorTotal),
		        observacao    
		    };
		    
		    // adiciona pedido no db
		   
		    pedidoDB.adicionar(valoresAtributosPedido);
		    
		    // Atualiza a quantidade dos produtos (estoque) no db a partir da lista
		    for (Produto produto : produtosPedido) {
		    	// aqui o getQuantidade é a quantidade do produto que sobrou no estoque
		        produtoDB.atualizarQuantidade(String.valueOf(produto.getId()), String.valueOf(produto.getQuantidade()));
		    }
	    	
	    }
	}
	
	private String criarRelatorioResumidoProdutosPedido() {
		StringBuilder stringBuilder = new StringBuilder();
		
		for (Produto produto : produtosPedido) {
			stringBuilder.append(produto.toStringQuantidadePedido());
		}
		
		return stringBuilder.toString();
	}
	
	
	private double calcularValorTotalPedido() {
		// calcula o valor total, a partir dos produtos na lista do pedido
	    double valorTotal = 0;
	  
	    for (Produto produto : produtosPedido) {
	    	
	    	// a quantidade aqui é a quantidade que o usuario escolheu 
	    	
	    	valorTotal += (produto.getValorVenda() * produto.getQuantidadePedido());
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
			while(observacao.length() < 3) {
				observacao = Util.pedeLinha("Insira sua observação ", scanner);
				if(observacao.length() == 0) {
					break;
				}
			}
		}
		
		return observacao;
	}
 
}
