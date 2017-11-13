package com.lemuel.lemubit.bakenow;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lemuel.lemubit.bakenow.Adapter.RecipeAdapter;
import com.lemuel.lemubit.bakenow.Models.Recipe;
import com.lemuel.lemubit.bakenow.RetrofitRequestInterface.RequestInterface;
import com.lemuel.lemubit.bakenow.Utils.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recipe_recycler_view)
    RecyclerView recipeRecyclerV;
    @BindView(R.id.recipe_progress)
    ProgressBar recipeProgress;

    RecyclerView.LayoutManager mLayoutManager;
    RecipeAdapter recipeAdapter;
    static List<Recipe> receipes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadRecipe();
        loadAppropriateLayout();
    }

    private void loadRecipe() {
        recipeProgress.setVisibility(View.VISIBLE);
        /*build Retrofit using Retrofit.Builder() and convert JSON data
        into accessible data object using GsonConverterFactory*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<List<Recipe>> call = request.getRecipes();//response will be a JSON array not a JSON object
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipeProgress.setVisibility(View.INVISIBLE);
                 receipes = response.body();
                recipeAdapter = new RecipeAdapter(MainActivity.this, receipes);
                recipeRecyclerV.setAdapter(recipeAdapter);

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("Error", t.getMessage());
                recipeProgress.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAppropriateLayout() {
        //IF SCREEN IS LARGE, SHOW MORE RECIPES PER ROW
        if (Util.isLargeScreen(MainActivity.this)) {
            //LARGE SCREEN
            if (MainActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                recipeRecyclerV.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                mLayoutManager = recipeRecyclerV.getLayoutManager();
            } else {
                recipeRecyclerV.setLayoutManager(new GridLayoutManager(MainActivity.this, 8));
                mLayoutManager = recipeRecyclerV.getLayoutManager();
            }
        } else if (Util.isMediumScreen(MainActivity.this)) {
            //NOT SO LARGE SCREEN :)
            if (MainActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                recipeRecyclerV.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                mLayoutManager = recipeRecyclerV.getLayoutManager();
            } else {
                recipeRecyclerV.setLayoutManager(new GridLayoutManager(MainActivity.this, 5));
                mLayoutManager = recipeRecyclerV.getLayoutManager();
            }
        } else {
            //SMALLER SCREENS
            if (MainActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                recipeRecyclerV.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                mLayoutManager = recipeRecyclerV.getLayoutManager();
            } else {
                recipeRecyclerV.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                mLayoutManager = recipeRecyclerV.getLayoutManager();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO work on savedinstancestate
    }
}
