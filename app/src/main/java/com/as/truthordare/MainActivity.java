package com.as.truthordare;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageView arrow,openMenuButton;
    int currentPoint,nextPoint;

    TextView headingTV;
    LinearLayout buttonHolder;

    Button truth,dare;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

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
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_View);
        openMenuButton = findViewById(R.id.openMenuIV);

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


        openMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });


        navigationView.setNavigationItemSelectedListener(MainActivity.this);


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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
            if(id==R.id.menu_about) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.baseline_info_24);
                builder.setTitle("About");
                builder.setMessage(R.string.about);
                builder.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
                builder.show();
            }

            else if(id==R.id.menu_share){
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"Found an amazing game!\n"+"https://play.google.com/store/apps/details?id=com.as.truthordare");
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TITLE,"Sharing the App!");
                Intent shareIntent = Intent.createChooser(sendIntent,null);
                startActivity(shareIntent);
            }
            else if(id==R.id.menu_email){
                String email ="ayushojha1512@gmail.com";
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+email));
                startActivity(Intent.createChooser(emailIntent,"Mail"));
            }
            else if (id==R.id.menu_exit){
                finishAffinity();
            }

        return false;
    }
}