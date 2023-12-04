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

import java.util.Objects;

public class changeGoal extends AppCompatActivity {
    TextView goal;
    EditText newReward, newGoal;
    Button createNewGoal;
    ImageButton home;
    String updateQuery = "";
    SQLiteDatabase db;
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
        db = MainActivity.db;
        int habitID = myIntent.getIntExtra("habitID", 0);

        createNewGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rewardStr;
                int goalInt;
                rewardStr=newReward.getText().toString();
                goalInt=Integer.parseInt(newGoal.getText().toString());
                if (Objects.equals(goalStr, "Short-Term Goal")){
                    db.execSQL("update habit set STDays = " + goalInt + " where habitID = " + habitID);
                    db.execSQL("update habit set STReward = '" + rewardStr + "' where habitID = " + habitID);
                    db.execSQL("update completedToday set STDaysComplete = 0 where habitID = " + habitID);
                }
                else if (Objects.equals(goalStr, "Long-Term Goal")){
                    db.execSQL("update habit set LTDays = " + goalInt + " where habitID = " + habitID);
                    db.execSQL("update habit set LTReward = '" + rewardStr + "' where habitID = " + habitID);
                    db.execSQL("update completedToday set LTDaysComplete = 0 where habitID = " + habitID);
                }
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