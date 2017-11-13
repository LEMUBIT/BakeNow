package com.lemuel.lemubit.bakenow.Fragments;

import android.os.Bundle;
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
    int position;
    List<Recipe> mRecipes = new ArrayList<>();
    List<Ingredients> mIngredients = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_recipe_ingredients, container, false);
        TextView ingredients = rootView.findViewById(R.id.ingredientsTxt);
        Bundle bundle = this.getArguments();
        if (Util.ObjectisNotNull(bundle)) {
            mRecipes = bundle.getParcelableArrayList("list");
            position = bundle.getInt("position");
            mIngredients = mRecipes.get(position).getIngredients();

            for (int i = 0; i < mIngredients.size(); i++) {
                ingredients.append("-- " + mIngredients.get(i).getQuantity() + " "
                        + Util.Plural(mIngredients.get(i).getQuantity(), mIngredients.get(i).getMeasure())
                        + " of " + mIngredients.get(i).getIngredient() + " \n");
            }
        }

        return rootView;
    }


}
