package com.pentalog.nguzun.file.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.pentalog.nguzun.file.BaseFileProcessor;
import com.pentalog.nguzun.vo.BaseValueObject;


/**
 *
 * @author Guzun
 */
public abstract class BaseXmlProcessor<T extends BaseValueObject> implements BaseFileProcessor<T>{

	protected static final Logger log = Logger.getLogger(BaseXmlProcessor.class.getName());
	
	protected String getStringProperty(String key, Element eElement) {
		String result  = eElement.getElementsByTagName(key).item(0).getTextContent();
		
		return result;
	}
	
	protected int getIntProperty(String key, Element eElement) throws NumberFormatException {
		int result =  Integer.parseInt(eElement.getElementsByTagName(key).item(0).getTextContent());
		
		return result;
	}

	@Override
	public Collection<T> readEntitiesFromFile(String pathFile) throws Exception {		
		Collection<T> list = new ArrayList<T>();
        File fXmlFile = new File(pathFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 	//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName(getTagName());
			for (int i = 0; i < nodeList.getLength(); i++) {			 
				Node node = nodeList.item(i);
				T entity = createEntity(node);
				if (entity != null) {
                    list.add(entity);
                }
			}
		} catch (SAXException e) {
			throw new Exception("BaseXmlProcessor.readEntityFromFile exception " + e.getMessage());
		}
		
		return list;
	}

	@Override
	public void writeEntitiesToFile(Collection<T> list, String pathFile) throws TransformerException, ParserConfigurationException  {
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 		Document doc = createXmlContent(list, docBuilder);
		
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(pathFile));

		transformer.transform(source, result);

    }
 
    protected Document createXmlContent(Collection<T> list, DocumentBuilder docBuilder) {
    	Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("results");
		doc.appendChild(rootElement);
		
		for (T entity : list) {
			Element userElement = createXmlNode(entity, doc);
			rootElement.appendChild(userElement);
	    }
		
		return doc;
    }
    
    
	abstract protected Element createXmlNode(T entity, Document doc);
	abstract public T createEntity(Node node);
    abstract public String getTagName();

}
