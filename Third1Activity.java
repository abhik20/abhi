package com.example.mahe.numberplate1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Third1Activity extends AppCompatActivity {

    //EditText numberplate,name,modelname,complaint;
    TextView textView;
    DatabaseHelper controller;
    Context ctx = this;
    String str,str1,str2,str3;
    private Button btnLogout;
    private Session session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third1);


        textView = (TextView) findViewById(R.id.textView);
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPreferences.edit();

        String name = mPreferences.getString("key"," ");
       // numberplate.setText(name);

        controller = new DatabaseHelper(this);



        str = name;

        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }
        btnLogout = (Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout(){
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(Third1Activity.this,ThanksActivity.class));
    }

    public void btn_click(View view) {

        switch (view.getId()){


            case R.id.list_owner :
                controller.test(controller,textView,str,str1,str2,str3);
                break;



        }
    }
}
