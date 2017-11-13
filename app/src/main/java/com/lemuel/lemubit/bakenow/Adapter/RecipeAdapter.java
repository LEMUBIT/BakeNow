package com.lemuel.lemubit.bakenow.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lemuel.lemubit.bakenow.Models.Recipe;
import com.lemuel.lemubit.bakenow.R;
import com.lemuel.lemubit.bakenow.RecipeDetail;
import com.lemuel.lemubit.bakenow.Utils.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by charl on 10/11/2017.
 */

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
            Log.e("image eeer:", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return recipes.size();
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
            //    Toast.makeText(context, "Name:" + recipes.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, RecipeDetail.class).putExtra("position", getAdapterPosition()));
        }
    }
}
