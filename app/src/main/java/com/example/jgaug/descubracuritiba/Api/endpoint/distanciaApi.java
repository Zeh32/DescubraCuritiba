package com.example.jgaug.descubracuritiba.Api.endpoint;

import com.example.jgaug.descubracuritiba.Api.Response.DistanciaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by eadcn on 29/01/2018.
 */

public interface distanciaApi {

    @GET("/api/distancematrix/json")
    Call<DistanciaResponse> getDistancia(@Query("origins") String latLongOrin, @Query("destinations") String latLongDest,
                                     @Query("key") String ApiKey
    );

}
