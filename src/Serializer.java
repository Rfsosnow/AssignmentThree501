import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.jdom2.*;

public class Serializer {
	private int hashMap = 0;
	Queue<Object> objectQueue = new LinkedList<Object>();
	Map<Object,Integer> objectMap = new IdentityHashMap<Object,Integer>();

	Serializer() {}
	
	public Document serialize(Object obj){
		
		
		Element rootElement = new Element("serialized");
		Document doc = new Document(rootElement);
		//doc.setRootElement(rootElement);
		
		addToMap(obj);

		Element objectElement = recurseSerialize(obj);
		
		doc.getRootElement().addContent(objectElement);
		
		while(objectQueue.peek()!= null) {
			Object nextObject = getNextFromQueue();
			Element recurseElement = recurseSerialize(nextObject);
			doc.getRootElement().addContent(recurseElement);
		}
	
	
		
		return doc;
		
		
	}




	private Element recurseSerialize(Object obj) {
		
		
		//NEEDS TO HANDLE THE SPECIAL CASE OF ARRAYLIST
		
		System.out.println("THE OBJECT NAME IS : "+obj.getClass().getName());
		Element returnElement =  new Element("Object");
		returnElement.setAttribute("Class",obj.getClass().getName());
		returnElement.setAttribute("id",objectMap.get(obj).toString());
		if(obj instanceof Collection<?>) {
			try {
				returnElement.setAttribute("length",obj.getClass().getMethod("size").invoke(obj).toString());
				
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			
		}else if(obj.getClass().isArray()) {
			returnElement.setAttribute("Length",String.valueOf(Array.getLength(obj)));
			if(isPrimitive(obj.getClass().getComponentType())) {
				for(int i = 0;i<Array.getLength(obj);i++) {
					Element arrayElement = new Element("value").setText(Array.get(obj, i).toString());
					returnElement.addContent(arrayElement);
				}
			}else {
				for(int i=0;i<Array.getLength(obj);i++) {
					addToMap(Array.get(obj, i));
					Element arrayElement = recurseSerialize(Array.get(obj, i));
					returnElement.addContent(arrayElement);
				}
			}
		}else {
		
			Field[] objectFields = obj.getClass().getFields();
			
			for(int i=0;i<objectFields.length;i++) {
				Element fieldElement = new Element("field");
				fieldElement.setAttribute("name",objectFields[i].getName());
				fieldElement.setAttribute("declaringclass",obj.getClass().getName());
	
				if (isPrimitive(objectFields[i])) {
					Object value;
					try {
						value = objectFields[i].get(obj);
						fieldElement.addContent(new Element("value").setText(value.toString()));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
					
				}else {
					addToQueue(getFieldUnderlyingObject(objectFields[i],obj));
					fieldElement.addContent(new Element("reference").setText(objectMap.get(getFieldUnderlyingObject(objectFields[i],obj)).toString()));
				}
				returnElement.addContent(fieldElement);
			}
		}
		return returnElement;
	}

	
	private boolean isPrimitive(Class<?> componentType) {
		if(componentType.isPrimitive() || componentType.equals(String.class)) {
			return true;
		}
		return false;
	}

	private Object getFieldUnderlyingObject(Field field,Object obj) {
		Object returnObject;
		try {
			returnObject = field.get(obj);
			return returnObject;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean isPrimitive(Field field) {
		if(field.getType().isPrimitive()) {
			return true; 
		}else if(field.getType().equals(String.class)){
			return true;
		}else {
			return false;
		}
	}

	private void addToQueue(Object obj) {
		System.out.println("Adding Object to QUeue");
		if(!objectMap.containsValue(obj)) {
			objectQueue.add(obj);
			System.out.println("Added Object to QUeue");
			addToMap(obj);
		}
	}
	
	private Object getNextFromQueue() {
		System.out.println("Getting Object froms QUeue");
		return objectQueue.remove();
	}
	
	private void addToMap(Object obj) {
		if(!objectMap.containsValue(obj)) {
			objectMap.put(obj,hashMap);
			hashMap++;
		}
	}
	
	
}



/*
Element objectElement = new Element("object");
objectElement.setAttribute("Class",obj.getClass().getName());
objectElement.setAttribute("id",objectMap.get(obj).toString());

Field[] objectFields = obj.getClass().getDeclaredFields();
objectFields = fieldAccessibility(objectFields);

for(int i=0;i<objectFields.length;i++) {
	Element fieldElement = new Element("field");
	fieldElement.setAttribute("name",objectFields[i].getName());
	fieldElement.setAttribute("declaringclass",obj.getClass().getName());
	//create element with the attributes name="fieldname", declaringclass="parentnode"
	if (isPrimitive(objectFields[i])) {
		Object value;
		try {
			value = objectFields[i].get(obj);
			fieldElement.addContent(new Element("value").setText(value.toString()));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}else {
		addToMap(objectFields[i]);
		addToQueue(objectFields[i]);
		fieldElement.addContent(new Element("reference").setText(objectMap.get(objectFields[i]).toString()));
	}
	objectElement.addContent(fieldElement);
}
*/