package com.as.truthordare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class TruthOrDareActivity extends AppCompatActivity {

    TextView headingOfCardView, mainContent;
    private String url;
    CardView mainCardView;
    ProgressBar progressBar;
    LinearLayout abortOrDoneBtn;

    Button abort,done;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truth_or_dare);

        headingOfCardView = findViewById(R.id.headingCard);
        mainContent = findViewById(R.id.contentTV);
        mainCardView = findViewById(R.id.cardView);
        progressBar = findViewById(R.id.progressBar);
        abortOrDoneBtn = findViewById(R.id.abortAndDoneBtnHolder);
        abort = findViewById(R.id.abortButton);
        done = findViewById(R.id.doneButton);

        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        //getting flag to check either truth is pressed or dare is pressed

        String f = getIntent().getStringExtra("flag");

        //setting urls accordingly

        if(f.equals("1")){
            url ="https://api.truthordarebot.xyz/v1/truth";
            makeRequest();
        }

        else if(f.equals("2")){
            headingOfCardView.setText("Dare");
            url = "https://api.truthordarebot.xyz/v1/dare";
            makeRequest();
        }


        //listeners to abort and done button

        abort.setOnClickListener(v -> {
            vibe.vibrate(100);
            mainContent.setText("The Player Lost!");
            mainContent.setTextColor(Color.RED);
            new Handler().postDelayed(() -> {
                startActivity(new Intent(TruthOrDareActivity.this,MainActivity.class));
                finish();
            },800);
        });

        done.setOnClickListener(v -> {
            vibe.vibrate(100);
            mainContent.setText("The Player Won!");
            mainContent.setTextColor(Color.RED);
            new Handler().postDelayed(() -> {
                startActivity(new Intent(TruthOrDareActivity.this,MainActivity.class));
                finish();
            },800);
        });
    }


    private void makeRequest(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this::parseResponse, error -> {

        });

        queue.add(jsonObjectRequest);

    }

    private void parseResponse(JSONObject response) {

        try {
            String question = response.getString("question");
            mainContent.setText(question);
            mainCardView.setVisibility(View.VISIBLE);
            abortOrDoneBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }   catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}