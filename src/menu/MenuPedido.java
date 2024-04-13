package menu;

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

	public MenuPedido(String[] constantes, Scanner scanner) {
		super(constantes, scanner);
		// TODO Auto-generated constructor stub
		pedidoItensDB = new PedidoItensDB();
		pedidoDB = new PedidoDB();
		produtoDB = new ProdutoDB();
	}

	

	@Override
	public void processarOpcao(int opcao) {
		switch (opcao) {

		case 1: cadastraPedidos(); break;
		case 2: Util.printMessage("Consulta de pedidos vai rolar aqui"); break;
		case 3: alterarPedidos(); break; 
		case 4: {
				MenuBuscarPedidoItens MenuBuscarPedidoItens = new MenuBuscarPedidoItens(ConstantesMenu.menuBuscarPedidoItens, scanner);
				MenuBuscarPedidoItens.executarMenu();
				break; 
			}
		}
	}
	
	private void cadastraPedidos() {
		incluirPedido();
	}
	
	private void alterarPedidos() {
		mostrarPedidos();
		
		System.out.println("Informe o codigo do pedido que deseja alterar: ");
		int opc;
		
		MenuBuscarPedidoItens MenuBuscarPedidoItens = new MenuBuscarPedidoItens(ConstantesMenu.menuBuscarPedidoItens, scanner);
		MenuBuscarPedidoItens.executarMenu();
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
	
		Util.printMessage("\nEsse foi o produto selecionado:");

		System.out.println(produto.toStringSemEstoque());
		
		return produto;		
	}
	
	public void incluirPedido() {
		Cliente cliente = incluirCliente();
		
		Map<String, String> produtoENovaQuantidade = new HashMap<>();
		
		boolean maisUM = false;
				
		do {
			Produto produto = incluirProduto();
			
			double vlDesconto = 0.0;
			
			int quantidade = 0;
			// antes de decidir a quantidade maxima, checa o mapa
			// que guarda temporariamente as quantidades dos produtos
			int qtdMaxima = 0;
			if(produtoENovaQuantidade.containsKey(String.valueOf(produto.getId()))) {
		        // Se o produto já foi adicionado, atualiza a qtdMaxima
		        int qtdAdicionada = Integer.parseInt(produtoENovaQuantidade.get(String.valueOf(produto.getId())));
		        qtdMaxima = produto.getQuantidade() - qtdAdicionada;
		    } else {
		        // Se o produto ainda não foi adicionado, a qtdMaxima é a quantidade total no estoque
		        qtdMaxima = produto.getQuantidade();
		    }
		
			
			while(quantidade < 1 || quantidade > qtdMaxima) {
				
				quantidade = Integer.parseInt(Util.askIntegerInput("\nInsira a quantidade de produtos. Estoque atual: " + qtdMaxima +  "\n", scanner));
				
				if(quantidade <= 0) {
					System.out.println("Quantidade não pode ser negativa!");
				}
				
				if(quantidade > qtdMaxima) {
					System.out.println("Quantidade não pode ser maior que o estoque!");
				}
			}
			
			String[] dadosPedidoItem = { 
					String.valueOf(cliente.getIdCliente()),
					String.valueOf(produto.getId()),
					String.valueOf(produto.getValorVenda()),
					String.valueOf(vlDesconto),
					String.valueOf(quantidade)
			};
			
			// salva o produto id e qtdExtra pra atualizar
			
			produtoENovaQuantidade.put(dadosPedidoItem[1], dadosPedidoItem[4]);
					
			// adiciona o pedido item
			pedidoItensDB.adicionar(dadosPedidoItem);
		
			// pergunta se quer adicionar mais produtos
			int opc = 0;
			while(opc < 1 || opc > 2) {
				opc = Integer.parseInt(Util.askIntegerInput("\nDeseja adicionar mais produtos?\n1 - SIM\n2 - NÃO", scanner));
			}
			
			if(opc == 1) {
				maisUM = true;
				
			}else if(opc == 2) {
				
				maisUM = false;
				// terminou de inserir pedidos itens
				
				String observacao = pegarObservacaoPedido();
				
				
				// insere o pedido
				String[] valoresAtributosPedido = { 
						String.valueOf(cliente.getIdCliente()),
						observacao	
				};
				// adiciona o pedido
				pedidoDB.adicionar(valoresAtributosPedido);
				
				// atualiza a quantidade dos produtos, tirando do map
				for(Map.Entry<String, String> p : produtoENovaQuantidade.entrySet()) {
					String idproduto = p.getKey();
					String quantidadeRetirada = p.getValue();
					
					produtoDB.atualizarQuantidade(idproduto, quantidadeRetirada);
				}
			}
					
		}while(maisUM);
		
		
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
				observacao = Util.pedeLinha("Insira sua observação: ", scanner);
			}
		}
		
		return observacao;
	}
 
}
