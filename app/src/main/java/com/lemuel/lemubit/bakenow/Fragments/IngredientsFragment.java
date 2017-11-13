package com.lemuel.lemubit.bakenow.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lemuel.lemubit.bakenow.Models.Ingredients;
import com.lemuel.lemubit.bakenow.Models.Recipe;
import com.lemuel.lemubit.bakenow.R;
import com.lemuel.lemubit.bakenow.RecipeDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charl on 10/11/2017.
 */

public class IngredientsFragment extends Fragment {
    int position;
     List<Recipe> mRecipes=new ArrayList<>();
    String test;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  test=String.valueOf(mRecipes.size());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_recipe_ingredients, container, false);
        TextView ingredients = rootView.findViewById(R.id.ingredientsTxt);
        //  List<Ingredients> LSTingredients = receipes.get(position).getIngredients();
        //ingredients.setText(LSTingredients.get(position).getIngredient());
        //TODO LIST is saying null..mRecipes not recognized
        Bundle bundle = this.getArguments();
        ingredients.setText(bundle.getParcelableArrayList("list").size());
        return rootView;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setRecipes(List<Recipe> recipes) {

        mRecipes = new ArrayList<>(recipes);
        test = String.valueOf(mRecipes.get(0).getName());
    }
}
