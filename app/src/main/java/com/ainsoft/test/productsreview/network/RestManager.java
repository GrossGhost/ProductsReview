package com.ainsoft.test.productsreview.network;


import android.support.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RestManager {
    private static ApiService apiService;

    @NonNull
    public static ApiService getApiService() {
        //I know that double checked locking is not a good pattern, but it's enough here
        ApiService service = apiService;
        if (service == null) {
            synchronized (RestManager.class) {
                service = apiService;
                if (service == null) {
                    service = apiService = createService();
                }
            }
        }
        return service;
    }

    @NonNull
    private static ApiService createService() {

        return new Retrofit.Builder()
                .baseUrl("http://ainsoft.pro")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
                .create(ApiService.class);
    }
}
