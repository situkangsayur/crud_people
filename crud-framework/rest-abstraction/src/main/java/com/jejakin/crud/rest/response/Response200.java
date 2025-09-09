package com.jejakin.crud.rest.response;

import com.jejakin.crud.rest.response.BaseResponse;

public class Response200 extends BaseResponse<String> {
    
    public Response200(String message) {
        super(true, message, null);
    }

    public Response200(String message, String data) {
        super(true, message, data);
    }
}


