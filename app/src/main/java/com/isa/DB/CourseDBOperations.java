package com.isa.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.isa.Model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseDBOperations
{
    public static final String LOGTAG = "COURSE_ADD_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns =
            {
                    CourseDBHandler.COLUMN_CID,
                    CourseDBHandler.COLUMN_COURSE_NAME
            };

    public CourseDBOperations(Context context)
    {
        dbhandler = new CourseDBHandler(context);
    }

    public void open()
    {
        Log.i(LOGTAG, "Database Opened");
        database = dbhandler.getWritableDatabase();
    }

    public void close()
    {
        Log.i(LOGTAG,"Database Closed");
        dbhandler.close();
    }

    public Course addCourse(Course Course)
    {
        ContentValues values = new ContentValues();
        values.put(CourseDBHandler.COLUMN_COURSE_NAME,Course.getCourseName());
        long insertCID = database.insert(CourseDBHandler.TABLE_COURSES, null, values);
        Course.setCourseID(insertCID);
        return Course;
    }

    public Course getCourse(long id)
    {
        Cursor cursor = database.query(CourseDBHandler.TABLE_COURSES,allColumns,CourseDBHandler.COLUMN_CID + "=?", new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor!= null && cursor.moveToFirst())
            cursor.moveToFirst();

        Course e = new Course(Long.parseLong(cursor.getString(0)),cursor.getString(1));
        return e;
    }

    public List<Course> getAllCourses()
    {
        Cursor cursor = database.query(CourseDBHandler.TABLE_COURSES,allColumns,null,null,null,null,null);

        List<Course> courses = new ArrayList<>();

        if(cursor.getCount() > 0 && cursor !=null)
        {
            while (cursor.moveToNext())
            {
                Course course = new Course();
                course.setCourseID(cursor.getLong(cursor.getColumnIndex(CourseDBHandler.COLUMN_CID)));
                course.setCourseName(cursor.getString(cursor.getColumnIndex(CourseDBHandler.COLUMN_COURSE_NAME)));

                courses.add(course);
            }
        }

        return courses;
    }

    public void removeCourse(Course course)
    {
        database.delete(CourseDBHandler.TABLE_COURSES,CourseDBHandler.COLUMN_CID + "=" + course.getCourseID(), null);
    }
}
