package com.lemuel.lemubit.bakenow.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lemuel.lemubit.bakenow.Models.Recipe;
import com.lemuel.lemubit.bakenow.R;
import com.lemuel.lemubit.bakenow.Utils.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by charl on 10/11/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> receipes;
    private Context context;

    public RecipeAdapter(Context context, List<Recipe> receipes) {
        this.context = context;
        this.receipes = receipes;
    }

    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder, int position) {
        ViewHolder myHolder = holder;
        myHolder.RecipeName.setText(receipes.get(position).getName().toString());

        try {
            if (Util.emptyString(receipes.get(position).getImage())) {
                Picasso.with(context).load(R.drawable.chef).into(myHolder.RecipeImage);
            } else {
                Picasso.with(context).load(receipes.get(position).getImage()).into(myHolder.RecipeImage);
            }
        } catch (Exception e) {
            Log.e("image eeer:", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return receipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView RecipeImage;
        TextView RecipeName;

        public ViewHolder(View itemView) {
            super(itemView);
            RecipeImage = itemView.findViewById(R.id.recipeIMG);
            RecipeName = itemView.findViewById(R.id.recipeName);
        }
    }
}
