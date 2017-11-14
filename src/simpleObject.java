import java.util.Scanner;

public class simpleObject {
	int intField;
	String testString = "Test String";
	String stringField;
	
	public simpleObject(Scanner scan) {
		System.out.println("Creating simple object. \n");
		boolean correctInput = false;
		do {
			try {
				System.out.println("Please enter integer value:\n");
				int intInput = scan.nextInt();
				correctInput = true;
				intField = intInput;
			}catch(Exception e) {
				System.out.println("The input must be an int.\n");
			}
			scan.nextLine();
		}while(!correctInput);
		System.out.println("Please enter a String value: \n");
		stringField = scan.nextLine();
	}
	
	public void setInt(int input) {
		intField = input;
	}
	public void setString(String input) {
		stringField = input;
	}

}
