import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class objectWithCollection {
	List<simpleObject> objectCollection = new ArrayList<simpleObject>();
	
	objectWithCollection(Scanner scan){
		System.out.println("Creating an object with a collection. \n");
		boolean keepAdding = true;
		while(keepAdding) {
			simpleObject object = new simpleObject(scan);
			System.out.println("Adding object to the collection\n");
			objectCollection.add(object);
			System.out.println("Do you want to add an object to the collection? \n [Y for yes, anything else for no] \n");
			String input = scan.nextLine();
			if (input == "Y") {
				keepAdding = true;
			}else {
				keepAdding = false;
			}
		}
		System.out.println("Finished adding objects to the collection.\n");
	}
}
