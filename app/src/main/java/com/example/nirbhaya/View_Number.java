package com.example.nirbhaya;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class View_Number extends AppCompatActivity {
    TextView ViewNumber1,ViewNumber2,ViewNumber3,ViewNumber4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_number);

        ViewNumber1=findViewById(R.id.ViewNumber1);
        ViewNumber2=findViewById(R.id.ViewNumber2);
        ViewNumber3=findViewById(R.id.ViewNumber3);
        ViewNumber4=findViewById(R.id.ViewNumber4);

        Intent intent=getIntent();
        String N1fromDB=intent.getStringExtra("n1");
        String N2fromDB=intent.getStringExtra("n2");
        String N3fromDB=intent.getStringExtra("n3");
        String N4fromDB=intent.getStringExtra("n4");

        ViewNumber1.setText(N1fromDB);
        ViewNumber2.setText(N2fromDB);
        ViewNumber3.setText(N3fromDB);
        ViewNumber4.setText(N4fromDB);



    }
}