package com.company.hilton.Exceptions;

public class GeoLocationNotFoundException extends RuntimeException{
    public GeoLocationNotFoundException (String msg){
        super(msg);
    }
}
