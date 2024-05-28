package com.isa.Model;

public class Course
{
    public long courseID;

    public String courseName;

    public Course(long courseID, String courseName)
    {
        this.courseID = courseID;
        this.courseName = courseName;
    }

    public Course()
    {

    }

    public long getCourseID()
    {
        return courseID;
    }

    public void setCourseID(long courseID)
    {
        this.courseID = courseID;
    }

    public String getCourseName()
    {
        return courseName;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    public String toString()
    {
        return "Course ID: " + getCourseID() + "\n" +
                "Course Name: " + getCourseName();
    }
}
