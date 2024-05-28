package com.isa.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.isa.Model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDBOperations
{
    public static final String LOGTAG = "STD_MNGMNT_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns =
            {
                    StudentDBHandler.COLUMN_SID,
                    StudentDBHandler.COLUMN_FIRST_NAME,
                    StudentDBHandler.COLUMN_LAST_NAME,
                    StudentDBHandler.COLUMN_PHONE_NUMBER,
                    StudentDBHandler.COLUMN_PASSWORD,
                    StudentDBHandler.COLUMN_ATTENDANCE,
                    StudentDBHandler.COLUMN_ENROLMENT_DATE,
                    StudentDBHandler.COLUMN_STUDY_FIELD
            };

    public StudentDBOperations(Context context)
    {
        dbhandler = new StudentDBHandler(context);
    }

    public void open()
    {
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();
    }
    public void close()
    {
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    public Student addStudent(Student Student)
    {
        ContentValues values = new ContentValues();
        values.put(StudentDBHandler.COLUMN_FIRST_NAME, Student.getFirstname());
        values.put(StudentDBHandler.COLUMN_LAST_NAME, Student.getLastname());
        values.put(StudentDBHandler.COLUMN_PHONE_NUMBER, Student.getPhonenumber());
        values.put(StudentDBHandler.COLUMN_PASSWORD, Student.getPassword());
        values.put(StudentDBHandler.COLUMN_ATTENDANCE, Student.getAttendance());
        values.put(StudentDBHandler.COLUMN_ENROLMENT_DATE,Student.getEnrolmentDate());
        values.put(StudentDBHandler.COLUMN_STUDY_FIELD, Student.getStudies());
        long insertSID = database.insert(StudentDBHandler.TABLE_STUDENTS, null, values);
        Student.setStudentID(insertSID);
        return Student;
    }

    public Student getStudent(long id)
    {
        Cursor cursor = database.query(StudentDBHandler.TABLE_STUDENTS,allColumns,StudentDBHandler.COLUMN_SID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null && cursor.moveToFirst())
            cursor.moveToFirst();

        Student e = new Student(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
        // return Student
        return e;
    }

    public List<Student> getAllStudents()
    {
        Cursor cursor = database.query(StudentDBHandler.TABLE_STUDENTS,allColumns,null,null,null, null, null);

        List<Student> students = new ArrayList<>();
        if(cursor.getCount() > 0 && cursor != null)
        {
            while(cursor.moveToNext())
            {
                Student student = new Student();
                student.setStudentID(cursor.getLong(cursor.getColumnIndex(StudentDBHandler.COLUMN_SID)));
                student.setFirstname(cursor.getString(cursor.getColumnIndex(StudentDBHandler.COLUMN_FIRST_NAME)));
                student.setLastname(cursor.getString(cursor.getColumnIndex(StudentDBHandler.COLUMN_LAST_NAME)));
                student.setPhonenumber(cursor.getString(cursor.getColumnIndex(StudentDBHandler.COLUMN_PHONE_NUMBER)));
                student.setPassword(cursor.getString(cursor.getColumnIndex(StudentDBHandler.COLUMN_PASSWORD)));
                student.setAttendance(cursor.getString(cursor.getColumnIndex(StudentDBHandler.COLUMN_ATTENDANCE)));
                student.setEnrolmentDate(cursor.getString(cursor.getColumnIndex(StudentDBHandler.COLUMN_ENROLMENT_DATE)));
                student.setStudies(cursor.getString(cursor.getColumnIndex(StudentDBHandler.COLUMN_STUDY_FIELD)));
                students.add(student);
            }
        }
        // return All Students
        return students;
    }

    // Updating Student
    public int updateStudent(Student student)
    {
        ContentValues values = new ContentValues();
        values.put(StudentDBHandler.COLUMN_FIRST_NAME, student.getFirstname());
        values.put(StudentDBHandler.COLUMN_LAST_NAME, student.getLastname());
        values.put(StudentDBHandler.COLUMN_PHONE_NUMBER,student.getPhonenumber());
        values.put(StudentDBHandler.COLUMN_PASSWORD, student.getPassword());
        values.put(StudentDBHandler.COLUMN_ATTENDANCE,student.getAttendance());
        values.put(StudentDBHandler.COLUMN_ENROLMENT_DATE,student.getEnrolmentDate());
        values.put(StudentDBHandler.COLUMN_ATTENDANCE, student.getAttendance());
        values.put(StudentDBHandler.COLUMN_STUDY_FIELD, student.getStudies());

        // updating row
        return database.update(StudentDBHandler.TABLE_STUDENTS, values,
                StudentDBHandler.COLUMN_SID + "=?",new String[] { String.valueOf(student.getStudentID())});
    }

    // Deleting Student
    public void deleteStudent(Student student)
    {
        database.delete(StudentDBHandler.TABLE_STUDENTS, StudentDBHandler.COLUMN_SID + "=" + student.getStudentID(), null);
    }
}
