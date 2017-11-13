package com.lemuel.lemubit.bakenow.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lemuel.lemubit.bakenow.R;

/**
 * Created by charl on 10/11/2017.
 */

public class StepDescriptionFragment extends Fragment {

    public StepDescriptionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_recipe_step_descriptions, container, false);
        RecyclerView desciption=(RecyclerView) rootView.findViewById(R.id.description_recycler_view);
        //TODO continue and get list of item
        return rootView;
    }
}
