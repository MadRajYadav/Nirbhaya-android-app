package com.example.nirbhaya;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class home_page extends AppCompatActivity {
    Button LogOutBtn;
    int count;
    TextView displayUserName, llinfo;
    Button Add_contect;
    ImageView helpbtn;
    FusedLocationProviderClient fusedlocationproviderclient;
    double latitude;
    double longitude;
    String LatLon;
    String Latitude;
    String Longitude;
    String UserNameDisplay, N1fromDB, N2fromDB, N3fromDB, N4fromDB;
    SmsManager smsManager = SmsManager.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        LogOutBtn = findViewById(R.id.LogOutBtn);
        Add_contect = findViewById(R.id.Add_contect);
        helpbtn = findViewById(R.id.helpbtn);
        llinfo = findViewById(R.id.llinfo);


        ////// Initialize fusedLocationProviderClient   //////
        fusedlocationproviderclient = LocationServices.getFusedLocationProviderClient(home_page.this);


        //////   Retrieve User Name from firebase  ///////

        displayUserName = findViewById(R.id.UserNameDisplay);
        Intent intent = getIntent();
        UserNameDisplay = intent.getStringExtra("name");
        N1fromDB = intent.getStringExtra("n1");
        N2fromDB = intent.getStringExtra("n2");
        N3fromDB = intent.getStringExtra("n3");
        N4fromDB = intent.getStringExtra("n4");

        displayUserName.setText(UserNameDisplay);

///////////////////////////////////////////////////////////////////

        helpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                send();


            }
        });

        LogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("Nirbhaya", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Number", null);
                editor.putBoolean("logIn", false);
                editor.apply();
                Intent intent = new Intent(home_page.this, MainActivity.class);
                Toast.makeText(home_page.this, "Log Out Successfully", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
        Add_contect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, View_Number.class);
                intent.putExtra("username", UserNameDisplay);
                intent.putExtra("n1", N1fromDB);
                intent.putExtra("n2", N2fromDB);
                intent.putExtra("n3", N3fromDB);
                intent.putExtra("n4", N4fromDB);

                startActivity(intent);
            }
        });

    }


    //send msg when user clicks two time on volume down buttom
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            count++;
        }else if(keyCode == KeyEvent.KEYCODE_BACK){
            finishAffinity();
            System.exit(0);
        }
        if (count == 2) {
            count = 0;
            send(); // sending msg with location

        }
        return true;
    }

    public void send() {


        if (ActivityCompat.checkSelfPermission(home_page.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedlocationproviderclient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    //Initialize location
                    Location location = task.getResult();
                    if (location != null) {

                        try {
                            Geocoder geocoder = new Geocoder(home_page.this, Locale.getDefault());
                            List<Address> address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                            //Set Latitude


                            latitude = address.get(0).getLatitude();
                            longitude = address.get(0).getLongitude();
                            Latitude = String.valueOf(latitude);
                            Longitude = String.valueOf(longitude);
                            LatLon = "I am" + " " + UserNameDisplay + ". " + "I am in a trouble,Please help me!,Copy my Longitude and Latitude and search in google map to find my Location." + "   " + Latitude + " " + Longitude;

                            Toast.makeText(home_page.this, "Fetching Location Successfully", Toast.LENGTH_SHORT).show();

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                                    try {


                                        Log.e("mas ", LatLon);
                                        smsManager.sendTextMessage(N1fromDB, null, LatLon, null, null);
                                        Toast.makeText(home_page.this, "Massage is sent to your father", Toast.LENGTH_SHORT).show();


                                        smsManager.sendTextMessage(N2fromDB, null, LatLon, null, null);
                                        Toast.makeText(home_page.this, "2nd Massage is sent to your mother", Toast.LENGTH_SHORT).show();


                                        smsManager.sendTextMessage(N3fromDB, null, LatLon, null, null);
                                        Toast.makeText(home_page.this, "3rd Massage is sent to your brother", Toast.LENGTH_SHORT).show();


                                        smsManager.sendTextMessage(N4fromDB, null, LatLon, null, null);
                                        Toast.makeText(home_page.this, "4th Massage is sent to your friend", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(home_page.this, "Faild to send massage", Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(home_page.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


    }



}