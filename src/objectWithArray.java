import java.util.Scanner;

public class objectWithArray {
	public int[] intArray;
	
	objectWithArray(Scanner scan, int length){
		System.out.println("Creating Object with Array \n");
		intArray = new int[length];
		for (int i = 0; i<length; i++) {
			boolean correctInput = false;
			do {
				try {
					System.out.format("Please enter integer value for position %d:\n",i);
					int intInput = scan.nextInt();
					correctInput = true;
					intArray[i] = intInput;
				}catch(Exception e) {
					System.out.println("The input must be an int. \n");
				}
				scan.nextLine();
			}while(!correctInput);
		}
	}

	public objectWithArray(int[] array) {
		intArray = array;
	}
}
