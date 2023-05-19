package com.example.nirbhaya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNumber extends AppCompatActivity {
    EditText InputNumber1,InputNumber2,InputNumber3,InputNumber4;
    Button Add;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_number);

        InputNumber1=findViewById(R.id.InputNumber1);
        InputNumber2=findViewById(R.id.InputNumber2);
        InputNumber3=findViewById(R.id.InputNumber3);
        InputNumber4=findViewById(R.id.InputNumber4);
        Add=findViewById(R.id.AddNumber);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String InputNumber01=InputNumber1.getText().toString().trim();
                String InputNumber02=InputNumber2.getText().toString().trim();
                String InputNumber03=InputNumber3.getText().toString().trim();
                String InputNumber04=InputNumber4.getText().toString().trim();

                if (!InputNumber01.isEmpty()){
                    if (!InputNumber02.isEmpty()){
                        if (!InputNumber03.isEmpty()){
                            if (!InputNumber04.isEmpty()){





                                Intent intent=new Intent(AddNumber.this,MainActivity.class);
                                InsertData();
                                startActivity(intent);
                                Toast.makeText(AddNumber.this,"Register Successfully",Toast.LENGTH_LONG).show();




                            }else {
                                Toast.makeText(AddNumber.this,"Enter Your Friend's Number",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(AddNumber.this,"Enter Your Brother's Number",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(AddNumber.this,"Enter Your Mother's Number",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(AddNumber.this,"Enter Your Father's Number",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void InsertData() {
        firebaseDatabase =FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("datauser");

        String InputNumber01_s=InputNumber1.getText().toString().trim();
        String InputNumber02_s=InputNumber2.getText().toString().trim();
        String InputNumber03_s=InputNumber3.getText().toString().trim();
        String InputNumber04_s=InputNumber4.getText().toString().trim();

        signup.data data=new signup.data();
        Storingdata Users=new Storingdata(data.Name, data.Email, data.Phone, data.Password, InputNumber01_s,InputNumber02_s,InputNumber03_s,InputNumber04_s);
        reference.child(data.Phone).setValue(Users);

    }

}