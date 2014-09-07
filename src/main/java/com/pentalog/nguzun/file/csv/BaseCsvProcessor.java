package com.pentalog.nguzun.file.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.pentalog.nguzun.file.BaseFileProcessor;
import com.pentalog.nguzun.vo.BaseValueObject;


/**
 *
 * @author Guzun
 */
public abstract class BaseCsvProcessor<T extends BaseValueObject> implements BaseFileProcessor<T>{

	protected static final Logger log = Logger.getLogger(BaseCsvProcessor.class.getName());
	
    public Collection<T> readEntitiesFromFile(String pathFile) {
        Collection<T> list = new ArrayList<T>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        //TODO aici ai nevoie de mai multe validari       
        try {
            br = new BufferedReader(new FileReader(pathFile));
            while ((line = br.readLine()) != null) {
                String[] record = getRecords(line, cvsSplitBy);
                T entity = this.createEntity(record);
                if (entity != null) {
                    list.add(entity);
                }
            }

        } catch (FileNotFoundException e) {
        	log.error("readEntitiesFromFile: an error csv was occured: " + e.getMessage(), e);
        } catch (IOException e) {
        	log.error("readEntitiesFromFile: an error csv was occured: " + e.getMessage(), e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                	log.error("readEntitiesFromFile: an error csv was occured: " + e.getMessage(), e);
                }
            }
        }
        
        return list;
    }

    private String[] getRecords(String line, String cvsSplitBy) {
        return line.split(cvsSplitBy);
    }

    public void writeEntityToFile(T entity, String pathFile) {
    	String cvsSplitBy = ",";
        try {
            FileWriter writer = new FileWriter(pathFile);
            String str = null;
            str = this.createStringForEntity(entity, cvsSplitBy);
            writer.append(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
        	log.error("writeEntityToFile: an error csv was occured: " + e.getMessage(), e);
        }
    }
    
    public void writeEntitiesToFile(Collection<T> list, String pathFile) {

        String cvsSplitBy = ",";
        try {
            FileWriter writer = new FileWriter(pathFile);
            String str = null;
            for (T entity : list) {
                str = this.createStringForEntity(entity, cvsSplitBy);
                writer.append(str);
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
        	log.error("writeEntitiesToFile: an error csv was occured: " + e.getMessage(), e);
        }
    }


    abstract public T createEntity(String[] record);

    abstract public String createStringForEntity(T entity, String cvsSplitBy);

}
