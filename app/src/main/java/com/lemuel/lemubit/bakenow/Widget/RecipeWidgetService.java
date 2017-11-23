package com.lemuel.lemubit.bakenow.Widget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViewsService;


import com.google.gson.Gson;
import com.lemuel.lemubit.bakenow.Models.Ingredients;
import com.lemuel.lemubit.bakenow.Models.Recipe;
import com.lemuel.lemubit.bakenow.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charl on 22/11/2017.
 */

public class RecipeWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String recipeString = preferences.getString(getString(R.string.RecipePreferenceKey), null);
        List<Ingredients> ingredients = new ArrayList<>();
        if (recipeString != null) {
            Recipe recipe = gson.fromJson(recipeString, Recipe.class);
            ingredients = recipe.getIngredients();

        }
        return (new RecipeViewsFactory(getApplicationContext(), intent, ingredients));
    }
}
