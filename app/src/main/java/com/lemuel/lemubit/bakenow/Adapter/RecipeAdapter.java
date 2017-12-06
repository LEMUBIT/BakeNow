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

package com.lemuel.lemubit.bakenow.Adapter;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lemuel.lemubit.bakenow.MainActivity;
import com.lemuel.lemubit.bakenow.Models.Recipe;
import com.lemuel.lemubit.bakenow.R;
import com.lemuel.lemubit.bakenow.RecipeDetail;
import com.lemuel.lemubit.bakenow.Utils.Util;
import com.lemuel.lemubit.bakenow.Widget.RecipeWidgetProvider;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> recipes;
    private Context context;

    public RecipeAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder, int position) {

        holder.RecipeName.setText(recipes.get(position).getName().toString());
        try {
            if (Util.emptyString(recipes.get(position).getImage())) {
                Picasso.with(context).load(R.drawable.chef).into(holder.RecipeImage);
            } else {
                Picasso.with(context).load(recipes.get(position).getImage()).into(holder.RecipeImage);
            }
        } catch (Exception e) {
            Log.e("image err:", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return Util.ObjectisNotNull(recipes) ? recipes.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView RecipeImage;
        TextView RecipeName;

        public ViewHolder(View itemView) {
            super(itemView);
            RecipeImage = itemView.findViewById(R.id.recipeIMG);
            RecipeName = itemView.findViewById(R.id.recipeName);
            itemView.setOnClickListener(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemView.setOnTouchListener(new View.OnTouchListener() {

                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        view.findViewById(R.id.recipeLYT).getBackground().setHotspot(motionEvent.getX(), motionEvent.getY());

                        return false;
                    }
                });
            }
        }

        @Override
        public void onClick(View view) {

            //Save Data in SharedPreference For App Widget To Access
            Gson gson = new Gson();
            String recipes = gson.toJson(MainActivity.recipes.get(getAdapterPosition()));
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(context.getString(R.string.RecipePreferenceKey), recipes);
            editor.apply();

            // Notify the widget that the data has changed
            ComponentName widget = new ComponentName(context, RecipeWidgetProvider.class);
            int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(widget);
            Intent intent = new Intent(context, RecipeWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            context.sendBroadcast(intent);

            //Open the Detail Activity
            context.startActivity(new Intent(context, RecipeDetail.class).putExtra("position", getAdapterPosition()));
        }
    }
}
