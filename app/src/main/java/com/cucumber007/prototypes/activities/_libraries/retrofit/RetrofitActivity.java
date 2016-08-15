package com.cucumber007.prototypes.activities._libraries.retrofit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.cucumber007.prototypes.R;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class RetrofitActivity extends Activity {

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        //logging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.interceptors().add(logging);

        //cookie
        httpClient.interceptors().add(chain -> {
            Request original = chain.request();

            String cookie = null; // = CookieModel.getInstance().getCookie();
            if (cookie == null) {
                cookie = "";
            }

            Request.Builder requestBuilder = original.newBuilder()
                    .header("Cookie", cookie)
                    .method(original.method(), original.body());

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
        clientBuilder.addNetworkInterceptor(chain -> {
            Response originalResponse = chain.proceed(chain.request());
            if (isNetworkAvailable()) {
                int maxAge = 60; // read from cache for 1 minute
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        });

        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        clientBuilder.cache(cache);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cucumber007app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
    }

    private boolean isNetworkAvailable() {return  true;}
}
