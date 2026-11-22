package com.myprojects.productcatalogservice.exceptions;

import org.springframework.http.HttpStatusCode;

public class ThirdPartyApiException extends RuntimeException{
    private HttpStatusCode httpStatusCode;
    public ThirdPartyApiException(String message, HttpStatusCode httpStatusCode){
        super(message);
        this.httpStatusCode = httpStatusCode;
    }
    public HttpStatusCode getHttpStatusCode(){
        return httpStatusCode;
    }
}
