package com.example.layoutexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private class HabitItem {
        public int habitID;
        public String habitName;
        public String habitDesc;
        public String category;
        public String STReward;
        public int STDays;
        public String LTReward;
        public int LTDays;

        public HabitItem(int habitID,
                         String habitName,
                         String habitDesc,
                         String category,
                         String STReward,
                         int STDays,
                         String LTReward,
                         int LTDays) {

            this.habitID = habitID;
            this.habitName = habitName;
            this.habitDesc = habitDesc;
            this.category = category;
            this.STReward = STReward;
            this.STDays = STDays;
            this.LTReward = LTReward;
            this.LTDays = LTDays;
        }
    }

    ArrayList<HabitItem> habitList;

    RelativeLayout addHabit;

    ProgressBar myProgressBar;
    int completedHabitsInt = 3;
    int totalHabitsInt = 0;
    int totalCompletedInt = 0;
    TextView completedHabits, totalHabits, date;
    LinearLayout todoSpace, completedSpace;
    Intent goToHabitDetails;
    public static SQLiteDatabase db;
    private DBHelper myDBHelper;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addHabit = findViewById(R.id.add_habit);
        myProgressBar = findViewById(R.id.progress_bar);
        completedHabits = findViewById(R.id.completed_habits);
        totalHabits = findViewById(R.id.total_habits);
        todoSpace = findViewById(R.id.todo_space);
        completedSpace = findViewById(R.id.completed_space);
        goToHabitDetails = new Intent(this, Habit_Description.class);

        createDB();
        getResult("select * from habit");

        myProgressBar.setProgress(completedHabitsInt);
        completedHabits.setText(Integer.toString(completedHabitsInt));
        totalHabits.setText(Integer.toString(totalHabitsInt));

        date = findViewById(R.id.date);

        // get current date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        date.setText(formattedDate);

        createDB();
        getResult("select * from habit");


        for (HabitItem habit: habitList) {
            LinearLayout habitLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.habit, todoSpace, false);
            ImageView img = habitLayout.findViewById(R.id.image);
            TextView title = habitLayout.findViewById(R.id.title);
            TextView category = habitLayout.findViewById(R.id.category);
            GradientDrawable backgroundDrawable = (GradientDrawable) habitLayout.findViewById(R.id.image_background).getBackground();

            title.setText(habit.habitName);

            switch (habit.category) {
                case "Mindfulness":
                    category.setText("Mindfulness");
                    img.setImageResource(R.drawable.mindfulness);
                    habitLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.mindfulness));
                    backgroundDrawable.setColor(ContextCompat.getColor(this, R.color.dark_mindfulness));
                    break;
                case "Financial":
                    category.setText("Financial");
                    img.setImageResource(R.drawable.financial);
                    habitLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.financial));
                    backgroundDrawable.setColor(ContextCompat.getColor(this, R.color.dark_financial));
                    break;
                case "Health":
                    category.setText("Health");
                    img.setImageResource(R.drawable.health);
                    habitLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.health));
                    backgroundDrawable.setColor(ContextCompat.getColor(this, R.color.dark_health));
                    break;
                case "Energy":
                    category.setText("Energy");
                    img.setImageResource(R.drawable.energy);
                    habitLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.energy));
                    backgroundDrawable.setColor(ContextCompat.getColor(this, R.color.dark_energy));
                    break;
                case "Creativity":
                    category.setText("Creativity");
                    img.setImageResource(R.drawable.creativity);
                    habitLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.creativity));
                    backgroundDrawable.setColor(ContextCompat.getColor(this, R.color.dark_creativity));
                    break;
                case "Productivity":
                    category.setText("Productivity");
                    img.setImageResource(R.drawable.productivity);
                    habitLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.productivity));
                    backgroundDrawable.setColor(ContextCompat.getColor(this, R.color.dark_productivity));
                    break;
            }

            habitLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToHabitDetails.putExtra("habitID", habit.habitID);
                    goToHabitDetails.putExtra("habitName", habit.habitName);
                    goToHabitDetails.putExtra("habitDesc", habit.habitDesc);
                    goToHabitDetails.putExtra("category", habit.category);
                    goToHabitDetails.putExtra("STReward", habit.STReward);
                    goToHabitDetails.putExtra("STDays", habit.STDays);
                    goToHabitDetails.putExtra("LTReward", habit.LTReward);
                    goToHabitDetails.putExtra("LTDays", habit.LTDays);
                    String query = "select * from completedToday where habitID = " + habit.habitID;
                    Cursor tableResult = db.rawQuery(query, null);
                    int count = tableResult.getCount();
                    tableResult.moveToFirst();
                    if (count >= 1){
                        do{
                            goToHabitDetails.putExtra("completedID", tableResult.getInt(0));
                            goToHabitDetails.putExtra("STDaysComplete", tableResult.getInt(2));
                            goToHabitDetails.putExtra("LTDaysComplete", tableResult.getInt(3));
                            goToHabitDetails.putExtra("timeStamp", tableResult.getString(4));
                        } while(tableResult.moveToNext());
                    }
                    MainActivity.this.startActivity(goToHabitDetails);
                }
            });

            if (count % 2 == 0)
                todoSpace.addView(habitLayout);
            else
                completedSpace.addView(habitLayout);
            count++;
        }

        int childCount = todoSpace.getChildCount();
        if (childCount > 0) {
            View lastChild = todoSpace.getChildAt(todoSpace.getChildCount() - 1);
            ((LinearLayout.LayoutParams) lastChild.getLayoutParams()).bottomMargin = getResources().getDimensionPixelSize(R.dimen.margin);
        }

        childCount = completedSpace.getChildCount();
        if (childCount > 0) {
            View lastChild = completedSpace.getChildAt(completedSpace.getChildCount() - 1);
            ((LinearLayout.LayoutParams) lastChild.getLayoutParams()).bottomMargin = getResources().getDimensionPixelSize(R.dimen.margin);
        }

        addHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, Add_Habit.class));
            }
        });
    }

    public void getResult(String query) {
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        habitList = new ArrayList<>();

        if (cursor.getCount() > 1) {
            do {
                habitList.add(new HabitItem(
                        cursor.getInt(cursor.getColumnIndex("habitID")),
                        cursor.getString(cursor.getColumnIndex("habitName")),
                        cursor.getString(cursor.getColumnIndex("habitDesc")),
                        cursor.getString(cursor.getColumnIndex("category")),
                        cursor.getString(cursor.getColumnIndex("STReward")),
                        cursor.getInt(cursor.getColumnIndex("STDays")),
                        cursor.getString(cursor.getColumnIndex("LTReward")),
                        cursor.getInt(cursor.getColumnIndex("LTDays"))
                ));
                totalHabitsInt++;
            } while (cursor.moveToNext());
        }
    }

    public void createDB() {
        myDBHelper = new DBHelper(this);
        try {
            myDBHelper.createDataBase();
        } catch (IOException e) {
            throw new Error("Unable to create database");
        }

        try {
            myDBHelper.openDataBase();
        } catch (SQLException e) {

        }

        db = myDBHelper.getWritableDatabase();
    }
}