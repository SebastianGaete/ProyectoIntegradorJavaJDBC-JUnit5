package org.sebastian.exceptions;

public class TableDontNotExistException extends RuntimeException{

    public TableDontNotExistException(String message){
        super(message);
    }

}
