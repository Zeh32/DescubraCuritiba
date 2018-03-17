package com.example.jgaug.descubracuritiba.Api.endpoint;

import com.example.jgaug.descubracuritiba.Api.Response.Distance;
import com.example.jgaug.descubracuritiba.Api.Response.DistanceTeste;
import com.example.jgaug.descubracuritiba.Api.Response.DistanciaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by eadcn on 29/01/2018.
 */

public interface distanciaApi {

    @GET("/maps/api/distancematrix/json")
    Call<DistanciaResponse> getDistancia(@Query("language") String language,@Query("mode") String mode,@Query("origins") String latLongOrin, @Query("destinations") String latLongDest,
                                         @Query("key") String ApiKey
    );

}
