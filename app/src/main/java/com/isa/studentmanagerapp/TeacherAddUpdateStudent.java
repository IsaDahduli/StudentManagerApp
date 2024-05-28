package com.isa.studentmanagerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.isa.DB.StudentDBOperations;
import com.isa.Model.Student;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TeacherAddUpdateStudent extends AppCompatActivity
{
    private static final String EXTRA_STUDENT_ID = "com.example.SId";
    private static final String EXTRA_ADD_UPDATE = "com.example.add_update";

    private RadioGroup radioGroup;
    private RadioButton trueRadioButton, falseRadioButton;

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText phoneNumberEditText;
    private EditText passwordEditText;
    private EditText enrolmentDateEditText;

    private EditText studiesEditText;

    private ImageView calendarImageView;

    private Button addUpdateButton;

    private Student newStuInfo;
    private Student oldStuInfo;

    private String mode;

    private long studentID;

    private StudentDBOperations studentData;

    final Calendar enrolmentCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_add_update_student);

        newStuInfo = new Student();
        oldStuInfo = new Student();

        firstNameEditText = (EditText) findViewById(R.id.edit_text_first_name);
        lastNameEditText = (EditText) findViewById(R.id.edit_text_last_name);
        phoneNumberEditText = (EditText)findViewById(R.id.edit_text_phone_number);
        passwordEditText = (EditText) findViewById(R.id.edit_text_student_password);

        enrolmentDateEditText = (EditText) findViewById(R.id.edit_text_enrolment_date);
        studiesEditText = (EditText)findViewById(R.id.edit_text_study);

        radioGroup = (RadioGroup) findViewById(R.id.radio_attending);

        calendarImageView = (ImageView) findViewById(R.id.image_view_date);

        trueRadioButton = (RadioButton) findViewById(R.id.radio_true);
        falseRadioButton = (RadioButton) findViewById(R.id.radio_false);

        addUpdateButton = (Button) findViewById(R.id.button_add_update_student);

        ArrayAdapter<CharSequence> adapterStudies = ArrayAdapter.createFromResource(this, R.array.StudyFields, R.layout.spinner_item);
        adapterStudies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner studyTitles = (Spinner) findViewById(R.id.spinner_studies);
        studyTitles.setAdapter(adapterStudies);

        studyTitles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String studyTitlesText = parent.getItemAtPosition(position).toString();
                studiesEditText.setText(studyTitlesText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        studentData = new StudentDBOperations(this);
        studentData.open();
        newStuInfo.setAttendance("True");

        mode = getIntent().getStringExtra(EXTRA_ADD_UPDATE);

        if (mode.equals("Update"))
        {
            addUpdateButton.setText("Update Student");
            studentID = getIntent().getLongExtra(EXTRA_STUDENT_ID, 0);

            initializeStudent(studentID);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == R.id.radio_true)
                {
                    newStuInfo.setAttendance("True");
                    if (mode.equals("Update"))
                    {
                        oldStuInfo.setAttendance("True");
                    }
                } else if (checkedId == R.id.radio_false)
                {
                    newStuInfo.setAttendance("False");
                    if (mode.equals("Update"))
                    {
                        oldStuInfo.setAttendance("False");
                    }
                }
            }
        });

        addUpdateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mode.equals("Add"))
                {
                    newStuInfo.setFirstname(firstNameEditText.getText().toString());
                    newStuInfo.setLastname(lastNameEditText.getText().toString());
                    newStuInfo.setPhonenumber(phoneNumberEditText.getText().toString());
                    newStuInfo.setPassword(passwordEditText.getText().toString());
                    newStuInfo.setEnrolmentDate(enrolmentDateEditText.getText().toString());
                    newStuInfo.setStudies(studiesEditText.getText().toString());

                    if (firstNameEditText.getText().toString().isEmpty() || lastNameEditText.getText().toString().isEmpty() || phoneNumberEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty() || enrolmentDateEditText.getText().toString().isEmpty() || studiesEditText.getText().toString().isEmpty())
                    {
                        Toast.makeText(TeacherAddUpdateStudent.this, "Field or Fields cannot be empty !!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        studentData.addStudent(newStuInfo);
                        Toast.makeText(TeacherAddUpdateStudent.this, "Student " + newStuInfo.getFirstname() + " has been added successfully !", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(TeacherAddUpdateStudent.this, TeacherMainActivity.class);
                        startActivity(i);
                    }
                }
                else
                {
                    oldStuInfo.setFirstname(firstNameEditText.getText().toString());
                    oldStuInfo.setLastname(lastNameEditText.getText().toString());
                    oldStuInfo.setPhonenumber(phoneNumberEditText.getText().toString());
                    oldStuInfo.setPassword(passwordEditText.getText().toString());
                    oldStuInfo.setEnrolmentDate(enrolmentDateEditText.getText().toString());
                    oldStuInfo.setStudies(studiesEditText.getText().toString());

                    if (firstNameEditText.getText().toString().isEmpty() || lastNameEditText.getText().toString().isEmpty() || phoneNumberEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty() || enrolmentDateEditText.getText().toString().isEmpty() || studiesEditText.getText().toString().isEmpty())
                    {
                        Toast.makeText(TeacherAddUpdateStudent.this, "Field or Fields cannot be empty !!", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        studentData.updateStudent(oldStuInfo);
                        Toast.makeText(TeacherAddUpdateStudent.this, "Student " + oldStuInfo.getFirstname() + " has been updated successfully !!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(TeacherAddUpdateStudent.this, TeacherMainActivity.class);
                        startActivity(i);
                    }
                }
            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()
        {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                // TODO Auto-generated method stub
                enrolmentCalendar.set(Calendar.YEAR, year);
                enrolmentCalendar.set(Calendar.MONTH, monthOfYear);
                enrolmentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        calendarImageView.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new DatePickerDialog(TeacherAddUpdateStudent.this, date, enrolmentCalendar.get(Calendar.YEAR), enrolmentCalendar.get(Calendar.MONTH), enrolmentCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel()
    {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        enrolmentDateEditText.setText(sdf.format(enrolmentCalendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_return, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.action_cancel:
                actionCancel();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        actionCancel();
    }

    private void actionCancel()
    {
        finish();
    }

    private void initializeStudent(long studentID)
    {
        oldStuInfo = studentData.getStudent(studentID);

        firstNameEditText.setText(oldStuInfo.getFirstname());
        lastNameEditText.setText(oldStuInfo.getLastname());
        phoneNumberEditText.setText(oldStuInfo.getPhonenumber());
        passwordEditText.setText(oldStuInfo.getPassword());
        enrolmentDateEditText.setText(oldStuInfo.getEnrolmentDate());

        radioGroup.check(oldStuInfo.getAttendance().equals("True") ? R.id.radio_true: R.id.radio_false);

        studiesEditText.setText(oldStuInfo.getStudies());

    }
}