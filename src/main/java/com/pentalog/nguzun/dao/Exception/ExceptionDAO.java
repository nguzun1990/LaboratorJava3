package com.pentalog.nguzun.dao.Exception;

import org.apache.log4j.Logger;

/**
 *
 * @author Guzun
 */
//TODO e un inceput bun, dar trebuie de inbunatatit NotDone
public class ExceptionDAO extends Exception {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionDAO() {
    }

    public ExceptionDAO(String message, Logger log) {
        log.error(message);
    }

    public ExceptionDAO(String message, Throwable rootCause, Logger log) {
        log.error(message, rootCause);
    }

    public ExceptionDAO(Throwable rootCause, Logger log) {
        log.error(rootCause);
    }
}
