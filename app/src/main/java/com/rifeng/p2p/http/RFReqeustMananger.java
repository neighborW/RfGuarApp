package com.rifeng.p2p.http;

import android.content.Context;


import com.rifeng.p2p.entity.ResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class RFReqeustMananger {


    private static RFReqeustMananger rfReqeustMananger;

    public static RFReqeustMananger getInstance() {
        if (rfReqeustMananger == null) {
            rfReqeustMananger = new RFReqeustMananger();
        }
        return rfReqeustMananger;
    }

}
