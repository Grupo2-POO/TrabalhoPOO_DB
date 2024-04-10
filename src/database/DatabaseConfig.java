package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;
import filemanager.FileManager;
import util.Util;

public class DatabaseConfig {
	
    private static final Properties properties = new Properties();
    
    public static void initializeDB() {
    	if(!FileManager.dbPropertiesExist()) {
			Util.printMessage("Não foi possível encontrar o arquivo 'db.properties'. Isso deve significar que você está executando isso pela primeira vez!");
			Util.printMessage("\nDeve criar um arquivo 'db.properties' e um novo banco de dados 'grupo2db'.");
			Scanner inputScanner = new Scanner(System.in);
			String user = "", password = "";
			boolean success = false;
			while (!success) {
			     user = Util.askInputLine("\nDigite seu usuario PostgreSQL: ", inputScanner);
			     password = Util.askInputLine("\nDigite sua senha PostgreSQL: ", inputScanner);
			     
			     String url = "jdbc:postgresql://localhost:5432/";
			     try (Connection connection = DriverManager.getConnection(url, user, password);
			          Statement statement = connection.createStatement()) {
			    	 // vai usar isso para consultar o SQL database - Statement
			    	 // sempre que tentar a conexao com o database deve ser criado o Statement para traduzir a String -> SQL
			    	 // execute a query to test if the db exists
				     ResultSet resultSet = statement.executeQuery("SELECT 1 FROM pg_database WHERE datname = '" + "grupo2db" + "'");
				     if(resultSet.next()) {
				    	 Util.printMessage("database encontrado e ele sera utilizado!");
				     } else {
				    	 // Execute SQL command to create the database
				         statement.executeUpdate("CREATE DATABASE grupo2db");
				         System.out.println("Database criado com sucesso!");
				     }
				     success = true;
			       
			     } catch (SQLException e) {
			         e.printStackTrace();
			         Util.printMessage(" Falha ao conectar com o database. Tente novamente.");
			     }
			}
				
			FileManager.createDBProperties(user, password);              
		 }
			 
    	loadProperties();
			 
    }
    
    private static void loadProperties() {
        // Specify the path to the properties file
        String filePath = "src/db.properties";

        try (InputStream input = new FileInputStream(filePath)) {
            // Load the properties file
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDbUrl() {
    
        return properties.getProperty("db.url");
    }

    public static String getDbUsername() {
    
        return properties.getProperty("db.username");
    }

    public static String getDbPassword() {
    	
        return properties.getProperty("db.password");
    }
}