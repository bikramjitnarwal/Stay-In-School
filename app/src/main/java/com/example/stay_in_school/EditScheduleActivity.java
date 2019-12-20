package com.example.stay_in_school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        final int[] thankmrgooseIDs = {R.id.thank1, R.id.thank2, R.id.thank3,
                R.id.thank4, R.id.thank5, R.id.thank6, R.id.thank7, R.id.thank8,
                R.id.thank9, R.id.thank10, R.id.thank11, R.id.thank12, R.id.thank13,
                R.id.thank14, R.id.thank15, R.id.thank16, R.id.thank17, R.id.thank18,
                R.id.thank19, R.id.thank20};

        Button[] thankmrgooseButtons = new Button[20];
        for (int i = 0; i < 20; i++) {
            thankmrgooseButtons[i] = findViewById(thankmrgooseIDs[i]);
            thankmrgooseButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(EditScheduleActivity.this, "thank mr goose", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
