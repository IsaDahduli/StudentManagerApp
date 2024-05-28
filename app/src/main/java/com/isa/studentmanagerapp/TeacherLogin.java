package com.isa.studentmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class TeacherLogin extends AppCompatActivity
{
    public EditText usernameEditTextTeacherLogin;
    public EditText passwordEditTextTeacherLogin;

    public Button loginTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        usernameEditTextTeacherLogin = (EditText)findViewById(R.id.edit_text_username_teacher_login);
        passwordEditTextTeacherLogin = (EditText)findViewById(R.id.edit_text_password_teacher_login);

        loginTeacher = (Button)findViewById(R.id.button_login_teacher);

        loginTeacher.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (usernameEditTextTeacherLogin.getText().toString().equals("Teacher") && passwordEditTextTeacherLogin.getText().toString().equals("Teacher123"))
                {
                    Intent i = new Intent(TeacherLogin.this , TeacherMainActivity.class);
                    startActivity(i);
                }

                else
                {
                    Toast.makeText(TeacherLogin.this, "Incorrect Fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}