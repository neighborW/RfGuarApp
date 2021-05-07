package com.rifeng.p2p.api;

import java.io.IOException;

import okhttp3.Response;

public interface TitleCallback {

    void onSuccess(String res);

    void onFailure(String e);


}
