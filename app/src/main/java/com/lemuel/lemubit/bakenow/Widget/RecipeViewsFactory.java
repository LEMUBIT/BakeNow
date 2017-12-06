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

package com.lemuel.lemubit.bakenow.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.lemuel.lemubit.bakenow.Models.Ingredients;
import com.lemuel.lemubit.bakenow.Models.Recipe;
import com.lemuel.lemubit.bakenow.R;
import com.lemuel.lemubit.bakenow.Utils.Util;

import java.util.List;


public class RecipeViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context = null;
    private int recipeWidgetId;
    public List<Ingredients> Ingredients;


    public RecipeViewsFactory(Context context, Intent intent, List<Ingredients> Ingredients) {
        this.context = context;
        recipeWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        this.Ingredients = Ingredients;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        //get saved recipe string from preference
        String recipeString = preferences.getString(context.getString(R.string.RecipePreferenceKey), null);
        if (recipeString != null) {
            //get ingredients form string
            Recipe recipe = gson.fromJson(recipeString, Recipe.class);
            Ingredients = recipe.getIngredients();


        }

        RecipeWidgetProvider.updateAppWidget(context, AppWidgetManager.getInstance(context), recipeWidgetId);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return Ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews
                (context.getPackageName(), R.layout.recipe_list_row);

        row.setTextViewText(R.id.recipeRow, Ingredients.get(position).getIngredient());

        //to format text properly
        String recipeText = Ingredients.get(position).getQuantity() + " "
                + Util.Plural(Ingredients.get(position).getQuantity(),
                Ingredients.get(position).getMeasure())
                + " of " + Ingredients.get(position).getIngredient() + ".";

        row.setTextViewText(R.id.recipeRow, recipeText);
        Intent i = new Intent();
        Bundle extras = new Bundle();

        extras.putString(RecipeWidgetProvider.EXTRA_WORD, Ingredients.get(position).getIngredient());
        extras.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID, recipeWidgetId);
        i.putExtras(extras);
        row.setOnClickFillInIntent(R.id.recipeRow, i);
        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
