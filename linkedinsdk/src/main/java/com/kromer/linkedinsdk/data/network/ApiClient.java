package com.kromer.linkedinsdk.data.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
  private static final String BASE_URL = "https://www.linkedin.com/uas/oauth2/";
  //Please check --> res/xml/network_security_config.xml

  private static ApiClient instance;

  private ApiInterface apiService;

  private ApiClient() {
    OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(120, TimeUnit.SECONDS)
        .connectTimeout(120, TimeUnit.SECONDS).addNetworkInterceptor(new StethoInterceptor())
        .build();

    Retrofit retrofit = new Retrofit.Builder().client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(
            GsonConverterFactory.create(new GsonBuilder().setLenient().create())).baseUrl(BASE_URL)
        .build();

    apiService = retrofit.create(ApiInterface.class);
  }

  public static ApiClient getInstance() {
    if (instance == null) {
      instance = new ApiClient();
    }
    return instance;
  }

  public ApiInterface getApiService() {
    return apiService;
  }
}