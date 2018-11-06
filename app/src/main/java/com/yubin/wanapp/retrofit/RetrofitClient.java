package com.yubin.wanapp.retrofit;

import com.yubin.wanapp.activity.App;
import com.yubin.wanapp.retrofit.cookies.CookieManger;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author : Yubin.Ying
 * time : 2018/11/1
 */
public class RetrofitClient {
    private RetrofitClient() {
    }

    private static class ClientHolder{

        private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //添加对Cookies的管理
                .cookieJar(new CookieManger(App.getContext()))
                .addInterceptor(new LoggerInterceptor("OkhttpUtils",true))
                .build();


        private static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.API_BASE)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static Retrofit getInstance(){
        return ClientHolder.retrofit;
    }
}
