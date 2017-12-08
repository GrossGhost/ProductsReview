package com.ainsoft.test.productsreview.network;


import com.ainsoft.test.productsreview.model.Xml;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/test/test.xml")
    Call<Xml> getXml();
}
