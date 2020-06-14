package com.fish.lib_common.http;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpInterface {

    @POST("app/sendSms")
    @FormUrlEncoded
    Observable<BaseHttpBean> login(@Field("mobile") String username,
                              @Field("code") String password,
                              @Field("uuid") String uuid);
}
