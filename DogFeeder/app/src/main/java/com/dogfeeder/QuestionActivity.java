package com.dogfeeder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class QuestionActivity extends AppCompatActivity {

    EditText typeEdit, weightEdit, ageEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        initViews();
        setOnClickListeners();

    }

    private void setOnClickListeners() {
        findViewById(R.id.questionNextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = typeEdit.getText().toString();
                String weight = weightEdit.getText().toString();
                String age = ageEdit.getText().toString();

                if(type.isEmpty())
                {
                    typeEdit.setError("Field is required");
                    typeEdit.requestFocus();
                    return;
                }

                if(weight.isEmpty())
                {
                    weightEdit.setError("Field is required");
                    weightEdit.requestFocus();
                    return;
                }

                if(age.isEmpty())
                {
                    ageEdit.setError("Field is required");
                    ageEdit.requestFocus();
                    return;
                }

                Map<String, Object> map = new HashMap<>();

                map.put("age", age);
                map.put("weight", weight);
                map.put("type", type);

                FirebaseDatabase.getInstance().getReference()
                        .child("dog_specs")
                        .setValue(map);

                Toast.makeText(QuestionActivity.this, "Data saved in database", Toast.LENGTH_SHORT).show();

                Const.dog = new Dog(type, age, weight);

                Intent intent = new Intent(QuestionActivity.this, SuggestionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        typeEdit = findViewById(R.id.dogTypeEdit);
        ageEdit = findViewById(R.id.dogAgeEdit);
        weightEdit = findViewById(R.id.dogWeightEdit);
    }
}