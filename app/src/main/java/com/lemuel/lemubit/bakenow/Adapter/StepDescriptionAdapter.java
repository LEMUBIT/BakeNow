package com.lemuel.lemubit.bakenow.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lemuel.lemubit.bakenow.Models.Steps;
import com.lemuel.lemubit.bakenow.R;

import java.util.List;

/**
 * Created by charl on 13/11/2017.
 */

public class StepDescriptionAdapter extends RecyclerView.Adapter<StepDescriptionAdapter.StepViewHolder> {

    private List<Steps> steps;
    private Context context;

    // Define a new interface OnStepClickListener that triggers a callback in the host activity
    OnStepClickListener mCallback;

    public interface OnStepClickListener {
        void onStepSelected(int position,List<Steps> steps);
    }


    public StepDescriptionAdapter(List<Steps> steps, Context context) {
        this.steps = steps;
        this.context = context;
    }


    @Override
    public StepDescriptionAdapter.StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step_description_card, parent, false);

        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + context.getString(R.string.must_Implement));
        }

        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepDescriptionAdapter.StepViewHolder holder, int position) {
        holder.step.setText(steps.get(position).getShortDescription().toString());
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView step;

        public StepViewHolder(View itemView) {
            super(itemView);
            step = itemView.findViewById(R.id.stepTXT);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mCallback.onStepSelected(getAdapterPosition(),steps);

        }
    }
}
