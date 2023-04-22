package com.as.truthordare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView arrow;
    int currentPoint,nextPoint;

    TextView headingTV;
    LinearLayout buttonHolder;

    Button truth,dare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        arrow = findViewById(R.id.arrow);
        headingTV = findViewById(R.id.heading);
        buttonHolder = findViewById(R.id.buttonHolderLayout);
        truth = findViewById(R.id.truthButton);
        dare = findViewById(R.id.dareButton);

        arrow.setOnClickListener(v -> spinArrow());

        truth.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,TruthOrDareActivity.class);
            intent.putExtra("flag","1");
            startActivity(intent);
        });

        dare.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,TruthOrDareActivity.class);
            intent.putExtra("flag","2");
            startActivity(intent);
        });


    }

    private void spinArrow() {
        //generating a random number
        nextPoint = new Random().nextInt(10000);

        int px = arrow.getWidth()/2;
        int py = arrow.getHeight()/2;

        Animation rotate = new RotateAnimation(currentPoint,nextPoint,px,py);
        rotate.setDuration(3000);
        rotate.setFillAfter(true);

        currentPoint = nextPoint;

        arrow.startAnimation(rotate);

        // listener on rotate

        rotate.setAnimationListener(new Animation.AnimationListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onAnimationStart(Animation animation) {

                headingTV.setText("Rotating...");
                buttonHolder.setVisibility(View.GONE);

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onAnimationEnd(Animation animation) {
                headingTV.setText("Choose Truth or Dare?");
                buttonHolder.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}