package com.isa.studentmanagerapp;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.isa.DB.StudentDBHandler;
import com.isa.DB.StudentDBOperations;

public class TeacherMainActivity extends AppCompatActivity
{
    private Button viewAllStudentsButton;
    private Button addStudentButton;
    private Button editStudentButton;
    private Button deleteStudentButton;

    private StudentDBOperations stuOps;

    private static final String EXTRA_STUDENT_ID = "com.example.SId";
    private static final String EXTRA_ADD_UPDATE = "com.example.add_update";

    private static final String TAG = "Student Exits";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

        viewAllStudentsButton = (Button) findViewById(R.id.button_view_all_students);
        addStudentButton = (Button) findViewById(R.id.button_student_add);
        editStudentButton = (Button) findViewById(R.id.button_student_update);
        deleteStudentButton = (Button) findViewById(R.id.button_student_delete);

        addStudentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(TeacherMainActivity.this, TeacherAddUpdateStudent.class);
                i.putExtra(EXTRA_ADD_UPDATE, "Add");
                startActivity(i);
            }
        });

        editStudentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getStudentIDAndUpdateStudent();
            }
        });

        deleteStudentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getStudentIDAndDeleteStudent();
            }
        });

        viewAllStudentsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(TeacherMainActivity.this, ViewAllStudentsActivity.class);
                startActivity(i);
            }
        });
    }

    public boolean check_existence(String stu_id)
    {
        SQLiteOpenHelper db = new StudentDBHandler(this);
        SQLiteDatabase database = db.getWritableDatabase();

        String select = "SELECT * FROM students WHERE studentID =" + stu_id;

        Cursor c = database.rawQuery(select, null);

        if (c.moveToFirst())
        {
            Log.d(TAG, "Student Exists");
            return true;
        }

        if (c != null)
        {
            c.close();
        }

        database.close();
        return false;
    }

    public void getStudentIDAndUpdateStudent()
    {
        LayoutInflater li = LayoutInflater.from(this);
        final View getStudentIdView = li.inflate(R.layout.dialog_get_student_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(getStudentIdView);

        final EditText userInput = (EditText) getStudentIdView.findViewById(R.id.editTextDialogUserInput);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                if (userInput.getText().toString().isEmpty())
                {
                    Toast.makeText(TeacherMainActivity.this, "User Input is Invalid", Toast.LENGTH_LONG).show();
                }
                else
                {
                    // get user input and set it to result
                    // edit text
                    if (check_existence(userInput.getText().toString()) == true)
                    {
                        Intent i = new Intent(TeacherMainActivity.this, TeacherAddUpdateStudent.class);
                        i.putExtra(EXTRA_ADD_UPDATE, "Update");
                        i.putExtra(EXTRA_STUDENT_ID, Long.parseLong(userInput.getText().toString()));
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(TeacherMainActivity.this, "Input is invalid", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).create().show();
    }

    public void getStudentIDAndDeleteStudent()
    {
        LayoutInflater li = LayoutInflater.from(this);
        final View getStudentIdView = li.inflate(R.layout.dialog_get_student_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(getStudentIdView);

        final EditText userInput = (EditText) getStudentIdView.findViewById(R.id.editTextDialogUserInput);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                if (userInput.getText().toString().isEmpty())
                {
                    Toast.makeText(TeacherMainActivity.this, "User Input is Invalid", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (check_existence(userInput.getText().toString()) == true)
                    {
                        stuOps.deleteStudent(stuOps.getStudent(Long.parseLong(userInput.getText().toString())));
                        Toast.makeText(TeacherMainActivity.this, "Student has been deleted successfully", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(TeacherMainActivity.this, "Input is invalid", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).create().show();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        stuOps = new StudentDBOperations(TeacherMainActivity.this);
        stuOps.open();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        stuOps.close();
    }
}