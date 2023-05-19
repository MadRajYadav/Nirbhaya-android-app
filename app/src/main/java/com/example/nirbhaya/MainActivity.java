package com.example.nirbhaya;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import java.util.Timer;
import java.util.TimerTask;

//http://localhost:55510/
public class MainActivity extends AppCompatActivity {
    ActivityResultLauncher<String[]> mPermissionResultLauncher;
    private Boolean isSmsPermissionGranted = false;
    private Boolean isLocationPermissionGranted = false;
    Button loginbtn;
    Button signbtn;
    EditText lpassword;
    EditText loginid;
    LinearLayout lProgressBar;
    ProgressBar progressBar;
    Timer time;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPermissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {

                if(result.get(android.Manifest.permission.SEND_SMS)!=null){
                    isSmsPermissionGranted = result.get(android.Manifest.permission.SEND_SMS);
                }
                if(result.get(android.Manifest.permission.ACCESS_FINE_LOCATION)!=null){
                    isSmsPermissionGranted = result.get(Manifest.permission.ACCESS_FINE_LOCATION);
                }
            }
        });

        requestPermission();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("datauser");


        loginbtn = findViewById(R.id.logbtn);
        signbtn = findViewById(R.id.gotosignbtn);
        lpassword = findViewById(R.id.lpassword);
        loginid = findViewById(R.id.loginid);
        progressBar=findViewById(R.id.progress_bar);
        lProgressBar = findViewById(R.id.l_progress);

        loginid.addTextChangedListener(loginTxtwatcher);
        lpassword.addTextChangedListener(loginTxtwatcher);


        //////////  After clicking Login button  ////////

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lProgressBar.setVisibility(View.VISIBLE);
                time = new Timer();
               count = 0;
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                       count++;
                       progressBar.setProgress(count);
                       if(count == 10){
                           time.cancel();
                       }

                    }
                };
                time.schedule(timerTask,0,20);
                String userid_data = loginid.getText().toString().trim();
                String password_data = lpassword.getText().toString().trim();
                Query check_name=databaseReference.orderByChild("phone").equalTo(userid_data);
                check_name.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()){

                            String passwordcheck=snapshot.child(userid_data).child("password").getValue(String.class);
                            if (passwordcheck.equals(password_data)){

                                String namefromDB=snapshot.child(userid_data).child("name").getValue(String.class);
                                String n1fromDB=snapshot.child(userid_data).child("n1").getValue(String.class);
                                String n2fromDB=snapshot.child(userid_data).child("n2").getValue(String.class);
                                String n3fromDB=snapshot.child(userid_data).child("n3").getValue(String.class);
                                String n4fromDB=snapshot.child(userid_data).child("n4").getValue(String.class);

                                SharedPreferences sharedPreferences = getSharedPreferences("Nirbhaya",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Number",userid_data);
                                editor.putBoolean("logIn",true);
                                editor.apply();
                                Intent intent= new Intent(MainActivity.this,home_page.class);
                                intent.putExtra("name",namefromDB);
                                intent.putExtra("n1",n1fromDB);
                                intent.putExtra("n2",n2fromDB);
                                intent.putExtra("n3",n3fromDB);
                                intent.putExtra("n4",n4fromDB);
                                startActivity(intent);
                                lProgressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "login successfully", Toast.LENGTH_LONG).show();

                            }else{
                                lProgressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "Worng Password", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            lProgressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this,"User does not exists",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


        ////// After clicking SIGN UP Button //////

        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, signup.class);
                startActivity(intent);
            }
        });

    }

    // TextWatcher <Button Enable and Disable>

    private TextWatcher loginTxtwatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameInput=loginid.getText().toString().trim();
            String passwordInput=lpassword.getText().toString().trim();

            loginbtn.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }




    private  void requestPermission(){

        // checking the required permission are given or not and storing its result in variable.
        isSmsPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_DENIED;
        isLocationPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED;

        // making arrayList
        List<String> permissionRequest = new ArrayList<String>();


        // checking permission granted or not
        if(!isLocationPermissionGranted){
            permissionRequest.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(!isSmsPermissionGranted){
            permissionRequest.add(Manifest.permission.SEND_SMS);
        }


        if(!permissionRequest.isEmpty()){
            mPermissionResultLauncher.launch(permissionRequest.toArray(new String[0]));
        }

    }

}