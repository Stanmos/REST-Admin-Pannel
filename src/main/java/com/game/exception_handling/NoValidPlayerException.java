package com.game.exception_handling;

public class NoValidPlayerException extends RuntimeException{

    public NoValidPlayerException(String message){
        super(message);
    }
}
