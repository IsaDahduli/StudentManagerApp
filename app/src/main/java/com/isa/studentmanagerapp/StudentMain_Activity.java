package com.isa.studentmanagerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.isa.DB.CourseDBHandler;
import com.isa.DB.CourseDBOperations;


public class StudentMain_Activity extends AppCompatActivity 
{

    private Button viewAllCoursesButton;
    private Button goToAddCourseButton;
    private Button deleteCourseButton;

    private CourseDBOperations courseDBOps;

    private static final String TAG = "Course Exits";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        viewAllCoursesButton = (Button)findViewById(R.id.button_view_all_courses);
        goToAddCourseButton = (Button)findViewById(R.id.button_go_to_course_add);
        deleteCourseButton = (Button)findViewById(R.id.button_course_delete);

        deleteCourseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getCourseIDAndDeleteCourse();
            }
        });
    }


    public void goToAddCourse(View view)
    {
        goToAddCourseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(StudentMain_Activity.this, StudentAddCourseActivity.class);
                startActivity(i);
            }
        });
    }

    public void goToViewAllCourses(View view)
    {
        viewAllCoursesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(StudentMain_Activity.this, ViewAllCoursesActivity.class);
                startActivity(i);
            }
        });
    }

    public boolean check_course_existence(String course_id)
    {
        SQLiteOpenHelper db = new CourseDBHandler(this);
        SQLiteDatabase database = db.getWritableDatabase();

        String select = "SELECT * FROM courses WHERE courseID = " + course_id;

        Cursor c = database.rawQuery(select, null);

        if (c.moveToFirst())
        {
            Log.d(TAG, "Course Exists");
            return true;
        }

        if (c != null)
        {
            c.close();
        }

        database.close();
        return false;
    }

    public void getCourseIDAndDeleteCourse()
    {
        LayoutInflater li = LayoutInflater.from(this);
        final View getCourseIDView = li.inflate(R.layout.dialog_get_course_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(getCourseIDView);

        final EditText studentInput = (EditText)getCourseIDView.findViewById(R.id.editTextDialogStudentCourseIDInput);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                if (studentInput.getText().toString().isEmpty())
                {
                    Toast.makeText(StudentMain_Activity.this,"User Input is Invalid", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(check_course_existence(studentInput.getText().toString()) == true)
                    {
                        courseDBOps.removeCourse(courseDBOps.getCourse(Long.parseLong(studentInput.getText().toString())));
                        Toast.makeText(StudentMain_Activity.this, "Course has been deleted successfully", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(StudentMain_Activity.this, "Input is invalid", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).create().show();
    }

    @Override
    protected  void onResume()
    {
        super.onResume();
        courseDBOps = new CourseDBOperations(StudentMain_Activity.this);
        courseDBOps.open();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        courseDBOps.close();
    }
}