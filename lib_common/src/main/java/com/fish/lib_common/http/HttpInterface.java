package com.fish.lib_common.http;

import com.fish.lib_common.bean.Student;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpInterface {

    @POST("api/member/login")
    @FormUrlEncoded
    Observable<BaseHttpBean<Student>> login(@Field("username") String username,
                                            @Field("password") String password);
}
