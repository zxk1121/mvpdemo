package com.app.demo.api;

import android.os.Looper;
import android.support.annotation.NonNull;

import com.app.common.util.LoggerUtil;
import com.app.common.util.NetWorkUtil;
import com.app.demo.application.App;
import com.app.demo.config.CacheFile;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zxk on 17-3-9.
 */

public class RetrofitService {
    private static IAPIService mApiService;

    private static final String HOST = "http://bogou.okxueche.net/";

    public static void init() {
        Cache cache = new Cache(CacheFile.getHttpCache(), 1024 * 1024 * 100);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(mLoggingInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build();

        mApiService = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HOST)
                .build().create(IAPIService.class);

    }

    private static final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtil.isNetWorkConnected(App.getContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Logger.e("no network");
            }
            return null;
        }
    };

    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Buffer buffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(buffer);
            } else {
                Logger.e("NETWORK", "request.body()==null");
            }
            Logger.w(request.url() + (request.body() != null ? "?" + _parseParams(request.body(), buffer) : ""));
            Response response = chain.proceed(request);
            return response;
        }
    };

    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }
}
