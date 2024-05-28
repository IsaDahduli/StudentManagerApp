package com.isa.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CourseDBHandler extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "courses.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_COURSES = "courses";
    public static final String COLUMN_CID = "courseID";
    public static final String COLUMN_COURSE_NAME = "coursename";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_COURSES + " (" +
                    COLUMN_CID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_COURSE_NAME + " TEXT " + ") ";

    public CourseDBHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if(newVersion>oldVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
            db.execSQL(TABLE_CREATE);
        }
    }
}
