package util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public final class Util {
	private static NumberFormat NF = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
	
	public static String converterMonetario(double valor) {
		return NF.format(valor);
	}
	
	public static String novaDT(Date dt) {
		Date currentDate = dt;

	    // Define your desired locale
	    Locale locale = new Locale("pt", "BR"); // Brazilian Portuguese locale

	    // Create SimpleDateFormat with your desired date format pattern and locale
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", locale);

	    // Format the date using SimpleDateFormat
	    String formattedDate = dateFormat.format(currentDate);
	    
	    return formattedDate;
	}
	
	public static String linhaSimples(int n) {
		String result = "-";
        for (int i = 0; i < n; i++) {
            result += "-";
        }
        return result;
    }

	private Util() {
		throw new AssertionError();
	}
	
	public static void printMessage(String message) {
		System.out.println(message);
		try {
		    Thread.sleep(500); // Sleep for milliseconds
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
	}
	
	public static String pedeLinha(String message, Scanner input) {
	    System.out.println(message);
	    StringBuilder rawBuilder = new StringBuilder();
	    input.nextLine();
	    String line = input.nextLine(); // Read the first line of input

	    // Check if the first line is empty (Enter key pressed)
	    if (line.isEmpty()) {
	        return ""; // Return empty string if Enter key was pressed immediately
	    }

	    // Append the first line of input to the StringBuilder
	    rawBuilder.append(line);
	    rawBuilder.append("\n"); // Add newline character to maintain line breaks

	    return rawBuilder.toString().trim();
	}

	
	public static String askInputLine(String message, Scanner input) {
	    //input.next(); // Consume the newline character from previous input
	    System.out.println(message);
	    System.out.println("Pressione enter para continuar");

	    StringBuilder rawBuilder = new StringBuilder();
	    String line = input.next(); // Read the first line of input

	    // Check if the first line is empty (Enter key pressed)
	    if (line.isEmpty()) {
	        return ""; // Return empty string if Enter key was pressed immediately
	    }

	    // Append the first line of input to the StringBuilder
	    rawBuilder.append(line);
	    rawBuilder.append("\n"); // Add newline character to maintain line breaks

	    return rawBuilder.toString().trim();
	}

	public static String askIntegerInput(String message, Scanner input) {
		String inputString = "";	
		while(!inputString.matches("-?\\d+")) {
			System.out.println(message);
			inputString = input.next();
			if(!inputString.matches("-?\\d+")) {
				System.out.println("\nInput inválido!");
			}
		}
		return inputString;
	}
	
	public static int pedirOpcaoInt(String message, Scanner input) {
		String inputString = "";	
		while(!inputString.matches("-?\\d+")) {
			System.out.println(message);
			inputString = input.next();
			if(!inputString.matches("-?\\d+")) {
				System.out.println("\nInput inválido!");
			}
		}
		return Integer.parseInt(inputString);
	}
	
	public static String askDoubleInput(String message, Scanner input) {
		String inputString = "";	
		while(!inputString.matches("-?\\d+(\\.\\d+)?")) {
			System.out.println(message);
			inputString = input.next();
			if(!inputString.matches("-?\\d+(\\.\\d+)?")) {
				System.out.println("\nInput inválido!");
			}
		}
		return inputString;
	}
	
	public static boolean validateInput(Scanner input) {
		String message = "Can we proceed? Input 1 for YES or 2 for NO";
		int choice = 0;
		while(choice <= 0 || choice > 2) {
			choice = Integer.parseInt(askIntegerInput(message, input));
			if(choice <= 0 || choice > 2) {
				System.out.println(" 1 for Yes 2 for No, please.");
			}
		}
		return choice == 1;
	}
	
	public static void wait(int secs_to_sleep) {
		try {
		    Thread.sleep(secs_to_sleep * 1000);
		} catch (InterruptedException ie) {
		    Thread.currentThread().interrupt();
		}
	}
	
	
}
