package com.example.wal.chooza_12;

/**
 * Created by wal on 9/17/2016.
 */
public class Result {
    private String Name;
    private String Education_Scope;
    private String Employment_Scope;


    public Result(String name, String education_Scope, String employment_Scope) {
        Name = name;
        Education_Scope = education_Scope;
        Employment_Scope = employment_Scope;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEducation_Scope() {
        return Education_Scope;
    }

    public void setEducation_Scope(String education_Scope) {
        Education_Scope = education_Scope;
    }

    public String getEmployment_Scope() {
        return Employment_Scope;
    }

    public void setEmployment_Scope(String employment_Scope) {
        Employment_Scope = employment_Scope;
    }
}
