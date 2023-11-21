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
    Intent createdHabit, changeGoal;
    Button btn, setGoalBtn;
    LinearLayout linLay1;
    PopupWindow popupWin;

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
//        btn = findViewById(R.id.popUp);
        linLay1 = findViewById(R.id.linLay1);

        // get current date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        date.setText(formattedDate);

        //popup window
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LayoutInflater layoutInf = (LayoutInflater) Habit_Description.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View newView = layoutInf.inflate(R.layout.popup, null);
//
//                setGoalBtn = (Button) newView.findViewById(R.id.setNewGoal);
//
//                popupWin = new PopupWindow(newView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//                popupWin.showAtLocation(linLay1, Gravity.CENTER, 0, 0);
//
//                setGoalBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        popupWin.dismiss();
//                    }
//                });
//            }
//        });


        //setting intent
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
        int photo = createdHabit.getIntExtra("catPhoto", R.drawable.financial);
        catPhoto.setImageResource(photo);
        changeGoal = new Intent(this, Habit_Description.class);

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
                    LayoutInflater layoutInf = (LayoutInflater) Habit_Description.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View newView = layoutInf.inflate(R.layout.popup, null);
                    setGoalBtn = (Button) newView.findViewById(R.id.setNewGoal);
                    popupWin = new PopupWindow(newView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWin.showAtLocation(linLay1, Gravity.CENTER, 0, 0);
                    setGoalBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWin.dismiss();

//                            Habit_Description.this.startActivity(changeGoal);
                        }
                    });
                }
            }
        });

    }
}