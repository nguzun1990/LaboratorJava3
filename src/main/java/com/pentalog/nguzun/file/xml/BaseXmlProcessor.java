package com.pentalog.nguzun.file.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
	
	protected int getIntProperty(String key, Element eElement) {
		int result  = 0;
		try {
			result =  Integer.parseInt(eElement.getElementsByTagName(key).item(0).getTextContent());
		} catch (NumberFormatException e) {
			result = 0;
		} catch (NullPointerException e) {
			result = 0;
		}
		
		return result;
	}

	@Override
	public Collection<T> readEntitiesFromFile(String pathFile) {		
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
		} catch (ParserConfigurationException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (SAXException | IOException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void writeEntitiesToFile(Collection<T> list, String pathFile) {
        try {
            FileWriter writer = new FileWriter(pathFile);
            String str = null;
            writer.append(getHeader());
            for (T entity : list) {
                str = this.createStringForEntity(entity);
                writer.append(str);
            }
            writer.append(getFooter());
            writer.flush();
            writer.close();
        } catch (IOException e) {
        	log.error("writeEntitiesToFile: an error BaseXmlProcessor was occured: " + e.getMessage(), e);
        }
    }
	
	
	abstract public T createEntity(Node node);

    abstract public String createStringForEntity(T entity);
    
    abstract public String getHeader();
    
    abstract public String getFooter();
    
    abstract public String getTagName();

}
