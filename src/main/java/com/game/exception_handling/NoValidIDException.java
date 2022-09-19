package com.game.exception_handling;

public class NoValidIDException extends RuntimeException{

    public NoValidIDException(String message){
        super(message);
    }
}
