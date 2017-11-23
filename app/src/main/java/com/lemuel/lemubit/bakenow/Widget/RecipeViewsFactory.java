package com.lemuel.lemubit.bakenow.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.lemuel.lemubit.bakenow.Models.Ingredients;
import com.lemuel.lemubit.bakenow.Models.Recipe;
import com.lemuel.lemubit.bakenow.R;
import com.lemuel.lemubit.bakenow.Utils.Util;

import java.util.List;

/**
 * Created by charl on 22/11/2017.
 */

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

        String recipeText=Ingredients.get(position).getQuantity() + " "
                + Util.Plural(Ingredients.get(position).getQuantity(),
                Ingredients.get(position).getMeasure())
                + " of " + Ingredients.get(position).getIngredient();

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
