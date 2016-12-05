package com.example.wal.chooza_12;

/**
 * Created by wal on 9/17/2016.
 */
public class Student {
    private String Name;
    private String Username;
    private String Password;
    private String Email_ID;
    private String Gender;
    private String DOB;
    private String City;
    private String Phone;

    public Student(String name, String username, String password, String email_ID, String gender, String DOB, String city, String phone) {
        Name = name;
        Username = username;
        Password = password;
        Email_ID = email_ID;
        Gender = gender;
        this.DOB = DOB;
        City = city;
        Phone = phone;
    }
public Student(){}

    public Student(String name, String username, String password) {
        Name = name;
        Username = username;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail_ID() {
        return Email_ID;
    }

    public void setEmail_ID(String email_ID) {
        Email_ID = email_ID;
    }


    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

}
