package com.example.layoutexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Objects;

public class GoalComplete extends AppCompatActivity {
    Button setNewGoal;
    Intent changeGoal;
    TextView goalTypeTV;
    ImageButton home;
    SQLiteDatabase db;

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
        int habitID = myIntent.getIntExtra("habitID", 0);
        db = MainActivity.db;

        setNewGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGoal.putExtra("goalType", goalStr);
                changeGoal.putExtra("habitID", habitID);
                GoalComplete.this.startActivity(changeGoal);
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(goalStr, "Short-Term Goal")) {
                    db.execSQL("update completedToday set STDaysComplete = 0 where habitID = " + habitID);
                } else if (Objects.equals(goalStr, "Long-Term Goal")){
                    db.execSQL("update completedToday set LTDaysComplete = 0 where habitID = " + habitID);
                }
                finish();
            }
        });
    }



}