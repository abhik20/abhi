package com.example.mahe.numberplate1;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, "TEST.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE STUDENTS(NUMBERPLATE TEXT PRIMARY KEY, NAME TEXT, MODELNAME TEXT, COMPLAINT TEXT); ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS STUDENTS;");
        onCreate(sqLiteDatabase);
    }

    public void insert_student(String numberplate, String name, String modelname, String complaint ){

        ContentValues contentValues = new ContentValues();
        contentValues.put("NUMBERPLATE",numberplate);
        contentValues.put("NAME",name);
        contentValues.put("MODELNAME",modelname);
        contentValues.put("COMPLAINT",complaint);
        this.getWritableDatabase().insertOrThrow("STUDENTS","",contentValues);

    }

    public void delete_student (String numberplate){

        this.getWritableDatabase().delete("STUDENTS","NUMBERPLATE='"+numberplate+"'",null );

    }


    public void update_student(String old_complaint, String new_complaint){

        this.getWritableDatabase().execSQL("UPDATE STUDENTS SET COMPLAINT='"+new_complaint+"' WHERE COMPLAINT='"+old_complaint+"'");

    }

    /*public void list_all_students(DatabaseHelper dop,TextView textview){



        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM STUDENTS", null);
        textview.setText("");
        while (cursor.moveToNext()){
            textview.append(cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(2)+"\n");


        }
    }*/
    public void test(DatabaseHelper dop,TextView textView,String str, String str1, String str2, String str3){



        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM STUDENTS", null);

        while (cursor.moveToNext()){
                  if (str.equals(cursor.getString(0))){
                      str1=cursor.getString(1);
                      str2 = cursor.getString(2);
                      str3 = cursor.getString(3);
                      textView.setText(str+"\n"+str1+"\n"+str2+"\n"+str3);
                  }


        }
    }

}

