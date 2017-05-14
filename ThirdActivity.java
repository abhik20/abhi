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


public class ThirdActivity extends AppCompatActivity {

    EditText numberplate,name,modelname,complaint;
    TextView textView;
    DatabaseHelper controller;
    Context ctx = this;
    String str,str1,str2,str3;
    private Button btnLogout;
    private Session session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        numberplate = (EditText) findViewById(R.id.numberplate_input);
        name = (EditText) findViewById(R.id.name_input);
        modelname = (EditText) findViewById(R.id.modelname_input);
        complaint = (EditText) findViewById(R.id.complaint_input);
        textView = (TextView) findViewById(R.id.textView);
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPreferences.edit();

        String name = mPreferences.getString("key"," ");
        numberplate.setText(name);

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
        startActivity(new Intent(ThirdActivity.this,ThanksActivity.class));
    }

    public void btn_click(View view) {

        switch (view.getId()){

            case R.id.button_add :
                try{
                    controller.insert_student(numberplate.getText().toString(),name.getText().toString(),modelname.getText().toString(),complaint.getText().toString());
                }catch (SQLException e){
                    Toast.makeText(ThirdActivity.this,"ALREADY EXISTS",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_delete :
                controller.delete_student(numberplate.getText().toString());
                break;
            case R.id.btn_update :
                AlertDialog.Builder dialog = new AlertDialog.Builder(ThirdActivity.this);
                dialog.setTitle("ENTER NEW COMPLAINT");

                final EditText new_complaint = new EditText(this);
                dialog.setView(new_complaint);

                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        controller.update_student(complaint.getText().toString(),new_complaint.getText().toString());

                    }
                });

                dialog.show();
                break;
            case R.id.list_owner :
                controller.test(controller,textView,str,str1,str2,str3);
                break;



        }
    }
}
