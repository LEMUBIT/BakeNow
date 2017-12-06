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

import com.lemuel.lemubit.bakenow.Adapter.StepDescriptionAdapter;
import com.lemuel.lemubit.bakenow.Models.Recipe;
import com.lemuel.lemubit.bakenow.Models.Steps;
import com.lemuel.lemubit.bakenow.R;
import com.lemuel.lemubit.bakenow.Utils.Util;

import java.util.ArrayList;
import java.util.List;


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

            if (Util.ObjectisNull(savedInstanceState)) {
                mRecipes = bundle.getParcelableArrayList(getString(R.string.list));
                position = bundle.getInt(getString(R.string.position));
                mSteps = mRecipes.get(position).getSteps();
            } else {
                //use savedInstanceState if not null
                mSteps = savedInstanceState.getParcelableArrayList(getString(R.string.list));
                position = savedInstanceState.getInt(getString(R.string.position));
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
        outState.putParcelableArrayList(getString(R.string.list), (ArrayList<? extends Parcelable>) mSteps);
        outState.putInt(getString(R.string.position), position);
        outState.putParcelableArrayList(getString(R.string.recipeList), (ArrayList<? extends Parcelable>) mRecipes);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mSteps = savedInstanceState.getParcelableArrayList(getString(R.string.list));
            position = savedInstanceState.getInt(getString(R.string.position));
            mRecipes = savedInstanceState.getParcelableArrayList(getString(R.string.recipeList));
        }
    }
}
