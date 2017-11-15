package com.lemuel.lemubit.bakenow;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.lemuel.lemubit.bakenow.Adapter.RecipeAdapter;
import com.lemuel.lemubit.bakenow.Adapter.StepDescriptionAdapter;
import com.lemuel.lemubit.bakenow.Fragments.IngredientsFragment;
import com.lemuel.lemubit.bakenow.Fragments.StepDescriptionFragment;
import com.lemuel.lemubit.bakenow.Models.Recipe;
import com.lemuel.lemubit.bakenow.Models.Steps;
import com.lemuel.lemubit.bakenow.Utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetail extends AppCompatActivity implements StepDescriptionAdapter.OnStepClickListener,
        com.devbrackets.android.exomedia.listener.OnPreparedListener {
    int position;
    @BindView(R.id.ingredientsLBL)
    TextView ingredientsLBL;
    @BindView(R.id.StepsLBL)
    TextView stepsLBL;
    @BindView(R.id.ToolBar_Recipe_Title)
    TextView ToolBarTitle;
    VideoView videoView;
    TextView instructionTXT;
    Toast toast;
    ImageView imageView;
    private boolean mTwoPane;
    List<Recipe> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //TODO : fix crash--app crashes when rotated in Recipe Detail Activity

        toast = Toast.makeText(this, R.string.NoVideo, Toast.LENGTH_SHORT);
        if (savedInstanceState == null) {
            position = getIntent().getExtras().getInt("position");
            recipes = MainActivity.recipes;
        } else {
            recipes = savedInstanceState.getParcelableArrayList("list");
            position = savedInstanceState.getInt("position");
        }
        ingredientsLBL.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_assignment_black_24dp, 0, 0, 0);
        stepsLBL.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_all_black_24dp, 0, 0, 0);

        ToolBarTitle.setText(recipes.get(position).getName());

        //check if it is the Two Fragment layout for Large screens
        if (findViewById(R.id.videoDetailLayout) != null) {
            //if it finds this layout, it means
            // that it is the layout for large screens
            mTwoPane = true;
            videoView = (VideoView) findViewById(R.id.video_view);
            instructionTXT = (TextView) findViewById(R.id.InstructionTXT);
            imageView = (ImageView) findViewById(R.id.imageView);
        } else {
            mTwoPane = false;
        }
        IngredientsFragment ingredientsFrag = new IngredientsFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) recipes);
        b.putInt("position", position);
        ingredientsFrag.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.FRGingredients, ingredientsFrag)
                .commit();

        StepDescriptionFragment stepDescriptionFragment = new StepDescriptionFragment();
        Bundle d = new Bundle();
        d.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) recipes);
        d.putInt("position", position);
        stepDescriptionFragment.setArguments(d);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.FRGdescription, stepDescriptionFragment)
                .commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) recipes);
        outState.putInt("position", position);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            recipes = savedInstanceState.getParcelableArrayList("list");
            position = savedInstanceState.getInt("position");
        }
    }

    @Override
    public void onStepSelected(int stepPosition, List<Steps> steps) {
        Bundle s = new Bundle();
        if (mTwoPane) {
            String instruction = steps.get(stepPosition).getDescription();
            String videoURL = steps.get(stepPosition).getVideoURL();
            String thumbnailURL = steps.get(stepPosition).getThumbnailURL();
            if (Util.StringNotEmpty(instruction)) {
                instructionTXT.setText(instruction);
            }

            if (Util.StringNotEmpty(videoURL)) {
                videoView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                setupVideoView(videoURL);
            } else if (Util.StringNotEmpty(thumbnailURL)) {
                setupVideoView(thumbnailURL);
            } else {
                toast.show();
                videoView.stopPlayback();
                videoView.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);
            }

        } else {
            s.putParcelableArrayList("step", (ArrayList<? extends Parcelable>) steps);
            s.putInt("stepPosition", stepPosition);
            startActivity(new Intent(this, RecipeStepDetail.class).putExtras(s));
        }


    }

    private void setupVideoView(String url) {
        videoView.setOnPreparedListener(this);
        videoView.setVideoURI(Uri.parse(url));
    }

    @Override
    public void onPrepared() {
        videoView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTwoPane)
            videoView.release();
    }
}
