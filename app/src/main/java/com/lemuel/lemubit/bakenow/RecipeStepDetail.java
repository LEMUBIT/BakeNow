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

package com.lemuel.lemubit.bakenow;

import android.net.Uri;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.lemuel.lemubit.bakenow.Models.Steps;
import com.lemuel.lemubit.bakenow.Utils.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* Displays Media player and step description for smaller screens
*/
public class RecipeStepDetail extends AppCompatActivity {
    List<Steps> steps;
    int initialPosition;
    int currentPosition;
    String instruction;

    @BindView(R.id.video_view)
    SimpleExoPlayerView videoView;
    @BindView(R.id.InstructionTXT)
    TextView instructionTXT;
    @BindView(R.id.imageView)
    ImageView imageView;

    private BandwidthMeter bandwidthMeter;
    private DataSource.Factory mediaDataSourceFactory;
    Snackbar snackbar;
    private Long currentMediaPlayerPosition;
    private String currentUrl;
    private SimpleExoPlayer mExoPlayer;
    Bundle savedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedState = savedInstanceState;
        setContentView(R.layout.activity_recipe_step_detail);
        ButterKnife.bind(this);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory
                (this, com.google.android.exoplayer2.util.Util.getUserAgent
                        (this, getString(R.string.bakeNow)),
                        (TransferListener<? super DataSource>) bandwidthMeter);

        if (savedInstanceState == null) {
            steps = getIntent().getExtras().getParcelableArrayList(getString(R.string.step));
            initialPosition = getIntent().getExtras().getInt(getString(R.string.stepPosition));
            currentMediaPlayerPosition = 0L;
        } else {
            steps = savedInstanceState.getParcelableArrayList(getString(R.string.list));
            initialPosition = savedInstanceState.getInt(getString(R.string.currentPosition));
        }
        currentPosition = initialPosition;
        instruction = steps.get(initialPosition).getDescription();
        String videoURL = steps.get(initialPosition).getVideoURL();
        String thumbnailURL = steps.get(initialPosition).getThumbnailURL();

        //display if video is not available
        snackbar = Snackbar
                .make(findViewById(R.id.recipeStepLayout), R.string.NoVideo, Snackbar.LENGTH_LONG);


        //Check if there is an instruction for the step
        if (Util.StringNotEmpty(instruction)) {
            instructionTXT.setText(instruction);
        }

        if (savedInstanceState == null) {
            if (Util.StringNotEmpty(videoURL)) {
                initializePlayer(videoURL);
            } else {
                snackbar.show();
                videoView.setVisibility(View.INVISIBLE);

                if (Util.StringNotEmpty(thumbnailURL))
                    Picasso.with(this).load(thumbnailURL).placeholder(R.drawable.chef).into(imageView);
            }
        }

    }

    @OnClick(R.id.nextBtn)
    public void next() {
        //stop playback
        release();
        //increment initialPosition
        currentPosition++;

        //if it has reached the end of the list, start over again
        if (currentPosition >= steps.size())
            currentPosition = 0;

        instruction = steps.get(currentPosition).getDescription();
        String videoURL = steps.get(currentPosition).getVideoURL();
        String thumbnailURL = steps.get(currentPosition).getThumbnailURL();

        //Check if there is an instruction before setting the text
        if (Util.StringNotEmpty(instruction)) {
            instructionTXT.setText(instruction);
        }


        if (Util.StringNotEmpty(videoURL)) {
            videoView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            initializePlayer(videoURL);
        } else {
            snackbar.show();
            videoView.setVisibility(View.INVISIBLE);
            currentUrl = "";
            if (Util.StringNotEmpty(thumbnailURL))
                Picasso.with(this).load(thumbnailURL).placeholder(R.drawable.chef).into(imageView);
        }

    }

    @OnClick(R.id.backBtn)
    public void back() {
        //stop playback
        release();
        //decrement initialPosition
        currentPosition--;
        if (currentPosition < 0)
            currentPosition = steps.size() - 1;
        instruction = steps.get(currentPosition).getDescription();
        String videoURL = steps.get(currentPosition).getVideoURL();
        String thumbnailURL = steps.get(currentPosition).getThumbnailURL();

        if (Util.StringNotEmpty(instruction)) {
            instructionTXT.setText(instruction);
        }


        if (Util.StringNotEmpty(videoURL)) {
            //   release();
            videoView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            initializePlayer(videoURL);
        } else {
            snackbar.show();
            currentUrl = "";
            videoView.setVisibility(View.INVISIBLE);

            if (Util.StringNotEmpty(thumbnailURL))
                Picasso.with(this).load(thumbnailURL).placeholder(R.drawable.chef).into(imageView);
        }
    }

    private void initializePlayer(String url) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            currentUrl = url;
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            videoView.setPlayer(mExoPlayer);

            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            // Prepare the MediaSource. :)
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url),
                    mediaDataSourceFactory, extractorsFactory, null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void initializePlayer() {
        // Create an instance of the ExoPlayer.
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        videoView.setPlayer(mExoPlayer);
        // Prepare the MediaSource.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // Prepare the MediaSource. :)
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(currentUrl),
                mediaDataSourceFactory, extractorsFactory, null, null);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);
        mExoPlayer.seekTo(currentMediaPlayerPosition);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (Util.ObjectisNotNull(outState)) {
            outState.putParcelableArrayList(getString(R.string.list), (ArrayList<? extends Parcelable>) steps);
            outState.putInt(getString(R.string.position), initialPosition);
            outState.putString(getString(R.string.currentUrl), currentUrl);
            outState.putInt(getString(R.string.currentPosition), currentPosition);
            if (Util.ObjectisNotNull(mExoPlayer)) {
                currentMediaPlayerPosition = mExoPlayer.getCurrentPosition();
                outState.putLong(getString(R.string.currentMediaPosition), currentMediaPlayerPosition);
            }

        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            steps = savedInstanceState.getParcelableArrayList(getString(R.string.list));
            initialPosition = savedInstanceState.getInt(getString(R.string.position));
            currentPosition = savedInstanceState.getInt(getString(R.string.currentPosition));
            currentUrl = savedInstanceState.getString(getString(R.string.currentUrl));
            currentMediaPlayerPosition = savedInstanceState.getLong(getString(R.string.currentMediaPosition));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (currentUrl != null && savedState != null) {
            if (Util.StringNotEmpty(currentUrl)) {
                initializePlayer();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    public void release() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        release();
    }


}
