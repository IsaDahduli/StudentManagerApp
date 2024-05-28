package com.isa.studentmanagerapp;

import android.app.DatePickerDialog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;

import com.isa.DB.CourseDBOperations;
import com.isa.Model.Course;

public class StudentAddCourseActivity extends AppCompatActivity
{
    private EditText courseEditText;
    private Button addCourseButton;

    private Course newCourseAdd;

    private CourseDBOperations courseData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add_course);

        newCourseAdd = new Course();

        courseData = new CourseDBOperations(this);
        courseData.open();

        courseEditText = (EditText)findViewById(R.id.edit_text_course);
        addCourseButton = (Button)findViewById(R.id.button_add_course);

        ArrayAdapter<CharSequence>adapterCourses = ArrayAdapter.createFromResource(this,R.array.CoursesIT,R.layout.spinner_item);
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner course = (Spinner)findViewById(R.id.spinner_courses);
        course.setAdapter(adapterCourses);
        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String courseText = parent.getItemAtPosition(position).toString();
                courseEditText.setText(courseText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        addCourseButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                newCourseAdd.setCourseName(courseEditText.getText().toString());

                if(courseEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(StudentAddCourseActivity.this,"Field can't be empty !",Toast.LENGTH_LONG).show();
                }
                else
                {
                    courseData.addCourse(newCourseAdd);
                    Toast.makeText(StudentAddCourseActivity.this,"Course has been added successfully !",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(StudentAddCourseActivity.this, StudentMain_Activity.class);
                    startActivity(i);
                }
            }
        });
    }
}
