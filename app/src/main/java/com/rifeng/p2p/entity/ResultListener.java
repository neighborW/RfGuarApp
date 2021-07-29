package com.rifeng.p2p.entity;

import org.json.JSONObject;

public interface ResultListener {
    void successResponse(JSONObject response) ;
    void faile(String request_error);
}
