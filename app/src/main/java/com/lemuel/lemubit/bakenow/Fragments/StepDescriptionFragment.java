package com.lemuel.lemubit.bakenow.Fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lemuel.lemubit.bakenow.Adapter.StepDescriptionAdapter;
import com.lemuel.lemubit.bakenow.Models.Recipe;
import com.lemuel.lemubit.bakenow.Models.Steps;
import com.lemuel.lemubit.bakenow.R;
import com.lemuel.lemubit.bakenow.Utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charl on 10/11/2017.
 */

public class StepDescriptionFragment extends Fragment {
    int position;
    List<Recipe> mRecipes = new ArrayList<>();
    List<Steps> mSteps = new ArrayList<>();
    RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_recipe_step_descriptions, container, false);
        RecyclerView description = rootView.findViewById(R.id.description_recycler_view);
        Bundle bundle = this.getArguments();
        if (Util.ObjectisNotNull(bundle)) {

            if (Util.ObjectisNull(savedInstanceState)) {
                mRecipes = bundle.getParcelableArrayList("list");
                position = bundle.getInt("position");
                mSteps = mRecipes.get(position).getSteps();
            } else {
                //use savedInstanceState if not null
                mSteps = savedInstanceState.getParcelableArrayList("list");
                position = savedInstanceState.getInt("position");
            }


            StepDescriptionAdapter stepDescriptionAdapter = new StepDescriptionAdapter(mSteps, getActivity());
            description.setLayoutManager(new LinearLayoutManager(getActivity()));
            description.setAdapter(stepDescriptionAdapter);
            RecyclerView.ItemDecoration itemDecoration = new
                    DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
            description.addItemDecoration(itemDecoration);

        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) mSteps);
        outState.putInt("position", position);
        outState.putParcelableArrayList("recipeList",(ArrayList<? extends Parcelable>) mRecipes);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mSteps = savedInstanceState.getParcelableArrayList("list");
            position = savedInstanceState.getInt("position");
            mRecipes= savedInstanceState.getParcelableArrayList("recipeList");
        }
    }
}
