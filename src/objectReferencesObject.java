import java.util.Scanner;

public class objectReferencesObject {
	simpleObject objectOne;
	simpleObject objectTwo;
	
	objectReferencesObject(Scanner scan){
		System.out.println("Creating first simpleObject. \n");
		objectOne = new simpleObject(scan);
		System.out.println("Creating second simpleObject. \n");
		objectTwo = new simpleObject(scan);
	}

}
