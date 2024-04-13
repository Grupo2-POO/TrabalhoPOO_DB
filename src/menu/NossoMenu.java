package menu;

import java.util.Scanner;

import util.Util;

public abstract class NossoMenu {
	
	private String[] constantes;
	
	protected static Scanner scanner;
	
	public static boolean sairMenuDerivado;
	
	public NossoMenu(String[] constantes, Scanner scanner) {
		super();
		this.constantes = constantes;

		NossoMenu.scanner = scanner;
	}
	
	public void executarMenu() {
		mostrar();
		processarOpcao(pegarOpcaoInt());
	}
	
	private void mostrar() {
		for(int i = 0; i < constantes.length; i++) {
			System.out.print(constantes[i]);
			if(i == 0) {
				System.out.println();
			}
			System.out.println(Util.linhaSimples(40));
			
		}
	}
	
	private int pegarOpcaoInt() {
		int opcao = 0;
		while (opcao < 1 || opcao > constantes.length + 1) {
				opcao = Util.pedirOpcaoInt("Digite uma opção válida", scanner);
		}
		return opcao;
	}
	
	public abstract void processarOpcao(int opcao);

}