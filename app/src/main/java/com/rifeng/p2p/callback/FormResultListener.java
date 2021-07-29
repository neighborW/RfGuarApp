package com.rifeng.p2p.callback;
import okhttp3.Response;

public interface FormResultListener {
    void successResponse(Response response);
    void faile(String request_error);
}
