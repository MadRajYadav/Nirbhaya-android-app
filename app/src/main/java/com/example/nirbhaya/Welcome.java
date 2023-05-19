package com.example.nirbhaya;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Welcome extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);



        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("datauser");

        SharedPreferences sharedPreferences = getSharedPreferences("Nirbhaya",MODE_PRIVATE);
        String preNumber = sharedPreferences.getString("Number",null);
        Boolean preLogin = sharedPreferences.getBoolean("logIn",false);
        if(preLogin){
            Query check_name=databaseReference.orderByChild("phone").equalTo(preNumber);
            check_name.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()){

                        String namefromDB=snapshot.child(preNumber).child("name").getValue(String.class);
                        String n1fromDB=snapshot.child(preNumber).child("n1").getValue(String.class);
                        String n2fromDB=snapshot.child(preNumber).child("n2").getValue(String.class);
                        String n3fromDB=snapshot.child(preNumber).child("n3").getValue(String.class);
                        String n4fromDB=snapshot.child(preNumber).child("n4").getValue(String.class);

                        Intent intent= new Intent(Welcome.this,home_page.class);
                        intent.putExtra("name",namefromDB);
                        intent.putExtra("n1",n1fromDB);
                        intent.putExtra("n2",n2fromDB);
                        intent.putExtra("n3",n3fromDB);
                        intent.putExtra("n4",n4fromDB);
                        startActivity(intent);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent= new Intent(Welcome.this,MainActivity.class);
                    startActivity(intent);
                }
            },3000);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_POWER){
            event.startTracking();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_POWER){

            return true;
        }
        return super.onKeyLongPress(keyCode, event);

    }





}