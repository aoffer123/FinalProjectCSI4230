package com.example.layoutexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Add_Habit extends AppCompatActivity
        implements View.OnClickListener{
    Intent goToHabitDetails;
    EditText habitName, habitDesc, shortGoalInput, shortRewardInput,
            longGoalInput, longRewardInput;
    String habitDescStr, shortRewardStr, habitNameStr, longRewardStr;
    int shortGoalInt, longGoalInt;
    String habitCat = "Financial";

    ImageButton financialBtn, energyBtn, creativityBtn, mindfulBtn, healthBtn,
            productivityBtn, home;
    TextView chosenCat;
    Button createHabit;
    int flagOn = 1, photo;
    SQLiteDatabase db;
    String insertQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        habitName = findViewById(R.id.habitName);
        habitDesc = findViewById(R.id.habitDesc);
        financialBtn = findViewById(R.id.financialBtn);
        energyBtn = findViewById(R.id.energyBtn);
        creativityBtn = findViewById(R.id.creativityBtn);
        mindfulBtn = findViewById(R.id.mindfulBtn);
        healthBtn = findViewById(R.id.healthBtn);
        productivityBtn = findViewById(R.id.productivityBtn);
        home = findViewById(R.id.home);
        shortGoalInput = findViewById(R.id.shortGoalInput);
        shortRewardInput = findViewById(R.id.shortRewardInput);
        longGoalInput = findViewById(R.id.longGoalInput);
        longRewardInput = findViewById(R.id.longRewardInput);
        chosenCat = findViewById(R.id.chosenCat);
        createHabit = findViewById(R.id.createHabit);
        goToHabitDetails = new Intent(this, Habit_Description.class);


        db = MainActivity.db;



        energyBtn.setOnClickListener(this);
        healthBtn.setOnClickListener(this);
        mindfulBtn.setOnClickListener(this);
        financialBtn.setOnClickListener(this);
        creativityBtn.setOnClickListener(this);
        productivityBtn.setOnClickListener(this);



        createHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habitNameStr=habitName.getText().toString();
                habitDescStr=habitDesc.getText().toString();
                shortGoalInt=Integer.valueOf(shortGoalInput.getText().toString());
                shortRewardStr=shortRewardInput.getText().toString();
                longGoalInt=Integer.valueOf(longGoalInput.getText().toString());
                longRewardStr=longRewardInput.getText().toString();

                goToHabitDetails.putExtra("habitName", habitNameStr);
                goToHabitDetails.putExtra("habitDesc", habitDescStr);
                goToHabitDetails.putExtra("STDays", shortGoalInt);
                goToHabitDetails.putExtra("STReward", shortRewardStr);
                goToHabitDetails.putExtra("LTDays", longGoalInt);
                goToHabitDetails.putExtra("LTReward", longRewardStr);
                Cursor maxID = db.rawQuery("select max(habitID) from habit", null);
                int count = maxID.getCount();
                maxID.moveToFirst();
                if (count >= 1){
                    do{
                        int newHabitID = (maxID.getInt(0) + 1);
                        insertQuery = "insert into habit (habitID, habitName, habitDesc, category, STReward, STDays, LTReward, LTDays) values (" + newHabitID + ",'" + habitNameStr + "','"+ habitDescStr + "','" + habitCat +"','" + shortRewardStr + "'," + shortGoalInt + ",'" + longRewardStr + "'," + longGoalInt + ")";
                        db.execSQL(insertQuery);
                        insertQuery = "insert into completedToday (habitID, STDaysComplete, STDaysComplete, [timeStamp(YYYY/MM/DD)]) values (" + newHabitID + ", 0, 0, 'null')";
                        db.execSQL(insertQuery);
                        goToHabitDetails.putExtra("habitID", newHabitID);
                    } while (maxID.moveToNext());
                }
                Add_Habit.this.startActivity(goToHabitDetails);
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

    public void clearBackgrounds() {
        financialBtn.setBackgroundResource(R.color.background);
        creativityBtn.setBackgroundResource(R.color.background);
        healthBtn.setBackgroundResource(R.color.background);
        mindfulBtn.setBackgroundResource(R.color.background);
        energyBtn.setBackgroundResource(R.color.background);
        productivityBtn.setBackgroundResource(R.color.background);
    }

    @Override
    public void onClick(View v) {
        clearBackgrounds();

        if (v.getId() == R.id.financialBtn) {
            financialBtn.setBackgroundResource(R.drawable.circle_background);
            chosenCat.setText("Category Chosen: Financial");
            photo = R.drawable.financial;
            habitCat = "Financial";
        } else if (v.getId() == R.id.creativityBtn) {
            creativityBtn.setBackgroundResource(R.drawable.circle_creative);
            chosenCat.setText("Category Chosen: Creativity");
            photo = R.drawable.creativity;
            habitCat = "Creativity";
        } else if (v.getId() == R.id.healthBtn) {
            healthBtn.setBackgroundResource(R.drawable.circle_health);
            chosenCat.setText("Category Chosen: Health");
            photo = R.drawable.health;
            habitCat = "Health";
        } else if (v.getId() == R.id.mindfulBtn) {
            mindfulBtn.setBackgroundResource(R.drawable.circle_mindful);
            chosenCat.setText("Category Chosen: Mindful");
            photo = R.drawable.mindfulness;
            habitCat = "Mindfulness";
        } else if (v.getId() == R.id.energyBtn) {
            energyBtn.setBackgroundResource(R.drawable.circle_energy);
            chosenCat.setText("Category Chosen: Energy");
            photo = R.drawable.energy;
            habitCat = "Energy";
        } else if (v.getId() == R.id.productivityBtn) {
            productivityBtn.setBackgroundResource(R.drawable.circle_productivity);
            chosenCat.setText("Category Chosen: Productivity");
            photo = R.drawable.productivity;
            habitCat = "Productivity";
        }
        goToHabitDetails.putExtra("catPhoto", photo);
    }




}
