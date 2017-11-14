import java.util.Scanner;

public class objectWithObjectArray {
	simpleObject[] objectArray;
	
	objectWithObjectArray(Scanner scan,int length) {
		System.out.println("Creating an object with an Object Array of length "+length+"\n");
		objectArray = new simpleObject[length];
		for(int i = 0;i<length;i++) {
			objectArray[i] = new simpleObject(scan);
		}
	}
}
