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
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private class Data {
        public int habitID;
        public String habitName;
        public String habitDesc;
        public String category;
        public String STReward;
        public int STDays;
        public String LTReward;
        public int LTDays;
        int STDaysComplete;
        int LTDaysComplete;
        String timeStamp;

        public Data(int habitID,
                    String habitName,
                    String habitDesc,
                    String category,
                    String STReward,
                    int STDays,
                    String LTReward,
                    int LTDays,
                    int STDaysComplete,
                    int LTDaysComplete,
                    String timeStamp) {

            this.habitID = habitID;
            this.habitName = habitName;
            this.habitDesc = habitDesc;
            this.category = category;
            this.STReward = STReward;
            this.STDays = STDays;
            this.LTReward = LTReward;
            this.LTDays = LTDays;
            this.STDaysComplete = STDaysComplete;
            this.LTDaysComplete = LTDaysComplete;
            this.timeStamp = timeStamp;
        }
    }

    ArrayList<Data> dataList;

    RelativeLayout addHabit;

    ProgressBar myProgressBar;
    int completedHabitsInt;
    int totalHabitsInt;
    TextView completedHabits, totalHabits, date;
    LinearLayout todoSpace, completedSpace;
    Intent goToHabitDetails;
    public static SQLiteDatabase db;
    private DBHelper myDBHelper;

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
        date = findViewById(R.id.date);

        addHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, Add_Habit.class));
            }
        });

        createDB();
    }

    @Override
    public void onResume() {

        super.onResume();
        getResults();

        // get current date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String dateToday = df.format(c);
        date.setText(dateToday);

        if (todoSpace != null) {
            todoSpace.removeAllViews();
        }
        if (completedSpace != null) {
            completedSpace.removeAllViews();
        }

        completedHabitsInt = 0;
        totalHabitsInt = 0;
        Log.d("abcde", Integer.toString(dataList.size()));
        for (Data data : dataList) {
            totalHabitsInt++;
            LinearLayout habitLayout;
            if (Objects.equals(dateToday, data.timeStamp)) {
                habitLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.habit, completedSpace, false);
                completedSpace.addView(habitLayout);
                completedHabitsInt++;
            } else {
                habitLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.habit, todoSpace, false);
                todoSpace.addView(habitLayout);
            }

            ImageView img = habitLayout.findViewById(R.id.image);
            TextView category = habitLayout.findViewById(R.id.category);
            GradientDrawable backgroundDrawable = (GradientDrawable) habitLayout.findViewById(R.id.image_background).getBackground();

            TextView title = habitLayout.findViewById(R.id.title);
            title.setText(data.habitName);

            switch (data.category) {
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
                    goToHabitDetails.putExtra("habitID", data.habitID);
                    goToHabitDetails.putExtra("habitName", data.habitName);
                    goToHabitDetails.putExtra("habitDesc", data.habitDesc);
                    goToHabitDetails.putExtra("category", data.category);
                    goToHabitDetails.putExtra("STReward", data.STReward);
                    goToHabitDetails.putExtra("STDays", data.STDays);
                    goToHabitDetails.putExtra("LTReward", data.LTReward);
                    goToHabitDetails.putExtra("LTDays", data.LTDays);
                    goToHabitDetails.putExtra("STDaysComplete", data.STDaysComplete);
                    goToHabitDetails.putExtra("LTDaysComplete", data.LTDaysComplete);
                    goToHabitDetails.putExtra("timeStamp", data.timeStamp);

                    MainActivity.this.startActivity(goToHabitDetails);
                }
            });
        }

        myProgressBar.setProgress(completedHabitsInt);
        myProgressBar.setMax(totalHabitsInt);
        completedHabits.setText(Integer.toString(completedHabitsInt));
        totalHabits.setText(Integer.toString(totalHabitsInt));

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
    }

    public void getResults() {
        Cursor cursor = db.rawQuery("select * from habit join completedToday on habit.habitID = completedToday.habitID", null);
        cursor.moveToFirst();

        dataList = new ArrayList<>();

        if (cursor.getCount() > 0) {
            do {
                dataList.add(new Data(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getInt(7),
                        cursor.getInt(9),
                        cursor.getInt(10),
                        cursor.getString(11)
                ));
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