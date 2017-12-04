package com.lemuel.lemubit.bakenow;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.google.android.exoplayer2.C;
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
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
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

/*
* The larger screens 'mTwoPane' will contain the Exoplayer in hte same activity
* */

public class RecipeDetail extends AppCompatActivity implements StepDescriptionAdapter.OnStepClickListener,
        ExoPlayer.EventListener {
    int position;
    @BindView(R.id.ingredientsLBL)
    TextView ingredientsLBL;
    @BindView(R.id.StepsLBL)
    TextView stepsLBL;
    @BindView(R.id.ToolBar_Recipe_Title)
    TextView ToolBarTitle;

    SimpleExoPlayerView videoView;
    TextView instructionTXT;
    Toast toast;
    ImageView imageView;
    private boolean mTwoPane;
    List<Recipe> recipes = new ArrayList<>();


    private int playerState = 0;
    private SimpleExoPlayer mExoPlayer;
    private BandwidthMeter bandwidthMeter;
    private DataSource.Factory mediaDataSourceFactory;
    StepDescriptionFragment stepDescriptionFragment;
    IngredientsFragment ingredientsFrag;
    private Long currentMediaPlayerPosition;
    private String currentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(this,
                com.google.android.exoplayer2.util.Util.getUserAgent(this, "bakenow"),
                (TransferListener<? super DataSource>) bandwidthMeter);


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

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ToolBarTitle.setText(recipes.get(position).getName());
        }

        //check if it is the Two Fragment layout for Large screens
        if (findViewById(R.id.videoDetailLayout) != null) {
            //the layout for large screens
            mTwoPane = true;
            videoView = (SimpleExoPlayerView) findViewById(R.id.video_view);
            instructionTXT = (TextView) findViewById(R.id.InstructionTXT);
            imageView = (ImageView) findViewById(R.id.imageView);
        } else {
            mTwoPane = false;
        }

        if (savedInstanceState == null) {
            ingredientsFrag = new IngredientsFragment();
            Bundle b = new Bundle();
            b.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) recipes);
            b.putInt("position", position);
            ingredientsFrag.setArguments(b);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.FRGingredients, ingredientsFrag)
                    .commit();

            stepDescriptionFragment = new StepDescriptionFragment();
            Bundle d = new Bundle();
            d.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) recipes);
            d.putInt("position", position);
            stepDescriptionFragment.setArguments(d);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.FRGdescription, stepDescriptionFragment)
                    .commit();
        }

        if (Util.ObjectisNull(currentUrl) && Util.firstRun == true && mTwoPane && Util.ObjectisNull(savedInstanceState)) {
            currentMediaPlayerPosition = 0L;
            Util.firstRun = false;
            onStepSelected(0, recipes.get(position).getSteps());
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (Util.ObjectisNotNull(outState) && mTwoPane) {
            outState.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) recipes);
            outState.putInt("position", position);
            outState.putString("currentUrl", currentUrl);
            getSupportFragmentManager().putFragment(outState, "stepDescription", stepDescriptionFragment);
            getSupportFragmentManager().putFragment(outState, "ingredientsFragm", ingredientsFrag);
            if (Util.ObjectisNotNull(mExoPlayer)) {
                currentMediaPlayerPosition = mExoPlayer.getCurrentPosition();
                outState.putLong("currentMediaPosition", currentMediaPlayerPosition);
            }
        } else if (Util.ObjectisNotNull(outState) && !mTwoPane) {
            outState.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) recipes);
            outState.putInt("position", position);
            getSupportFragmentManager().putFragment(outState, "stepDescription", stepDescriptionFragment);
            getSupportFragmentManager().putFragment(outState, "ingredientsFragm", ingredientsFrag);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            recipes = savedInstanceState.getParcelableArrayList("list");
            position = savedInstanceState.getInt("position");
            stepDescriptionFragment = (StepDescriptionFragment)
                    getSupportFragmentManager().getFragment(savedInstanceState, "stepDescription");
            ingredientsFrag = (IngredientsFragment)
                    getSupportFragmentManager().getFragment(savedInstanceState, "ingredientsFragm");

            if (mTwoPane) {
                currentMediaPlayerPosition = savedInstanceState.getLong("currentMediaPosition");
                currentUrl = savedInstanceState.getString("currentUrl");
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mTwoPane) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().hide();
                }
            } else {
                //To show the action bar
                if (getSupportActionBar() != null) {
                    getSupportActionBar().show();
                }
            }
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
                release();
                videoView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                setupVideoView(videoURL);
            } else if (Util.StringNotEmpty(thumbnailURL)) {
                release();
                setupVideoView(thumbnailURL);
            } else {
                toast.show();
                release();
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

        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            currentUrl = url;
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            videoView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            // Prepare the MediaSource. :)
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url),
                    mediaDataSourceFactory, extractorsFactory, null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);

        }


    }

    private void setupVideoView() {

        if (mExoPlayer == null) {
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


    }

    @Override
    protected void onResume() {
        super.onResume();

        //If it is in tablet mode and there was a Current video being played
        if (mTwoPane) {
            if (Util.StringNotEmpty(currentUrl)) {
                setupVideoView();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTwoPane) {
            if (mExoPlayer != null) {
                mExoPlayer.setPlayWhenReady(false);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTwoPane) {
            release();
            //reset variable firstRun
            Util.firstRun = true;
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
