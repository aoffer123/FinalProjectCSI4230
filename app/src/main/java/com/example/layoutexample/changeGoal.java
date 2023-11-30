package com.example.layoutexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class changeGoal extends AppCompatActivity {
    TextView goal;
    EditText newReward, newGoal;
    Button createNewGoal;
    ImageButton home;
    private static SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_goal);
        goal = findViewById(R.id.goalType);
        createNewGoal = findViewById(R.id.createGoal);
        newReward = findViewById(R.id.newRewardInput);
        newGoal = findViewById(R.id.newGoalInput);
        home = findViewById(R.id.home);
        Intent myIntent = getIntent();
        String goalStr = myIntent.getStringExtra("goalType");
        goal.setText("Add New " + goalStr);
//        db = MainActivity.db;

        createNewGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rewardStr;
                int goalInt;
                rewardStr=newReward.getText().toString();
                goalInt=Integer.valueOf(newGoal.getText().toString());

//                ContentValues newValues = new ContentValues();
//                newValues.put("STReward", rewardStr);
//                db.update("habit", newValues, "HabitID=6", null);

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