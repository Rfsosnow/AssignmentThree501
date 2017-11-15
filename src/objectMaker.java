import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.SAXException;


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
		
		
		Serializer serializer = new Serializer();
		Document doc = serializer.serialize(createdObjects);
		XMLOutputter xmOut = new XMLOutputter();
		xmOut.setFormat(Format.getPrettyFormat());
		
		Writer writer;
		try {
			writer = new FileWriter("XML-output.xml");
			xmOut.output(doc, writer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File xmlFile = new File("XML-output.xml");
		SAXBuilder saxBuilder = new SAXBuilder();
		Document xmlRecreated = null;
		try {
			xmlRecreated = saxBuilder.build(xmlFile);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		deserializer undo = new deserializer();
		List<Object> recreatedObjects = undo.deserialize(xmlRecreated);
		
		Serializer redoSerializer = new Serializer();
		Document redo = redoSerializer.serialize(recreatedObjects);
		try {
			writer = new FileWriter("XML-outputAgain.xml");
			xmOut.output(redo, writer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
