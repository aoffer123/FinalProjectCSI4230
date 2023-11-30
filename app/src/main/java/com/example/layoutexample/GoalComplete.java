package com.example.layoutexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class GoalComplete extends AppCompatActivity {
    Button setNewGoal;
    Intent changeGoal;
    TextView goalTypeTV;
    ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_complete);
        setNewGoal = findViewById(R.id.setNewGoal);
        changeGoal = new Intent(this, changeGoal.class);
        goalTypeTV = findViewById(R.id.goalType);
        home = findViewById(R.id.home);
        Intent myIntent = getIntent();
        String goalStr = myIntent.getStringExtra("goalType");
        goalTypeTV.setText(goalStr);

        setNewGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGoal.putExtra("goalType", goalStr);
                GoalComplete.this.startActivity(changeGoal);
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}