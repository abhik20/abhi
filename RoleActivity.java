package com.example.mahe.numberplate1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class RoleActivity extends AppCompatActivity {

    Button but1, but2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        but1 = (Button) findViewById(R.id.btn1);
        but2 = (Button) findViewById(R.id.btn2);

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RoleActivity.this, Login1Activity.class));
            }
        });

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RoleActivity.this, LoginActivity.class));
            }
        });


    }
}
