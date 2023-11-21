package com.example.layoutexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener{
    Intent goToHabitDetails;
    EditText habitName, habitDesc, shortGoalInput, shortRewardInput,
            longGoalInput, longRewardInput;
    String habitDescStr, habitCat, shortGoalStr, shortRewardStr, habitNameStr,
            longGoalStr, longRewardStr;

    ImageButton financialBtn, energyBtn, creativityBtn, mindfulBtn, healthBtn,
            productivityBtn;
    TextView chosenCat;
    Button createHabit;
    int flagOn = 1, photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        habitName = findViewById(R.id.habitName);
        habitDesc = findViewById(R.id.habitDesc);
        financialBtn = findViewById(R.id.financialBtn);
        energyBtn = findViewById(R.id.energyBtn);
        creativityBtn = findViewById(R.id.creativityBtn);
        mindfulBtn = findViewById(R.id.mindfulBtn);
        healthBtn = findViewById(R.id.healthBtn);
        productivityBtn = findViewById(R.id.productivityBtn);
        shortGoalInput = findViewById(R.id.shortGoalInput);
        shortRewardInput = findViewById(R.id.shortRewardInput);
        longGoalInput = findViewById(R.id.longGoalInput);
        longRewardInput = findViewById(R.id.longRewardInput);
        chosenCat = findViewById(R.id.chosenCat);
        createHabit = findViewById(R.id.createHabit);
        goToHabitDetails = new Intent(this, Habit_Description.class);

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
                shortGoalStr=shortGoalInput.getText().toString();
                shortRewardStr=shortRewardInput.getText().toString();
                longGoalStr=longGoalInput.getText().toString();
                longRewardStr=longRewardInput.getText().toString();

                goToHabitDetails.putExtra("habitName", habitNameStr);
                goToHabitDetails.putExtra("habitDesc", habitDescStr);
                goToHabitDetails.putExtra("shortGoalInput", shortGoalStr);
                goToHabitDetails.putExtra("shortRewardInput", shortRewardStr);
                goToHabitDetails.putExtra("longGoalInput", longGoalStr);
                goToHabitDetails.putExtra("longRewardInput", longRewardStr);
                MainActivity.this.startActivity(goToHabitDetails);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.financialBtn) {
            financialBtn.setBackgroundResource(R.drawable.circle_background);
            chosenCat.setText("Category Chosen: Financial");
            photo = R.drawable.financial;
        } else if (v.getId() == R.id.creativityBtn) {
            creativityBtn.setBackgroundResource(R.drawable.circle_creative);
            chosenCat.setText("Category Chosen: Creativity");
            photo = R.drawable.creativity;
        } else if (v.getId() == R.id.healthBtn) {
            healthBtn.setBackgroundResource(R.drawable.circle_health);
            chosenCat.setText("Category Chosen: Health");
            photo = R.drawable.health;
        } else if (v.getId() == R.id.mindfulBtn) {
            mindfulBtn.setBackgroundResource(R.drawable.circle_mindful);
            chosenCat.setText("Category Chosen: Mindful");
            photo = R.drawable.mindful;
        } else if (v.getId() == R.id.energyBtn) {
            energyBtn.setBackgroundResource(R.drawable.circle_energy);
            chosenCat.setText("Category Chosen: Energy");
            photo = R.drawable.energy;
        } else if (v.getId() == R.id.productivityBtn) {
            productivityBtn.setBackgroundResource(R.drawable.circle_productivity);
            chosenCat.setText("Category Chosen: Productivity");
            photo = R.drawable.productivity;
        }
        goToHabitDetails.putExtra("catPhoto", photo);
    }



}
