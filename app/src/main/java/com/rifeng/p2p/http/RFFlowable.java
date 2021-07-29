package com.rifeng.p2p.http;

import android.widget.Toast;


import com.rifeng.p2p.app.BaseApp;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by caixiangyu on 2018/5/16.
 */

public abstract class RFFlowable<T> implements Subscriber<T> {

    protected abstract void onSuccess(T t);

    @Override
    public void onSubscribe(Subscription s) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable t) {


    }

    @Override
    public void onComplete() {

    }

    public void showError(String msg) {
        Toast.makeText(BaseApp.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }
}
