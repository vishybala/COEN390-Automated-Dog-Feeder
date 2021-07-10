package com.dogfeeder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CustomisedMealActivity extends AppCompatActivity {

    TextView feedTime1, feedTime2, feedTime3, feedTime4;
    EditText amountEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customised_meal);

        initViews();
        setOnClickListeners();


    }

    private void initViews() {
        feedTime1 = findViewById(R.id.feedTime1Txt);
        feedTime2 = findViewById(R.id.feedTime2Txt);
        feedTime3 = findViewById(R.id.feedTime3Txt);
        feedTime4 = findViewById(R.id.feedTime4Txt);
        amountEdit = findViewById(R.id.feedEdit);
    }

    private void setOnClickListeners() {

        findViewById(R.id.feedTime1Edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(1);
            }
        });

        findViewById(R.id.feedTime2Edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(2);
            }
        });

        findViewById(R.id.feedTime3Edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(3);
            }
        });

        findViewById(R.id.feedTime4Edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(4);
            }
        });

        findViewById(R.id.nextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amount = amountEdit.getText().toString();
                String meal1Time = feedTime1.getText().toString();
                String meal2Time = feedTime2.getText().toString();
                String meal3Time = feedTime3.getText().toString();
                String meal4Time = feedTime4.getText().toString();

                if(amount.isEmpty())
                {
                    amountEdit.setError("Field is required");
                    amountEdit.requestFocus();
                    return;
                }


                if(meal1Time.equals("Answer") && meal2Time.equals("Answer")
                        && meal3Time.equals("Answer") && meal4Time.equals("Answer"))
                {
                    Toast.makeText(CustomisedMealActivity.this, "Set at least one feed time", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!meal1Time.equals("Answer"))
                {
                    Helper.setFoodAlarm(
                            CustomisedMealActivity.this,
                            Helper.getCalendarObj(meal1Time),
                            1000
                    );
                }

                if(!meal2Time.equals("Answer"))
                {
                    Helper.setFoodAlarm(
                            CustomisedMealActivity.this,
                            Helper.getCalendarObj(meal2Time),
                            1001);
                }

                if(!meal3Time.equals("Answer"))
                {
                    Helper.setFoodAlarm(
                            CustomisedMealActivity.this,
                            Helper.getCalendarObj(meal3Time),
                            1002);
                }

                if(!meal4Time.equals("Answer"))
                {
                    Helper.setFoodAlarm(CustomisedMealActivity.this,
                            Helper.getCalendarObj(meal4Time),
                            10003);
                }

                FirebaseDatabase.getInstance().getReference()
                        .child("suggested_food_amount")
                        .setValue(amount);


                Intent intent = new Intent(CustomisedMealActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });


    }

    private void showTimePicker(int num) {

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {

            Log.d("HGS", hourOfDay + ":" + minute1);

            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute1);

            String time = new SimpleDateFormat("hh:mm", Locale.getDefault())
                    .format(calendar.getTime());

            if(num == 1)
            {
                feedTime1.setText(time);
            }
            else if(num == 2)
            {
                feedTime2.setText(time);
            }
            else if(num == 3)
            {
                feedTime3.setText(time);
            }
            else
            {
                feedTime4.setText(time);
            }

        }, hour, minute , true);

        dialog.show();

    }
}