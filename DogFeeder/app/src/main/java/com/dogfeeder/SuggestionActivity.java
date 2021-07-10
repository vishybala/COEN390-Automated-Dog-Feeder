package com.dogfeeder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class SuggestionActivity extends AppCompatActivity {

    TextView amountTxt, meal1Txt, meal2Txt, meal3Txt, meal4Txt;
    int suggestedAmount = 0;
    int mealTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        initViews();
        calculateAmount();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        
        findViewById(R.id.useSuggestionBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference()
                        .child("suggested_food_amount")
                        .setValue(suggestedAmount);
                
                setMealAlarms();

                Intent intent = new Intent(SuggestionActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                
            }
        });

        findViewById(R.id.customizedBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuggestionActivity.this, CustomisedMealActivity.class);
                startActivity(intent);
            }
        });
        
    }

    private void setMealAlarms() {
        if(mealTime == 2)
        {
            Helper.setFoodAlarm(this, Helper.getCalendarObj(Const.MEAL_1_TIME), 1000);
            Helper.setFoodAlarm(this, Helper.getCalendarObj(Const.MEAL_2_TIME),1001);
        }
        else if(mealTime == 3)
        {
            Helper.setFoodAlarm(this, Helper.getCalendarObj(Const.MEAL_1_TIME),1000);
            Helper.setFoodAlarm(this, Helper.getCalendarObj(Const.MEAL_2_TIME),1001);
            Helper.setFoodAlarm(this, Helper.getCalendarObj(Const.MEAL_3_TIME),1002);
        }
        else
        {
            Helper.setFoodAlarm(this, Helper.getCalendarObj(Const.MEAL_1_TIME),1000);
            Helper.setFoodAlarm(this, Helper.getCalendarObj(Const.MEAL_2_TIME),1001);
            Helper.setFoodAlarm(this, Helper.getCalendarObj(Const.MEAL_3_TIME),1002);
            Helper.setFoodAlarm(this, Helper.getCalendarObj(Const.MEAL_4_TIME),1003);
        }
    }

    private void calculateAmount() {
        Dog dog = Const.dog;

        int weight = Integer.parseInt(dog.getWeight());

        if(weight <= 2)
        {
            suggestedAmount = calculatePercentage(weight, 10);
            amountTxt.setText("Suggested Amount: " + suggestedAmount + "g");
            mealTime = 4;
        }
        else if(weight <= 4)
        {
            suggestedAmount = calculatePercentage(weight, 7);
            amountTxt.setText("Suggested Amount: " + suggestedAmount + "g");
            mealTime = 4;
        }
        else if(weight <= 8)
        {
            suggestedAmount = calculatePercentage(weight, 5);
            amountTxt.setText("Suggested Amount: " + suggestedAmount + "g");
            mealTime = 3;
        }
        else if(weight <= 10)
        {
            suggestedAmount = calculatePercentage(weight, 3);
            amountTxt.setText("Suggested Amount: " + suggestedAmount + "g");
            mealTime = 2;
        }
        else
        {
            suggestedAmount = calculatePercentage(weight, 2);
            amountTxt.setText("Suggested Amount: " + suggestedAmount + "g");
            mealTime = 2;
        }


        if(mealTime == 2)
        {
            meal1Txt.setText("Meal 1: " + Const.MEAL_1_TIME);
            meal2Txt.setText("Meal 2: " +Const.MEAL_2_TIME);
        }
        else if(mealTime == 3)
        {
            meal1Txt.setText("Meal 1: " + Const.MEAL_1_TIME);
            meal2Txt.setText("Meal 2: " + Const.MEAL_2_TIME);
            meal3Txt.setText("Meal 3: " + Const.MEAL_3_TIME);
        }
        else
        {
            meal1Txt.setText("Meal 1: " + Const.MEAL_1_TIME);
            meal2Txt.setText("Meal 2: " + Const.MEAL_2_TIME);
            meal3Txt.setText("Meal 3: " + Const.MEAL_3_TIME);
            meal4Txt.setText("Meal 4: " + Const.MEAL_4_TIME);
        }
        
        

    }

    private int calculatePercentage(int weight, int percentage) {
        weight = weight * 1000;
        return (weight/100) * percentage;
    }


    private void initViews() {
        amountTxt = findViewById(R.id.suggestedAmountTxt);
        meal1Txt = findViewById(R.id.meal1Txt);
        meal2Txt = findViewById(R.id.meal2Txt);
        meal3Txt = findViewById(R.id.meal3Txt);
        meal4Txt = findViewById(R.id.meal4Txt);
    }
}