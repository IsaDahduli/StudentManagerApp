package com.isa.studentmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainMenuActivity extends AppCompatActivity
{
    private Button TeacherButton;
    private Button StudentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        TeacherButton = (Button)findViewById(R.id.button_login_teacher);
        StudentButton = (Button)findViewById(R.id.button_login_student);
    }

    public void goToTeacherLogin(View view)
    {
        TeacherButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MainMenuActivity.this, TeacherLogin.class);
                startActivity(i);
            }
        });
    }

    public void goToStudentLogin(View view)
    {
        StudentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MainMenuActivity.this, StudentLogin.class);
                startActivity(i);
            }
        });
    }
}