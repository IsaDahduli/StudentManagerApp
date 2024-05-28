package com.isa.Model;

public class Student
{
    public long studentID;

    public String firstname;
    public String lastname;
    public String phonenumber;
    public String password;
    public String attendance;
    public String enrolmentdate;
    public String studyfield;

    public Student(long studentID,String firstname, String lastname,String phonenumber, String password, String attendance, String enrolmentdate, String studyfield)
    {
        this.studentID = studentID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.password = password;
        this.attendance = attendance;
        this.enrolmentdate = enrolmentdate;
        this.studyfield = studyfield;
    }

    public Student()
    {

    }

    public long getStudentID()
    {
        return studentID;
    }

    public void setStudentID(long studentID)
    {
        this.studentID = studentID;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getAttendance()
    {
        return attendance;
    }

    public void setAttendance(String attendance)
    {
        this.attendance = attendance;
    }

    public String getEnrolmentDate()
    {
        return enrolmentdate;
    }

    public void setEnrolmentDate(String enrolmentdate)
    {
        this.enrolmentdate = enrolmentdate;
    }

    public String getStudies()
    {
        return studyfield;
    }

    public void setStudies(String studyfield)
    {
        this.studyfield = studyfield;
    }

    public String toString()
    {
        return  "Student ID: " + getStudentID() + "\n" +
                "Name: " + getFirstname() + " " + getLastname() + "\n" +
                "Phone Number: " + getPhonenumber() + "\n" +
                "Password: " + getPassword() + "\n" +
                "Attendance: " + getAttendance() + "\n" +
                "Enrolment Date: " + getEnrolmentDate() + "\n" +
                "Study Field: " + getStudies();
    }

}
