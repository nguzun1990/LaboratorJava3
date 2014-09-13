package com.pentalog.nguzun.dao;

import java.util.Collection;

import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.vo.BaseValueObject;


/**
 *
 * @author Guzun
 */
public interface  BaseDAO<T extends BaseValueObject> {

    Collection<T> retrive() throws ExceptionDAO;

    boolean delete(int id) throws ExceptionDAO;

    boolean update(T baseValueObject) throws ExceptionDAO;

    T retrive(int id) throws ExceptionDAO;

    int create(T baseValueObject) throws ExceptionDAO;
}
