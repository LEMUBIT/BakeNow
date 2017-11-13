package com.lemuel.lemubit.bakenow.RetrofitRequestInterface;

/**
 * Created by charl on 10/11/2017.
 */

import com.lemuel.lemubit.bakenow.Models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    /*
    will return details of recipes.
     */
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();
}
