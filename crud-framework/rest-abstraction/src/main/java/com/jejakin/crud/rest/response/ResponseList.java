
package com.jejakin.crud.rest.response;

import java.util.HashMap;

public class ResponseList {

    private HashMap<Integer, BaseResponse<String>> listResponse;

    public ResponseList() {
        listResponse = new HashMap<>();

        listResponse.put(200, new BaseResponse<String>(true, "OK", null));
        listResponse.put(201, new BaseResponse<String>(true, "Created", null));
        listResponse.put(204, new BaseResponse<String>(true, "No Content", null));
        listResponse.put(400, new BaseResponse<String>(false, "Bad Request", null));
        listResponse.put(401, new BaseResponse<String>(false, "Unauthorized", null));
        listResponse.put(403, new BaseResponse<String>(false, "Forbidden", null));
        listResponse.put(404, new BaseResponse<String>(false, "Not Found", null));
        listResponse.put(405, new BaseResponse<String>(false, "Method Not Allowed", null));
        listResponse.put(409, new BaseResponse<String>(false, "Conflict", null));
        listResponse.put(500, new BaseResponse<String>(false, "Internal Server Error", null));
        listResponse.put(503, new BaseResponse<String>(false, "Service Unavailable", null));

    }

    public void addResponse(Integer key, BaseResponse response) {
        listResponse.put(key, response);
        }

    public BaseResponse getResponse(Integer key) {
        return listResponse.get(key);
    }

    public void removeResponse(Integer key) {
        listResponse.remove(key);
    }

    
}
