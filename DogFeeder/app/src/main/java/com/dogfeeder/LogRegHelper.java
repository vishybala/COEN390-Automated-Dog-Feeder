package com.dogfeeder;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LogRegHandler {

    private String username;
    private String password;

    public String getDogBreed() {
        return dogBreed;
    }

    public void setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
    }

    public Double getDogWeight() {
        return dogWeight;
    }

    public void setDogWeight(Double dogWeight) {
        this.dogWeight = dogWeight;
    }

    public Integer getDogAge() {
        return dogAge;
    }

    public void setDogAge(Integer dogAge) {
        this.dogAge = dogAge;
    }

    private String dogBreed;
    private Double dogWeight;
    private Integer dogAge;

    public LogRegHandler(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LogRegHandler(String username, String password, String dogBreed, Double dogWeight, Integer dogAge) {
        this.username = username;
        this.password = password;
        this.dogBreed = dogBreed;
        this.dogWeight = dogWeight;
        this.dogAge = dogAge;
    }

    public boolean registerUser (Context context, FirebaseAuth authorize){
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            Toast.makeText(context, "Empty Credentials", Toast.LENGTH_SHORT).show();
        }
        else if (password.length() < 6){
            Toast.makeText(context, "Password too short", Toast.LENGTH_SHORT).show();
        }
        else {
            HashMap<String, Object> regMap = new HashMap<>();
            regMap.put("Username", username);
            regMap.put("Dog Breed", dogBreed);
            regMap.put("Dog Weight", dogWeight);
            regMap.put("Dog Age", dogAge);

            return registerHandler(context,regMap);
        }
        return false;
    }

    private boolean registerHandler(Context context,HashMap<String, Object> regMap) {
        String email = username + "@example.com";
        final Boolean[] res = {false};
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("Users").child(username).updateChildren(regMap);
                    Toast.makeText(context, "Registering user successful", Toast.LENGTH_SHORT).show();
                    res[0] = true;
                }
                else {
                    Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return res[0];
    }

    public boolean loginUser (Context context){
        String email = username + "@example.com";
        final Boolean[] res = {false};
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
                res[0] = true;
            }
        });
        return res[0];
    }

    public void logoutUser (Context context){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(context, "Logout Successful", Toast.LENGTH_SHORT).show();
    }

}
