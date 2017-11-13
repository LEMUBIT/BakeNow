package com.lemuel.lemubit.bakenow;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lemuel.lemubit.bakenow.Fragments.IngredientsFragment;
import com.lemuel.lemubit.bakenow.Fragments.StepDescriptionFragment;

import java.util.ArrayList;

public class RecipeDetail extends AppCompatActivity {
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        position = getIntent().getExtras().getInt("position");
        IngredientsFragment ingredientsFrag = new IngredientsFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) MainActivity.recipes);
        b.putInt("position", position);
        ingredientsFrag.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.FRGingredients, ingredientsFrag)
                .commit();

        StepDescriptionFragment stepDescriptionFragment = new StepDescriptionFragment();
        Bundle d = new Bundle();
        d.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) MainActivity.recipes);
        d.putInt("position", position);
        stepDescriptionFragment.setArguments(d);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.FRGdescription, stepDescriptionFragment)
                .commit();
    }
}
