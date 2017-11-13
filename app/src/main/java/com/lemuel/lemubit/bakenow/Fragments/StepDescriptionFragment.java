package com.lemuel.lemubit.bakenow.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lemuel.lemubit.bakenow.Adapter.StepDescriptionAdapter;
import com.lemuel.lemubit.bakenow.Models.Ingredients;
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_recipe_step_descriptions, container, false);
        RecyclerView description = rootView.findViewById(R.id.description_recycler_view);
        Bundle bundle = this.getArguments();
        if (Util.ObjectisNotNull(bundle)) {
            mRecipes = bundle.getParcelableArrayList("list");
            position = bundle.getInt("position");
            mSteps = mRecipes.get(position).getSteps();
            StepDescriptionAdapter stepDescriptionAdapter=new StepDescriptionAdapter(mSteps,getActivity());
            description.setLayoutManager(new LinearLayoutManager(getActivity()));
            description.setAdapter(stepDescriptionAdapter);

        }

        return rootView;
    }
}
