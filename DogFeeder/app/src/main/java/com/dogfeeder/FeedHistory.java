package com.dogfeeder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FeedHistory extends AppCompatActivity {

    ListView feedList;
    ProgressDialog dialog;
    ArrayList<String> history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_history);

        initViews();
        getData();

    }

    private void getData() {

        dialog.setMessage("Loading history");
        dialog.setCancelable(false);
        dialog.show();

        history = new ArrayList<>();


        FirebaseDatabase.getInstance().getReference()
                .child("feed_history")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        dialog.dismiss();

                        if(snapshot.exists())
                        {
                            for(DataSnapshot snap: snapshot.getChildren())
                            {
                                history.add(snap.getValue().toString());
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                              FeedHistory.this,
                              android.R.layout.simple_list_item_1,
                              history
                            );

                            feedList.setAdapter(adapter);

                        }else
                        {
                            Toast.makeText(FeedHistory.this, "No data saved yet", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

    }

    private void initViews() {
        feedList = findViewById(R.id.feedHistoryList);
        dialog = new ProgressDialog(this);
    }
}