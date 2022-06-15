package com.metadjioo_ds.view.activity.used;

import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;

import com.metadjioo_ds.R;
import com.metadjioo_ds.view.activity.MDSActivity;
import com.metadjioo_ds.view.presentation.EmptyPresentation;

public class ExperienceActivity extends MDSActivity {

    public static final int SCROLL_DELTA = 300;

    private HorizontalScrollView scrollView;
    private ImageButton btnScrollLeft;
    private ImageButton buttonScrollRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experience);
        initSecondMonitor();

        scrollView = findViewById(R.id.experience_scroll_view);
        btnScrollLeft = findViewById(R.id.scroll_left);
        buttonScrollRight = findViewById(R.id.scroll_right);
        btnScrollLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollLeft();
            }
        });

        buttonScrollRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollRight();
            }
        });
    }

    public void initSecondMonitor(){
        DisplayManager dm = (DisplayManager) getSystemService(DISPLAY_SERVICE);
        if (dm != null)
        {
            Display[] displays = dm.getDisplays();
            if(displays.length>0){
                Display display = displays[1];
                mPresentation = new EmptyPresentation(this, display);
                mPresentation.show();
            }
        }
    }

    private void scrollRight(){
        int new_x = this.scrollView.getScrollX()+SCROLL_DELTA;
        this.scrollView.scrollTo(new_x, this.scrollView.getScrollY());
    }

    private void scrollLeft(){
        int new_x = this.scrollView.getScrollX()-SCROLL_DELTA;
        this.scrollView.scrollTo(new_x, this.scrollView.getScrollY());
    }
}