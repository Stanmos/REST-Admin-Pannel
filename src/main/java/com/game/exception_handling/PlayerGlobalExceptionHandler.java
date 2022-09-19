package com.game.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PlayerGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<PlayerIncorrectData> handlerException(
            NoSuchPlayerException exception){

        PlayerIncorrectData data = new PlayerIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.valueOf(404));
    }

    @ExceptionHandler
    public ResponseEntity<PlayerIncorrectData> handlerException(
            NoValidIDException exception){

        PlayerIncorrectData data = new PlayerIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.valueOf(400));
    }

    @ExceptionHandler
    public ResponseEntity<PlayerIncorrectData> handlerException(
            NoValidPlayerException exception){

        PlayerIncorrectData data = new PlayerIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.valueOf(400));
    }

    @ExceptionHandler
    public ResponseEntity<PlayerIncorrectData> handlerException(
            Exception exception){
        PlayerIncorrectData data = new PlayerIncorrectData();
        data.setInfo("Not valid data");

        return new ResponseEntity<>(data, HttpStatus.valueOf(400));
    }


}
