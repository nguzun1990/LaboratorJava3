package com.pentalog.nguzun.file;

import java.util.Collection;

import com.pentalog.nguzun.vo.BaseValueObject;
import com.pentalog.nguzun.vo.Group;


/**
 *
 * @author Guzun
 */
public interface BaseFileProcessor<T extends BaseValueObject>{
	
    abstract public Collection<T> readEntitiesFromFile(String pathFile);


    abstract public void writeEntitiesToFile(Collection<T> list, String pathFile) throws Exception;

}
