package com.isa.studentmanagerapp;

import android.os.Bundle;
import android.app.ListActivity;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import com.isa.DB.CourseDBOperations;
import com.isa.Model.Course;

import java.util.List;

public class ViewAllCoursesActivity extends ListActivity
{

    private CourseDBOperations courseDBOps;
    List<Course> courses;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_courses);

        courseDBOps = new CourseDBOperations(this);
        courseDBOps.open();
        courses = courseDBOps.getAllCourses();
        courseDBOps.close();

        ArrayAdapter<Course>adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,courses);
        setListAdapter(adapter);
    }
}