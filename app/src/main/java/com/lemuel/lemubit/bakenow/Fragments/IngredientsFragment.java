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

package com.lemuel.lemubit.bakenow.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lemuel.lemubit.bakenow.Models.Ingredients;
import com.lemuel.lemubit.bakenow.Models.Recipe;
import com.lemuel.lemubit.bakenow.R;
import com.lemuel.lemubit.bakenow.Utils.Util;

import java.util.ArrayList;
import java.util.List;


public class IngredientsFragment extends Fragment {
    int position = 0;
    List<Recipe> mRecipes = new ArrayList<>();
    List<Ingredients> mIngredients = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_recipe_ingredients, container, false);
        TextView ingredients = rootView.findViewById(R.id.ingredientsTxt);
        Bundle bundle = this.getArguments();
        if (Util.ObjectisNotNull(bundle)) {

            //Get Recipes list and current list position from bundle or savedInstanceState
            if (Util.ObjectisNull(savedInstanceState)) {
                mRecipes = bundle.getParcelableArrayList(getString(R.string.list));
                position = bundle.getInt(getString(R.string.position));
            } else {
                mRecipes = savedInstanceState.getParcelableArrayList(getString(R.string.list));
                position = savedInstanceState.getInt(getString(R.string.position));
            }

            //if its Portrait for large screens or if it is a smaller screen, then show
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ||
                    Util.isSmallScreen(getActivity())) {

                mIngredients = mRecipes.get(position).getIngredients();

                //properly format ingredients and quantity
                for (int i = 0; i < mIngredients.size(); i++) {
                    ingredients.append("-- " + mIngredients.get(i).getQuantity() + " "
                            + Util.Plural(mIngredients.get(i).getQuantity(), mIngredients.get(i).getMeasure())
                            + " of " + mIngredients.get(i).getIngredient() + " \n");
                }
            }
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(getString(R.string.list), (ArrayList<? extends Parcelable>) mRecipes);
        outState.putInt(getString(R.string.position), position);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mRecipes = savedInstanceState.getParcelableArrayList(getString(R.string.list));
            position = savedInstanceState.getInt(getString(R.string.position));
        }
    }
}
