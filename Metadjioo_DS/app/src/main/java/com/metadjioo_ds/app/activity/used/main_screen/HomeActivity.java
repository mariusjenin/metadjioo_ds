package com.metadjioo_ds.app.activity.used.main_screen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.metadjioo_ds.app.activity.MDSActivityMainScreen;
import com.metadjioo_ds.app.activity.used.second_screen.EmptyActivity;
import com.metadjioo_ds.R;
import com.metadjioo_ds.utils.Utils;

public class HomeActivity extends MDSActivityMainScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Button btn_setup = findViewById(R.id.setup_experience);
        Button btn_launch = findViewById(R.id.launch_experience);
        btn_setup.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
            HomeActivity.this.startActivity(intent);
        });

        btn_launch.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ExperienceActivity.class);
            HomeActivity.this.startActivity(intent);
            finish();
        });
    }


    public void initSecondScreen(){
        Utils.launchActivityOnSecondScreen(EmptyActivity.class);
    }
}