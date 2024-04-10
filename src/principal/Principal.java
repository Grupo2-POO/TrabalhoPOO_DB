package principal;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import database.DB;
import filemanager.FileManager;
import util.Util;
import menu.ConstantesMenu;
import menu.MenuPrincipal;

public class Principal {
	
	private static boolean programaLigado = true; 
	
	private static Scanner scanner = new Scanner(System.in);		
	
	public static void main(String[] args) {
		
		boolean hasConnectedToDb = conectarAoDatabase();
		
		// cria o menu principal
		MenuPrincipal menu = new MenuPrincipal(ConstantesMenu.menuPrincipal, scanner);

		do {
			System.out.println("Conectado com sucesso!");
			do	{
				
				menu.executarMenu();
				
				programaLigado = conferirProgramaLigado();
			} while (programaLigado); 
		} while (hasConnectedToDb && programaLigado);
		
		System.out.println("\nObrigado(a) por utilizar o programa!");
	}
	
	private static boolean conferirProgramaLigado() {
		int retorna = 0;
		while (retorna != 1 && retorna != 2) {
				retorna = Util.pedirOpcaoInt("\nDigite 1 caso queira prosseguir com programa ligado\n"
											+ "Digite 2 caso queira encerrar o programa", scanner);
		}
		return retorna == 1;
		
	}
	
	public static boolean conectarAoDatabase() {
		
		try (var connection = DB.connect()) {
			
			System.out.println("Conectado ao PostgreSQL database com sucesso!");
			
			if (FileManager.confirmaExist()) {
				return true;
			}
			
			Statement statement = connection.createStatement();
			
			
			statement.executeUpdate(FileManager.readDBCreateFile());
			
			
			statement.executeUpdate(FileManager.readDBInsertFile());
			FileManager.createConfirmationFile();
			
		} catch (SQLException error) {
			System.err.println(error.getMessage());
		}
		
		return false;
	} 

}