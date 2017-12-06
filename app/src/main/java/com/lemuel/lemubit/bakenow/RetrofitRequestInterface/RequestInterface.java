/*
 *
 *   BakeNow application
 *
 *   @author Lemuel Ogbunude
 *   Copyright (C) 2017 Lemuel Ogbunude (lemuelcco@gmail.com)
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *
 */

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
