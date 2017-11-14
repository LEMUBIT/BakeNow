package com.lemuel.lemubit.bakenow;

import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.lemuel.lemubit.bakenow.Models.Steps;
import com.lemuel.lemubit.bakenow.Utils.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeStepDetail extends AppCompatActivity implements com.devbrackets.android.exomedia.listener.OnPreparedListener {
    List<Steps> steps;
    int position;
    int currentPosition;
    String instruction;
    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.InstructionTXT)
    TextView instructionTXT;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        ButterKnife.bind(this);
//        ActionBar actionBar=getSupportActionBar(); //crashing when recreating Recipe Detail
//        actionBar.setDisplayHomeAsUpEnabled(true);
        steps = getIntent().getExtras().getParcelableArrayList("step");
        position = getIntent().getExtras().getInt("stepPosition");
        currentPosition = position;
        instruction = steps.get(position).getDescription();
        String videoURL = steps.get(position).getVideoURL();
        toast = Toast.makeText(this, R.string.NoVideo, Toast.LENGTH_LONG);
        if (Util.StringNotEmpty(instruction)) {
            instructionTXT.setText(instruction);
        }

        if (Util.StringNotEmpty(videoURL)) {
            setupVideoView(videoURL);
        } else {
            toast.show();
        }

    }

    @OnClick(R.id.nextBtn)
    public void next() {
        //stop playback
        videoView.stopPlayback();
        //increment position
        currentPosition++;

        if (currentPosition >= steps.size())
            currentPosition = 0;

        instruction = steps.get(currentPosition).getDescription();
        String videoURL = steps.get(currentPosition).getVideoURL();

        if (Util.StringNotEmpty(instruction)) {
            instructionTXT.setText(instruction);
        }


        if (Util.StringNotEmpty(videoURL)) {
            videoView.setVisibility(View.VISIBLE);
            setupVideoView(videoURL);
        } else {
            toast.show();
            videoView.setVisibility(View.INVISIBLE);
        }

    }

    @OnClick(R.id.backBtn)
    public void back() {
        //stop playback
        videoView.stopPlayback();
        //decrement position
        currentPosition--;
        if (currentPosition < 0)
            currentPosition = steps.size() - 1;
        instruction = steps.get(currentPosition).getDescription();
        String videoURL = steps.get(currentPosition).getVideoURL();

        if (Util.StringNotEmpty(instruction)) {
            instructionTXT.setText(instruction);
        }


        if (Util.StringNotEmpty(videoURL)) {
            videoView.setVisibility(View.VISIBLE);
            setupVideoView(videoURL);
        } else {
            toast.show();
            videoView.setVisibility(View.INVISIBLE);
        }
    }

    private void setupVideoView(String url) {
        videoView = (VideoView) findViewById(R.id.video_view);
        videoView.setOnPreparedListener(this);
        videoView.setVideoURI(Uri.parse(url));
    }

    @Override
    public void onPrepared() {
        videoView.start();
    }
}
