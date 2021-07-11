package com.dogfeeder;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatabaseHelper {

    public ArrayList<String> getFeedHistory(String username){
        final ArrayList<String> mealList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Meal History").child(username);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mealList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    LastFedContainer info = snapshot.getValue(LastFedContainer.class);
                    mealList.add(info.getLastFed());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return mealList;
    }

    public boolean uploadFeedTime(String username, String datetime){
        FirebaseDatabase.getInstance.getReference.child("Meal History").child(username).push().setValueAsync(datetime);
    }
}
