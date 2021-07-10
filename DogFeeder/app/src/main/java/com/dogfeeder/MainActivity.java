package com.dogfeeder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView lastFed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        getLastFedData();
        setOnClickListeners();
    }

    private void getLastFedData() {

        FirebaseDatabase.getInstance().getReference()
                .child("last_fed")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {

                        if(snapshot.exists())
                        {
                            lastFed.setText("Last Fed: " + snapshot.getValue().toString());
                        }
                        else
                        {
                            lastFed.setText("No Record");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                    }
                });

    }

    private void setOnClickListeners() {

        findViewById(R.id.feedingHistoryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FeedHistory.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.giveFoodBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                String time = new SimpleDateFormat("dd/MM/yyyy  hh:mm", Locale.getDefault())
                        .format(calendar.getTime());

                FirebaseDatabase.getInstance().getReference()
                        .child("last_fed")
                        .setValue(time);

                FirebaseDatabase.getInstance().getReference()
                        .child("feed_history")
                        .push()
                        .setValue(time);


                lastFed.setText("Last Fed: " + time);

                Toast.makeText(MainActivity.this, "Data saved", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initViews() {
        lastFed = findViewById(R.id.lastFedTxt);
    }
}