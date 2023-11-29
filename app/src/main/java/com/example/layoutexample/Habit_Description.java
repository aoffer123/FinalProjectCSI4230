package com.example.layoutexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Habit_Description extends AppCompatActivity {
    TextView date, habitDetails, shortGoal, shortReward,
            longGoal, longReward, shortProgress, longProgress;
    int indexShort=0, indexLong=0;
    ImageView catPhoto;
    CheckBox habitCB;
    Intent createdHabit, goalComplete;
    LinearLayout linLay1;

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
        date = findViewById(R.id.date);
        catPhoto = findViewById(R.id.catPhoto);
        habitCB = findViewById(R.id.habitCompletedCB);
        linLay1 = findViewById(R.id.linLay1);

        // get current date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        date.setText(formattedDate);


        //setting intent
        createdHabit = getIntent();
        String createdName = createdHabit.getStringExtra("habitName");
        String createdDesc = createdHabit.getStringExtra("habitDesc");
        String finalDetails = createdName + "\n" + createdDesc;
        habitDetails.setText(finalDetails);
        int numberOfDays = (createdHabit.getIntExtra("shortGoalInput", 0));
        shortGoal.setText("Complete this habit " + numberOfDays + " days in a row");
        String createdShortReward = createdHabit.getStringExtra("shortRewardInput");
        shortReward.setText(createdShortReward);
        int numberOfDaysLong = createdHabit.getIntExtra("longGoalInput", 0);
        longGoal.setText("Complete this habit " + numberOfDaysLong + " days in a row");
        String createdLongReward = createdHabit.getStringExtra("longRewardInput");
        longReward.setText(createdLongReward);
        shortProgress.setText("Progress: " + indexShort + "/" + numberOfDays);
        longProgress.setText("Progress: " + indexLong + "/" + numberOfDaysLong);
        int photo = createdHabit.getIntExtra("catPhoto", R.drawable.financial);
        catPhoto.setImageResource(photo);
        goalComplete = new Intent(this, GoalComplete.class);

        habitCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(habitCB.isChecked()){
                    indexShort += 1;
                    indexLong += 1;
                }
                else{
                    indexShort -= 1;
                    indexLong -= 1;
                }
                shortProgress.setText("Progress: " + indexShort + "/" + numberOfDays);
                longProgress.setText("Progress: " + indexLong + "/" + numberOfDaysLong);

                if(indexLong == numberOfDaysLong){
                    goalComplete.putExtra("goalType", "Long-term goal");
                    Habit_Description.this.startActivity(goalComplete);
                }
                else if(indexShort == numberOfDays){
                    goalComplete.putExtra("goalType", "Short-term goal");
                    Habit_Description.this.startActivity(goalComplete);
                }
            }
        });

    }
}