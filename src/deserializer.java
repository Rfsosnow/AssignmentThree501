import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.jdom2.Document;
import org.jdom2.Element;


public class deserializer {

	Map<Integer,Element> elementMap = new IdentityHashMap<Integer,Element>();
	List<Object> deserializedObjects = new ArrayList<Object>();
	
	public List<Object> deserialize(Document doc){
				
		Element rootNode = doc.getRootElement();
		List<Element> childrenList = rootNode.getChildren();
		
		addReferencesToMap(childrenList);
		System.out.println("The children list is of the size "+childrenList.size());
		System.out.println("The Element list is of the size "+elementMap.size());

		for(int i = 0; i< childrenList.size();i++){
			Element child = childrenList.get(i);
			String className = child.getAttributeValue("Class");

			if(className.equals("simpleObject")){
				deserializedObjects.add(deserializeSimpleObject(child));
			} else if(className.equals("objectReferencesObject")){
				deserializedObjects.add(deserializeObjectWithReferences(child));
			} else if(className.equals("objectWithArray")){
				deserializedObjects.add(deserializeObjectWithArray(child));
			} else if(className.equals("objectWithObjectArray")){
				deserializedObjects.add(deserializeObjectWithObjectArray(child));
			} else {
				System.out.println("Something went Wrong in loop "+ i);
			}
		}
		
		
		return deserializedObjects;
	}
	
	public void addReferencesToMap(List<Element> list){
		
		for(int i = 0; i<list.size();i++){
			Element listElement = list.get(i);
			String id = listElement.getAttributeValue("id");
			if(id != null){
				if(!elementMap.containsKey(Integer.parseInt(id)));
					elementMap.put(Integer.parseInt(id), listElement);
			}
			List<Element> sublist = listElement.getChildren();
			addReferencesToMap(sublist);
		}
	}

	
	public simpleObject deserializeSimpleObject(Element element){
		List<Element> elementFields = element.getChildren();
		int intParameter = Integer.parseInt(elementFields.get(0).getChild("value").getValue());
		String stringParameter = elementFields.get(2).getChild("value").getValue();
		simpleObject returnObject = new simpleObject(intParameter,stringParameter);
		return returnObject;
	}
	
	
	public objectWithArray deserializeObjectWithArray(Element element){
		Element arrayField = element.getChild("field");
		String referenceID = arrayField.getChild("reference").getValue();
		System.out.println("THE REFERENCE ID IS: "+referenceID);
		Element arrayElement = elementMap.get(Integer.parseInt(referenceID));
		int[] array = deserializeArray(arrayElement);
		
		objectWithArray returnObject = new objectWithArray(array);
		return returnObject;
	}

	
	
	private int[] deserializeArray(Element arrayElement) {
		int[] returnArray = new int[Integer.parseInt(arrayElement.getAttributeValue("Length"))];
		List<Element> arrayComponents = arrayElement.getChildren();
		for(int i = 0; i<arrayComponents.size();i++){
			Element component = arrayComponents.get(i);
			String value = component.getValue();
			returnArray[i] = Integer.parseInt(value);
		}
		return returnArray;
	}
	
	public objectReferencesObject deserializeObjectWithReferences(Element element){
		simpleObject simpleOne = null;
		simpleObject simpleTwo = null;
		
		List<Element> subElements = element.getChildren();
		Element elemOne = elementMap.get(Integer.parseInt(subElements.get(0).getChild("reference").getValue()));
		Element elemTwo = elementMap.get(Integer.parseInt(subElements.get(1).getChild("reference").getValue()));
		simpleOne = deserializeSimpleObject(elemOne);
		simpleTwo = deserializeSimpleObject(elemTwo);
		
		objectReferencesObject returnObject = new objectReferencesObject(simpleOne,simpleTwo);
		return returnObject;
	}
	
	public objectWithObjectArray deserializeObjectWithObjectArray(Element element){
		String referenceValue = element.getChild("field").getChild("reference").getValue();
		int key = Integer.parseInt(referenceValue);
		Element sObjectArray = elementMap.get(key);
		
		simpleObject[] objectArray = deserializeSimpleObjectArray(sObjectArray);
		
		objectWithObjectArray returnObject = new objectWithObjectArray(objectArray);
		return returnObject;
	}

	private simpleObject[] deserializeSimpleObjectArray(Element sObjectArray) {
		String sizeString = sObjectArray.getAttributeValue("Length");
		int size = Integer.parseInt(sizeString);
		
		simpleObject[] returnObject = new simpleObject[size];
		List<Element> arrayChildren = sObjectArray.getChildren();
		for (int i=0;i<size;i++){
			returnObject[i] = deserializeSimpleObject(arrayChildren.get(i));
		}
		return returnObject;
	}
	
	
}
