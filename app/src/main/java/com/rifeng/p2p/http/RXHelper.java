package com.rifeng.p2p.http;

import com.rifeng.p2p.entity.RFResponse;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by caixiangyu on 2018/5/16.
 */

public class RXHelper {
    public static <T> FlowableTransformer<RFResponse<T>, T> RFFlowableTransformer() {
        return new FlowableTransformer<RFResponse<T>, T>() {

            @Override
            public Publisher<T> apply(final Flowable<RFResponse<T>> upstream) {
                return upstream.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                        .flatMap(new Function<RFResponse<T>, Publisher<T>>() {
                            @Override
                            public Publisher<T> apply(final RFResponse<T> trfResponse) throws Exception {
//                                if ("S".equalsIgnoreCase(trfResponse.getErrCode())) {
                                //token过期

                                if(trfResponse.getCode() == 200 || trfResponse.getCode() == 0){
                                    return Flowable.create(new FlowableOnSubscribe<T>() {
                                        @Override
                                        public void subscribe(FlowableEmitter<T> e) throws Exception {
                                            if(trfResponse.getData()==null){
//                                                if(trfResponse.getData() instanceof String){
//                                                    String s= (String) trfResponse.getData();
                                                    e.onNext((T) "");
                                                    e.onComplete();
//                                                }
                                            }else {
                                                e.onNext(trfResponse.getData());
                                                e.onComplete();
                                            }
                                        }
                                    }, BackpressureStrategy.BUFFER);
                                }else if(trfResponse.getCode() == 401){
                                    return Flowable.error(new RFTokenException(trfResponse.getMsg()));
                                }
                                else {
                                    return Flowable.error(new RFException(trfResponse.getCode(), trfResponse.getMsg()));
                                }
                            }
                        });
            }
        };
    }
}
