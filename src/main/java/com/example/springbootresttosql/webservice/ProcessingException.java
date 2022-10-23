package com.example.springbootresttosql.webservice;

/**
 * Exception class for our controllers (see exception handing chapter in Effective Java - this is an examnple of
 * coding to an abstraction.)
 * */
public class ProcessingException extends Exception {

    /**
     * Construct an exception.
     * By providing only one constructor we force client code to provide a message and the underlying exception that caused
     * the exception to be thrown.
     * @param message helpful message explaining the cause of the exception
     * @param cause actual cause of the exception
     */
    public ProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
