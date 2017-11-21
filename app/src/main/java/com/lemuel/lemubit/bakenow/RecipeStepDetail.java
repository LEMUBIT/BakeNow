package com.lemuel.lemubit.bakenow;

import android.media.MediaDataSource;
import android.net.Uri;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.lemuel.lemubit.bakenow.Models.Steps;
import com.lemuel.lemubit.bakenow.Utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeStepDetail extends AppCompatActivity implements
        ExoPlayer.EventListener {
    List<Steps> steps;
    int position;
    int currentPosition;
    String instruction;
    @BindView(R.id.video_view)
    SimpleExoPlayerView videoView;
    @BindView(R.id.InstructionTXT)
    TextView instructionTXT;
    @BindView(R.id.imageView)
    ImageView imageView;

    private int playerState = 0;
    private BandwidthMeter bandwidthMeter;
    private DataSource.Factory mediaDataSourceFactory;
    Toast toast;
    private Long currentMediaPlayerPosition;
    private String currentUrl;
    private SimpleExoPlayer mExoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        ButterKnife.bind(this);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        //////////////////
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory
                (this, com.google.android.exoplayer2.util.Util.getUserAgent(this, "mediaPlayerSample"),
                        (TransferListener<? super DataSource>) bandwidthMeter);
        ////////////////////
        if (savedInstanceState == null) {
            steps = getIntent().getExtras().getParcelableArrayList("step");
            position = getIntent().getExtras().getInt("stepPosition");
        } else {
            steps = savedInstanceState.getParcelableArrayList("list");
            position = savedInstanceState.getInt("position");
        }
        currentPosition = position;
        instruction = steps.get(position).getDescription();
        // String videoURL = steps.get(position).getVideoURL();
        // String thumbnailURL = steps.get(position).getThumbnailURL();
        String thumbnailURL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"; //URL USED FOR TESTING
        String videoURL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"; //URL USED FOR TESTING
        toast = Toast.makeText(this, R.string.NoVideo, Toast.LENGTH_SHORT);

        if (Util.StringNotEmpty(instruction)) {
            instructionTXT.setText(instruction);
        }

        if (Util.StringNotEmpty(videoURL)) {
            initializePlayer(videoURL);
        } else if (Util.StringNotEmpty(thumbnailURL)) {
            initializePlayer(thumbnailURL);

        } else {
            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.INVISIBLE);
            toast.show();
        }

    }

    @OnClick(R.id.nextBtn)
    public void next() {
        //stop playback
        if (playerState == mExoPlayer.STATE_READY)
            release();
        //increment position
        currentPosition++;

        if (currentPosition >= steps.size())
            currentPosition = 0;

        instruction = steps.get(currentPosition).getDescription();
        String videoURL = steps.get(currentPosition).getVideoURL();
        String thumbnailURL = steps.get(currentPosition).getThumbnailURL();

        if (Util.StringNotEmpty(instruction)) {
            instructionTXT.setText(instruction);
        }


        if (Util.StringNotEmpty(videoURL)) {
            videoView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            initializePlayer(videoURL);
        } else if (Util.StringNotEmpty(thumbnailURL)) {
            initializePlayer(thumbnailURL);

        } else {
            toast.show();
            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.INVISIBLE);
        }

    }

    @OnClick(R.id.backBtn)
    public void back() {
        //stop playback
        if (playerState == mExoPlayer.STATE_READY)
            release();
        //decrement position
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
            if (playerState == mExoPlayer.STATE_READY)
                release();
            videoView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            initializePlayer(videoURL);
        } else if (Util.StringNotEmpty(thumbnailURL)) {
            if (playerState == mExoPlayer.STATE_READY)
                release();//could make the ExoPlayer null and try...video not changing
            initializePlayer(thumbnailURL);
        } else {
            toast.show();
            if (playerState == mExoPlayer.STATE_READY)
                release();
            videoView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
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
            currentMediaPlayerPosition = mExoPlayer.getCurrentPosition();
        }
    }

    private void initializePlayer(Bundle savedInstanceState) {

        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            currentUrl = savedInstanceState.getString("currentUrl");
            Toast.makeText(this, "current:" + String.valueOf(currentMediaPlayerPosition), Toast.LENGTH_SHORT).show();//todo remove test
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            videoView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            // Prepare the MediaSource. :)
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(currentUrl),
                    mediaDataSourceFactory, extractorsFactory, null, null);
            mExoPlayer.seekTo(currentMediaPlayerPosition);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (Util.ObjectisNotNull(outState) && Util.ObjectisNotNull(mExoPlayer)) {
            outState.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) steps);
            outState.putInt("position", position);
            outState.putString("currentUrl", currentUrl);
            outState.putInt("currentPosition", currentPosition);
            currentMediaPlayerPosition = mExoPlayer.getCurrentPosition();
            outState.putLong("currentMediaPosition", currentMediaPlayerPosition);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            steps = savedInstanceState.getParcelableArrayList("list");
            position = savedInstanceState.getInt("position");
            currentPosition = savedInstanceState.getInt("currentPosition");
            initializePlayer(savedInstanceState);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mExoPlayer != null && currentMediaPlayerPosition != 0) {
            mExoPlayer.seekTo(currentMediaPlayerPosition);
            mExoPlayer.setPlayWhenReady(true);
            if (Util.StringNotEmpty(currentUrl)) {
                mExoPlayer.setPlayWhenReady(true);
            }
        }

        //todo  not resuming paused after pausing
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
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        playerState = playbackState;
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }
}
