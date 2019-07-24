package com.multimedia.controller.utils;

public class NoMediaFoundException extends Exception {
    NoMediaFoundException(){
        super("File cannot be opened.");
    }
}
