package com.example.layoutexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Habit_Description extends AppCompatActivity {
    TextView habitDetails;
    TextView shortGoal;

    TextView shortReward;

    TextView longGoal;

    TextView longReward;

    TextView shortProgress;
    TextView longProgress;

    int indexShort=0;

    int indexLong=0;



    Intent createdHabit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_description);
        habitDetails = findViewById(R.id.habitDetails);
        shortGoal = findViewById(R.id.shortGoal);
        shortReward = findViewById(R.id.shortReward);
        longGoal = findViewById(R.id.longGoal);
        longReward = findViewById(R.id.longReward);
        shortProgress = findViewById(R.id.shortProgress);
        longProgress = findViewById(R.id.longProgress);

        createdHabit = getIntent();
        String createdName = createdHabit.getStringExtra("habitName");
        String createdDesc = createdHabit.getStringExtra("habitDesc");
        String finalDetails = createdName + "\n" + createdDesc;
        habitDetails.setText(finalDetails);
        String createdShortTerm = createdHabit.getStringExtra("shortGoalInput");
        shortGoal.setText("Complete this habit " + createdShortTerm + " days in a row");
        String createdShortReward = createdHabit.getStringExtra("shortRewardInput");
        shortReward.setText(createdShortReward);
        String createdLongTerm = createdHabit.getStringExtra("longGoalInput");
        longGoal.setText("Complete this habit " + createdLongTerm + " days in a row");
        String createdLongReward = createdHabit.getStringExtra("longRewardInput");
        longReward.setText(createdLongReward);
        int numberOfDays = Integer.parseInt(createdShortTerm);
        shortProgress.setText("Progress: " + indexShort + "/" + numberOfDays);
        int numberOfDaysLong = Integer.parseInt(createdLongTerm);
        longProgress.setText("Progress: " + indexLong + "/" + numberOfDaysLong);


    }
}