package com.example.paddycropdisease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutSceen extends AppCompatActivity {

    String about = "This app identifies disease based on the image provided and " +
            "provides you suitable solution need to be used";

    String disclaimer = "This app (“App)” provides only information, " +
            "and not treatment advice and may not be treated as such by the user. " +
            "Users should upload only images of diseased paddy crop. This app will identify only four diseases(Blast," +
            "Bacterial Blight, Tungro, BrownSpot). "+
            "The information on this App is not a substitute for professional advice or treatment. " +
            "You are strongly encouraged to confirm any information obtained from or through this App with your Agricultural Specialist " +
            "and to review all information regarding any disease with your crop and its treatment.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_sceen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tvAbout = findViewById(R.id.tvAbout);
        TextView tvDisclaimer = findViewById(R.id.tvDisclaimer);
        Button btnBack = findViewById(R.id.btnBack);

        tvAbout.setText(about);
        tvDisclaimer.setText(disclaimer);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}