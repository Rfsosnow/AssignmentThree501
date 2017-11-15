import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;


public class deserializer {

	Map<Integer,Element> elementMap = new IdentityHashMap<Integer,Element>();
	List<Object> deserializedObjects = new ArrayList<Object>();
	
	public Object deserialize(Document doc){
				
		Element rootNode = doc.getRootElement();
		List<Element> childrenList = rootNode.getChildren();
		
		addReferencesToMap(childrenList);
		
		for(int i = 0; i< childrenList.size();i++){
			Element child = childrenList.get(i);
			String className = child.getAttributeValue("class");
			if(className == "simpleObject"){
				deserializedObjects.add(deserializeSimpleObject(child));
			} else if(className == "objectReferencesObject"){
				deserializedObjects.add(deserializeObjectWithReferences(child));
			} else if(className == "objectWithArray"){
				deserializedObjects.add(deserializeObjectWithArray(child));
			} else if(className == "objectWithObjectArray"){
				deserializedObjects.add(deserializeObjectWithObjectArray(child));
			}
		}
		
		
		return null;
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
	
	public void deserializeChild(Element element){
		//get the name of the element from class attribute
		//create a new Object reflectively of the type name
		//iterate through each of the objects fields, getting
		//their name, and setting the field of the made object
		//with the value element inside each of those 
		
		
	}
	
	public simpleObject deserializeSimpleObject(Element element){
		simpleObject returnObject = new simpleObject(Integer.parseInt(element.getAttribute("intField").getValue()),element.getAttribute("stringField").getValue());
		return returnObject;
	}
	public objectWithArray deserializeObjectWithArray(Element element){
		Element arrayField = element.getChild("field");
		String referenceID = arrayField.getChild("reference").getValue();
		Element arrayElement = elementMap.get(Integer.parseInt(referenceID));
		int[] array = deserializeArray(arrayElement);
		
		objectWithArray returnObject = new objectWithArray(array);
		return returnObject;
	}

	private int[] deserializeArray(Element arrayElement) {
		int[] returnArray;
		List<Element> arrayComponents = arrayElement.getChildren();
		returnArray = new int[arrayComponents.size()];
		for(int i = 0; i<arrayComponents.size();i++){
			Element component = arrayComponents.get(i);
			Element value = component.getChild("value");
			returnArray[i] = Integer.parseInt(value.getValue());
		}
		return returnArray;
	}
	
	public objectReferencesObject deserializeObjectWithReferences(Element element){
		simpleObject simpleOne = null;
		simpleObject simpleTwo = null;
		
		List<Element> subElements = element.getChildren();
		simpleOne = deserializeSimpleObject(subElements.get(0));
		simpleTwo = deserializeSimpleObject(subElements.get(1));
		
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
