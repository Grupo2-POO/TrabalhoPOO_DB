package filemanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class FileManager {
	// manages different files needed for the project

	// prevents instantiation - objeto
	private FileManager() {
		throw new AssertionError();
	}
	
	public static boolean dbPropertiesExist() {
        // Specify the relative path to your src folder
        String path = "src/db.properties";

        // Create a File object for the specified path
        File file = new File(path);

        // Check if the file exists
        return file.exists();
    }
	
	public static boolean confirmaExist() {
		String path = "src/confirmainicio";
		File file = new File(path);
		return file.exists();
	}
	
	public static void createDBProperties(String dbUsername, String dbPassword) {
        // Specify the relative path to your src folder
        String path = "src/db.properties";

        try (BufferedWriter out = new BufferedWriter(new FileWriter(path))) { // padrao para criar um arquivo utilizando o path
            // Write database properties to the file
        	out.write("db.url=jdbc:postgresql://localhost:5432/grupo2db" + "\n");
            out.write("db.username=" + dbUsername + "\n");
            out.write("db.password=" + dbPassword + "\n");
            out.flush(); // garantir que nao vai ter nada mais escrito no arquivo
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void createConfirmationFile() {
		
		String path = "src/confirmainicio";
		
		try (BufferedWriter out = new BufferedWriter(new FileWriter(path))) { 
			out.write("Inicializacao feita com sucesso!");
			out.flush();
			out.close();
					
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static String readDBCreateFile() {
        // Specify the relative path to your src folder
        String path = "src/db.create";
        
        return readFile(path);
        
    }

	public static String readDBInsertFile() {
		
		String path = "src/db.insert";
		

        return readFile(path);
    }
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	
	private static String readFile(String path) { 
		// Funcao ler, ela vai receber os paths necessarios para efetuar a leitura do arquivo
		
		StringBuilder content = new StringBuilder(); 
		
		// Otimizacao para criar uma String - utiliza-se o Builder

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) { 
        	
        	//ler o arquivo, bem parecido com criar o arquivo do path
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
	}
}
