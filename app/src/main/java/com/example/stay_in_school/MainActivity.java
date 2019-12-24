package com.example.stay_in_school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button settingsButton = findViewById(R.id.settings_button);
        Button thankButton = findViewById(R.id.thank_mr_goose);
        Button editScheduleButton = findViewById(R.id.edit_schedule);

//        settingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent settingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
//                startActivity(settingsActivity);
//            }
//        });

//        thankButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent thankmrgooseActivity = new Intent(MainActivity.this, ThankMrGooseActivity.class);
//                startActivity(thankmrgooseActivity);
//            }
//        });

        editScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editScheduleActivity = new Intent(MainActivity.this, EditScheduleActivity.class);
                startActivity(editScheduleActivity);
            }
        });

    }
}
