import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class objectMaker {
	static Scanner scan = new Scanner(System.in);
	static List<Object> createdObjects = new ArrayList<Object>();
	
    public static void main(String[] args) {
    	
		
		
		String userInput;
		System.out.println("Would you like to create an object? \n[Y for yes, anything else for no]\n");
		userInput = scan.nextLine();
		char userInputChar = userInput.charAt(0);
		while(userInputChar == 'Y') {
			System.out.println("What object would you like to create? \n "
					+ "1) Simple object\n"
					+ "2) Object with references to objects\n"
					+ "3) Object with array\n"
					+ "4) Object with an array of object references\n"
					+ "5) Object with collection class of other objects\n"
					+ "Please enter the corresponding number.\n");
			String objectChoice = scan.nextLine();
			if((objectChoice.length()!=1) || (objectChoice.charAt(0)<48) ||(objectChoice.charAt(0)>57)) {
				System.out.println("Invalid Entry. Returning to main prompt.\n");
			}else if(objectChoice.charAt(0) == '1') {
				createSimpleObject();
			}else if(objectChoice.charAt(0) == '2') {
				createReferenceObject();
			}else if(objectChoice.charAt(0) == '3') {
				createObjectWArray();
			}else if(objectChoice.charAt(0) == '4') {
				createObjectWObjectArray();
			}else if(objectChoice.charAt(0) == '5') {
				createObjectWCollection();
			}
			System.out.println("Would you like to create another object? \n [Y for yes, anything else for no]\n");
			userInput = scan.nextLine();
			userInputChar = userInput.charAt(0);
		}
		System.out.println("Finished created objects \n");
    }

	private static void createObjectWCollection() {
		objectWithCollection object = new objectWithCollection(scan);
		createdObjects.add(object);
	}

	private static void createObjectWObjectArray() {
		boolean correctInput = false;
		int length = 1;
		do {
			try {
				System.out.println("Please enter the length of the array to create: \n");
				int intInput = scan.nextInt();
				correctInput = true;
				length = intInput;
			}catch(Exception e) {
				System.out.println("The length must be an int.\n");
			}
			scan.nextLine();
		}while(!correctInput);
		objectWithObjectArray object = new objectWithObjectArray(scan,length);
		createdObjects.add(object);
	}

	private static void createObjectWArray() {
		boolean correctInput = false;
		int length = 1;
		do {
			try {
				System.out.println("Please enter the length of the array to create: \n");
				int intInput = scan.nextInt();
				correctInput = true;
				length = intInput;
			}catch(Exception e) {
				System.out.println("The length must be an int.\n");
			}
			scan.nextLine();
		}while(!correctInput);
		objectWithArray object = new objectWithArray(scan,length);
		createdObjects.add(object);
	}

	private static void createReferenceObject() {
		objectReferencesObject object = new objectReferencesObject(scan);
		createdObjects.add(object);
	}

	private static void createSimpleObject() {
		simpleObject object = new simpleObject(scan);
		createdObjects.add(object);
	}
}
