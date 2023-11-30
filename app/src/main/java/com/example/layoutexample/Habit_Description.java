package com.example.layoutexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Habit_Description extends AppCompatActivity {
    TextView date, habitDetails, shortGoal, shortReward,
            longGoal, longReward, shortProgress, longProgress;
    int indexShort=0, indexLong=0;
    ImageView catPhoto;
    CheckBox habitCB;
    Intent myIntent, goalComplete;
    LinearLayout linLay1;
    ImageButton home;

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
        home = findViewById(R.id.home);

        // get current date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        date.setText(formattedDate);


        //setting intent
        myIntent = getIntent();
        String createdName = myIntent.getStringExtra("habitName");
        String createdDesc = myIntent.getStringExtra("habitDesc");
        String finalDetails = createdName + "\n" + createdDesc;
        habitDetails.setText(finalDetails);
        int numberOfDays = (myIntent.getIntExtra("STDays", 0));
        shortGoal.setText("Complete this habit " + numberOfDays + " days in a row");
        String createdShortReward = myIntent.getStringExtra("STReward");
        shortReward.setText(createdShortReward);
        int numberOfDaysLong = myIntent.getIntExtra("LTDays", 0);
        longGoal.setText("Complete this habit " + numberOfDaysLong + " days in a row");
        String createdLongReward = myIntent.getStringExtra("LTReward");
        longReward.setText(createdLongReward);
        shortProgress.setText("Progress: " + indexShort + "/" + numberOfDays);
        longProgress.setText("Progress: " + indexLong + "/" + numberOfDaysLong);
        int photo = myIntent.getIntExtra("catPhoto", R.drawable.financial);
        catPhoto.setImageResource(photo);

        String category = myIntent.getStringExtra("category");
        if (Objects.equals(category, "Mindfulness")){
            catPhoto.setImageResource(R.drawable.mindfulness);
        }else if (Objects.equals(category, "Finacial")){
            catPhoto.setImageResource(R.drawable.financial);
        }else if (Objects.equals(category, "Health")){
            catPhoto.setImageResource(R.drawable.health);
        }else if (Objects.equals(category, "Energy")){
            catPhoto.setImageResource(R.drawable.energy);
        }else if (Objects.equals(category, "Creativity")){
            catPhoto.setImageResource(R.drawable.creativity);
        }else if (Objects.equals(category, "Productivity")){
            catPhoto.setImageResource(R.drawable.productivity);
        }


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
                    goalComplete.putExtra("goalType", "Long-Term Goal");
                    Habit_Description.this.startActivity(goalComplete);
                    finish();
                }
                else if(indexShort == numberOfDays){
                    goalComplete.putExtra("goalType", "Short-Term Goal");
                    Habit_Description.this.startActivity(goalComplete);
                    finish();
                }
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