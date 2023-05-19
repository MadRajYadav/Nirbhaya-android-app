package com.example.nirbhaya;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class signup extends AppCompatActivity {
    Button Register;
    static EditText sName, sEmail,sPassword,sPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Register = findViewById(R.id.register);

        sName = findViewById(R.id.sName);
        sEmail = findViewById(R.id.sEmail);
        sPhone=findViewById(R.id.sPhone);

        sPassword = findViewById(R.id.sPassword);



        sName.addTextChangedListener(signTextWatcher);
        sEmail.addTextChangedListener(signTextWatcher);
        sPassword.addTextChangedListener(signTextWatcher);



        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameinput = sName.getText().toString().trim();
                String useremailinput = sEmail.getText().toString().trim();
                String userphoneinput=sPhone.getText().toString().trim();
                String userpasswordinput = sPassword.getText().toString().trim();

                if (!usernameinput.isEmpty()){
                    if (!useremailinput.isEmpty()){
                        if (!userphoneinput.isEmpty()){
                            if (!userpasswordinput.isEmpty()){


                                Intent intent=new Intent(signup.this,AddNumber.class);

                                intent.putExtra("name",usernameinput);
                                intent.putExtra("email",useremailinput);
                                intent.putExtra("phone",userphoneinput);
                                intent.putExtra("password",userpasswordinput);
                                startActivity(intent);

                            }else {
                                Toast.makeText(signup.this,"Create Password",Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Toast.makeText(signup.this,"Enter Your Mobile Number",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(signup.this,"Enter Email",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(signup.this,"Enter your name",Toast.LENGTH_LONG).show();
                }



            }
        });


    }

    private TextWatcher signTextWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameinput=sName.getText().toString().trim();
            String useremailinput=sEmail.getText().toString().trim();

            String userpasswordinput=sPassword.getText().toString().trim();

            Register.setEnabled(!usernameinput.isEmpty());



        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    static class data{
        String Name=sName.getText().toString().trim();
        String Email=sEmail.getText().toString().trim();
        String Phone=sPhone.getText().toString().trim();
        String Password=sPassword.getText().toString().trim();
    }

}