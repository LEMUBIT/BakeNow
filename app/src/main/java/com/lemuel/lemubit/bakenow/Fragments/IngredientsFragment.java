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
import android.widget.Toast;

import com.lemuel.lemubit.bakenow.Models.Ingredients;
import com.lemuel.lemubit.bakenow.Models.Recipe;
import com.lemuel.lemubit.bakenow.R;
import com.lemuel.lemubit.bakenow.RecipeDetail;
import com.lemuel.lemubit.bakenow.Utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charl on 10/11/2017.
 */

public class IngredientsFragment extends Fragment {
    int position=0;
    List<Recipe> mRecipes = new ArrayList<>();
    List<Ingredients> mIngredients = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_recipe_ingredients, container, false);
        TextView ingredients = rootView.findViewById(R.id.ingredientsTxt);
        Bundle bundle = this.getArguments();
        if (Util.ObjectisNotNull(bundle)) {

            //Check if savedInstanceStateIsNull
            if (Util.ObjectisNull(savedInstanceState)) {
                mRecipes = bundle.getParcelableArrayList("list");
                position = bundle.getInt("position");
            } else {
                mRecipes = savedInstanceState.getParcelableArrayList("list");
                position = savedInstanceState.getInt("position");
            }

            if(this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT) {

                mIngredients = mRecipes.get(position).getIngredients();

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
        outState.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) mRecipes);
        outState.putInt("position", position);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mRecipes = savedInstanceState.getParcelableArrayList("list");
            position = savedInstanceState.getInt("position");
        }
    }
}
