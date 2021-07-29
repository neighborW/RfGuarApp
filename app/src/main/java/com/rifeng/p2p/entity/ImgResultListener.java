package com.rifeng.p2p.entity;

import okhttp3.Response;

public interface ImgResultListener {
    void successResponse(Response response);
    void faile(String request_error);
}
