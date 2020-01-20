package com.tistory.firewallgogo.baseballgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_second extends AppCompatActivity {

    ImageView iv;
    TextView tvstartbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        iv = findViewById(R.id.fisrtstartbtn);
        iv.setOnClickListener(firstbtnlistener);
        tvstartbtn=findViewById(R.id.tvpressthebutton);

        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_pressthebutton);
        tvstartbtn.startAnimation(startAnimation);
    }//onCreate

    View.OnClickListener firstbtnlistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(activity_second.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };





}//activity_second
