package com.example.layoutexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum HabitCategory {
        MINDFULNESS,
        FINANCIAL,
        HEALTH,
        ENERGY,
        CREATIVITY,
        PRODUCTIVITY
    }

    private class HabitItem {
        public String title;
        public HabitCategory category;

        public HabitItem(String title, HabitCategory category) {
            this.title = title;
            this.category = category;
        }
    }

    private HabitItem[] habitItemArray;

    RelativeLayout addHabit;

    ProgressBar myProgressBar;
    int completedHabitsInt = 2;
    int totalHabitsInt = 5;
    TextView completedHabits, totalHabits;
    LinearLayout habitSpace;
    Intent goToHabitDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        habitItemArray = new HabitItem[6];
        habitItemArray[0] = new HabitItem("Read 10 pages of book", HabitCategory.MINDFULNESS);
        habitItemArray[1] = new HabitItem("Put $5 into savings", HabitCategory.FINANCIAL);
        habitItemArray[2] = new HabitItem("Run 1 mile", HabitCategory.HEALTH);
        habitItemArray[3] = new HabitItem("Go to bed at 10 pm", HabitCategory.ENERGY);
        habitItemArray[4] = new HabitItem("Paint while watching tv", HabitCategory.CREATIVITY);
        habitItemArray[5] = new HabitItem("Send work emails", HabitCategory.PRODUCTIVITY);

        addHabit = findViewById(R.id.add_habit);
        myProgressBar = findViewById(R.id.progress_bar);
        completedHabits = findViewById(R.id.completed_habits);
        totalHabits = findViewById(R.id.total_habits);
        habitSpace = findViewById(R.id.habit_space);
        goToHabitDetails = new Intent(this, Habit_Description.class);

        myProgressBar.setProgress(completedHabitsInt);
        completedHabits.setText(Integer.toString(completedHabitsInt));
        totalHabits.setText(Integer.toString(totalHabitsInt));

        for (HabitItem habit: habitItemArray) {
            LinearLayout habitLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.habit, habitSpace, false);
            ImageView img = habitLayout.findViewById(R.id.image);
            TextView title = habitLayout.findViewById(R.id.title);
            TextView category = habitLayout.findViewById(R.id.category);
            GradientDrawable backgroundDrawable = (GradientDrawable) habitLayout.findViewById(R.id.image_background).getBackground();

            title.setText(habit.title);

            switch (habit.category) {
                case MINDFULNESS:
                    category.setText("Mindfulness");
                    img.setImageResource(R.drawable.mindfulness);
                    habitLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.mindfulness));
                    backgroundDrawable.setColor(ContextCompat.getColor(this, R.color.dark_mindfulness));
                    break;
                case FINANCIAL:
                    category.setText("Financial");
                    img.setImageResource(R.drawable.financial);
                    habitLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.financial));
                    backgroundDrawable.setColor(ContextCompat.getColor(this, R.color.dark_financial));
                    break;
                case HEALTH:
                    category.setText("Health");
                    img.setImageResource(R.drawable.health);
                    habitLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.health));
                    backgroundDrawable.setColor(ContextCompat.getColor(this, R.color.dark_health));
                    break;
                case ENERGY:
                    category.setText("Energy");
                    img.setImageResource(R.drawable.energy);
                    habitLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.energy));
                    backgroundDrawable.setColor(ContextCompat.getColor(this, R.color.dark_energy));
                    break;
                case CREATIVITY:
                    category.setText("Creativity");
                    img.setImageResource(R.drawable.creativity);
                    habitLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.creativity));
                    backgroundDrawable.setColor(ContextCompat.getColor(this, R.color.dark_creativity));
                    break;
                case PRODUCTIVITY:
                    category.setText("Productivity");
                    img.setImageResource(R.drawable.productivity);
                    habitLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.productivity));
                    backgroundDrawable.setColor(ContextCompat.getColor(this, R.color.dark_productivity));
                    break;
            }

            habitLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToHabitDetails.putExtra("habitName", title.getText().toString());
                    goToHabitDetails.putExtra("habitDesc", "a");
                    goToHabitDetails.putExtra("shortGoalInput", 1);
                    goToHabitDetails.putExtra("shortRewardInput", "b");
                    goToHabitDetails.putExtra("longGoalInput", 2);
                    goToHabitDetails.putExtra("longRewardInput", "c");
                    MainActivity.this.startActivity(goToHabitDetails);
                }
            });

            habitSpace.addView(habitLayout);
        }

        int childCount = habitSpace.getChildCount();
        if (childCount > 0) {
            View lastChild = habitSpace.getChildAt(habitSpace.getChildCount() - 1);
            ((LinearLayout.LayoutParams) lastChild.getLayoutParams()).bottomMargin = getResources().getDimensionPixelSize(R.dimen.margin);
        }

        addHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, Add_Habit.class));
            }
        });
    }
}