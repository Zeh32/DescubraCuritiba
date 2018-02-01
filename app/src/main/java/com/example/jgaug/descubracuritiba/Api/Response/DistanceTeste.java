package com.example.jgaug.descubracuritiba.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eadcn on 01/02/2018.
 */

public class DistanceTeste {

    @SerializedName("value")
    @Expose
    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
