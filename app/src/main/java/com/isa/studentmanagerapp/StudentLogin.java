package com.isa.studentmanagerapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.isa.DB.StudentDBHandler;
import com.isa.Model.Student;

public class StudentLogin extends AppCompatActivity
{
    private static final String TAG = "Student Exits";

    public EditText phoneNumberEditTextStudentLogin;
    public EditText passwordEditTextStudentLogin;

    public Button loginStudentButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        phoneNumberEditTextStudentLogin = (EditText)findViewById(R.id.edit_text_phone_student_login);
        passwordEditTextStudentLogin = (EditText)findViewById(R.id.edit_text_password_student_login);

        loginStudentButton = (Button)findViewById(R.id.button_login_student);

        loginStudentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(phoneNumberEditTextStudentLogin.getText().toString().isEmpty() || passwordEditTextStudentLogin.getText().toString().isEmpty())
                {
                    Toast.makeText(StudentLogin.this,"Field or fields cannot be empty !",Toast.LENGTH_SHORT).show();
                }

                else if(check_login(phoneNumberEditTextStudentLogin.getText().toString(), passwordEditTextStudentLogin.getText().toString()) == true)
                {
                    Intent i = new Intent(StudentLogin.this, StudentMain_Activity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(StudentLogin.this,"Wrong Credentials !",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean check_login(String stu_phonenumber, String stu_pass)
    {
        SQLiteOpenHelper database = new StudentDBHandler(this);
        SQLiteDatabase db = database.getWritableDatabase();

        String select = "SELECT * FROM students WHERE phonenumber = '" + stu_phonenumber + "' AND password= '" + stu_pass+"'";

        Cursor c = db.rawQuery(select,null);

        if(c.moveToFirst())
        {
            Log.d(TAG,"Employee exists");
            return true;
        }

        if(c!=null)
        {
            c.close();
        }
        db.close();
        return false;
    }
}