package com.metadjioo_ds.app.presentation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.metadjioo_ds.MDSApp;
import com.metadjioo_ds.R;
import com.metadjioo_ds.app.fragment.CardWineFragment;
import com.metadjioo_ds.db.AppDatabase;
import com.metadjioo_ds.db.entity.CompanyVideo;
import com.metadjioo_ds.db.entity.Wine;
import com.metadjioo_ds.db.entity.WineCuvee;
import com.metadjioo_ds.db.entity.WineCuveeDatas;
import com.metadjioo_ds.db.entity.WineDatas;
import com.metadjioo_ds.utils.ImgSaver;

import java.util.Timer;
import java.util.TimerTask;

public class VideoDataSheetPresentation extends MDSPresentation {

    private final static int DELAY_BEFORE_REINIT = 30000;
    private ConstraintLayout mVideoLayout;
    private LinearLayout mDatasheet;
    private TextView mDatasheetPlaceholder;
    private ImageView mImgViewWine;
    private boolean mIsVideo;
    private boolean mIsVideoTeaser;
    private Timer mTimer;
    private CardWineFragment mCardWineFragment;
    private boolean mTimerRunning;

    public VideoDataSheetPresentation(Context outerContext, Display display) {
        super(outerContext, display);
        mIsVideo = true;
        mIsVideoTeaser = true;
        mTimerRunning = false;
        mTimer = new Timer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_datasheet_presentation);
        mVideoLayout = findViewById(R.id.video_layout);
        VideoView videoView = findViewById(R.id.video_view);
        mDatasheet = findViewById(R.id.datasheet);
        mImgViewWine = findViewById(R.id.wine_img);
        mDatasheetPlaceholder = findViewById(R.id.placeholder_datasheet);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(mIsVideoTeaser);
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (!mIsVideoTeaser) {
                    stopTimer();
                    reinitPresentation();
                }
            }
        });
        reinitPresentation();
        refreshDisplay();
    }

    @SuppressLint("SetTextI18n")
    public void setVideo(String path, boolean isLooping) {
        //TODO get videos in distant database and save them
//        mVideoView.setVideoPath(path);
        TextView video_placholder = findViewById(R.id.video_placholder);
        video_placholder.setText("Video at : " + path);
        Toast.makeText(getContext(), "Play " + path, Toast.LENGTH_SHORT).show();
        mIsVideo = true;
        mIsVideoTeaser = false;
        refreshDisplay();
        stopTimer();
    }

    @SuppressLint("SetTextI18n")
    public void setDataSheet(Wine wine, WineCuvee wineCuvee, WineDatas wineDatas, WineCuveeDatas wineCuveeDatas) {
        mDatasheetPlaceholder.setText("Datasheet " + wineCuveeDatas.name);
        Bitmap bmpWineImg = new ImgSaver(MDSApp.getContext()).setDirectoryName(wineCuvee.img_directory).setFileName(wineCuvee.img_name).load();
        mImgViewWine.setImageBitmap(bmpWineImg);
        mIsVideo = false;
        mIsVideoTeaser = false;
        refreshDisplay();
        stopTimer();
        putTimer();
    }

    private void stopTimer() {
        mTimerRunning = false;
        mTimer.cancel();
        mTimer.purge();
        mTimer = new Timer();
    }

    private void putTimer() {
        mTimerRunning = true;
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                reinitPresentation();
                mTimerRunning = false;
            }
        }, DELAY_BEFORE_REINIT);
    }

    public void resetTimer() {
        if (mTimerRunning) {
            stopTimer();
            putTimer();
        }
    }

    private void reinitPresentation() {
        if(mCardWineFragment !=null){
            mCardWineFragment.unselect();
        }
        CompanyVideo mVideoTeaser = AppDatabase.getInstance1(getContext()).companyVideoDAO().getDisplayed(true);
        //TODO
//      mVideoView.setVideoPath(mVideoTeaser.path_video);
        mIsVideo = true;
        mIsVideoTeaser = true;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                TextView video_placholder = findViewById(R.id.video_placholder);
                video_placholder.setText("Video at : " + mVideoTeaser.path_video);
                Toast.makeText(getContext(), "Play " + mVideoTeaser.title_video, Toast.LENGTH_SHORT).show();
                refreshDisplay();
            }
        });
    }

    private void refreshDisplay() {
        if (mIsVideo) {
            mVideoLayout.setVisibility(View.VISIBLE);
            mDatasheet.setVisibility(View.GONE);
        } else {
            mVideoLayout.setVisibility(View.GONE);
            mDatasheet.setVisibility(View.VISIBLE);
        }
    }

    public void setCardWineFragment(CardWineFragment cwf) {
        if (mCardWineFragment != null)
            mCardWineFragment.unselect();
        mCardWineFragment = cwf;
        mCardWineFragment.select();
    }
}
