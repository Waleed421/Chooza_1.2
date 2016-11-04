package com.example.wal.chooza_12;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wal on 9/17/2016.
 */
public class MyDBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="CHOOZA.db";

    //Student Table
    private  static final String TAG_STUDENT= "Student";
    private static final String COLUMN_STUDENTID="Student_ID";
    private static final String COLUMN_NAME="Name";
    private static final String COLUMN_STUDENTUSERNAME="Username";
    private static final String COLUMN_PASSWORD="Password";
    private static final String COLUMN_EMAILID="Email_ID";
    private static final String COLUMN_PICTURE="Picture";
    private static final String COLUMN_GENDER="Gender";
    private static final String COLUMN_DOB="DOB";
    private static final String COLUMN_PHONE="Phone";


    //Test Table
    private  static final String TAG_TEST= "Test";
    private static final String COLUMN_TESTID="Test_ID";
    private static final String COLUMN_TESTTYPE="Type";

    //Question Table
    private  static final String TAG_QUESTION= "Question";
    private static final String COLUMN_QUESTIONID="Question_ID";
    private static final String COLUMN_STATEMENT="Statement";
    private static final String COLUMN_TYPE="Type";

    //Option Table
    private  static final String TAG_OPTION= "Option";
    private static final String COLUMN_OPTIONID="Option_ID";

    //Result Table
    private  static final String TAG_RESULT= "Result";
    private static final String COLUMN_RESULTID="Result_ID";
    private static final String COLUMN_PERSONALITYINTEREST="Personality_Interest";

    //Recommendation Table
    private  static final String TAG_RECOMMENDATION= "Recommendation";
    private static final String COLUMN_RECOMMENDATIONID="Recommendation_ID";

    //University Table
    private static final String TAG_UNIVERSITY = "University";
    private static final String COLUMN_UNIVERSITYID="University_ID";
    private static final String COLUMN_UNIVERSITYNAME="Name";
    private static final String COLUMN_CITY="City";
    private static final String COLUMN_INTRODUCTION="Introduction";
    private static final String COLUMN_IMAGEDATA="ImageData";
    private static final String COLUMN_ADDRESS="Address";
    private static final String COLUMN_WEBSITE="Website";
    private static final String COLUMN_SECTOR="Sector";

    //Program Table
    private static final String TAG_PROGRAM = "Program";
    private static final String COLUMN_PROGRAMID = "Program_ID";
    private static final String COLUMN_PROGRAMNAME = "Name";
    private static final String COLUMN_SKILLSNEEDED = "Skills_Needed";
    private static final String COLUMN_EDUCATIONSCOPE = "Education_Scope";
    private static final String COLUMN_EMPLOYMENTSCOPE = "Employment_Scope";
    private static final String COLUMN_PROGRAMOVERVIEW = "Program_Overview";

    //Program_Univserity Table
    private static final String TAG_PROGRAMUNIVERSITY = "Program_University";
    private static final String COLUMN_PROGRAMUNIVERSITYID = "Program_Uni_ID";
    private static final String COLUMN_DURATION = "Duration";
    private static final String COLUMN_HSSCCRITERIA = "HSSC_Criteria";
    private static final String COLUMN_SSCCRITERIA = "SSC_Criteria";
    private static final String COLUMN_REFERENCE = "Reference";

    //Fee_Structure Table
    private static final String TAG_FEESTRUCTURE = "Fee_Structure";
    private static final String COLUMN_FEEID = "Fee_ID";
    private static final String COLUMN_FIRSTSEMESTERFEE = "First_Semester_Fee";
    private static final String COLUMN_SECONDONWARDSFEE = "Second_Onwards_Fee";

    //aspnet_Roles Table
    private static final String TAG_ASPNET_ROLES = "aspnet_Roles";
    private static final String COLUMN_APPLICATIONID = "ApplicationId";
    private static final String COLUMN_ROLEID = "RoleId";
    private static final String COLUMN_ROLENAME = "RoleName";

    //aspnet_Users Table
    private static final String TAG_ASPNET_USERS = "aspnet_Users";
    private static final String COLUMN_USERID = "UserId";
    private static final String COLUMN_USERNAME = "UserName";

    //aspnet_UsersInRoles Table
    private static final String TAG_ASPNET_USERSINROLES = "aspnet_UsersInRoles";

    private SQLiteDatabase database;


    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUESTION_TABLE = "CREATE TABLE " + TAG_QUESTION + "("
                + COLUMN_QUESTIONID + " TEXT," + COLUMN_STATEMENT + " TEXT,"
                + COLUMN_TYPE + " TEXT" + ")";
        db.execSQL(CREATE_QUESTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TAG_QUESTION);
        // Creating tables again
        onCreate(db);
    }
    // Adding new university
    public void addUniversity(University university) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(COLUMN_UNIVERSITYID, university.getUniversity_ID());
        values.put(COLUMN_UNIVERSITYNAME, university.getName());
        //values.put(COLUMN_CITY, university.getCity());
        //values.put(COLUMN_INTRODUCTION, university.getIntroduction());
        //values.put(COLUMN_IMAGEDATA, university.getImageData());
        //values.put(COLUMN_ADDRESS, university.getAddress());
        //values.put(COLUMN_WEBSITE, university.getWebsite());
        //values.put(COLUMN_SECTOR, university.getSector());
        db.insert(TAG_UNIVERSITY, null, values);
        db.close(); // Closing database connection
    }

        // Getting All Universities
        public List<University> getAllUniversities() {
        List<University> UniversityList = new ArrayList<University>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TAG_UNIVERSITY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
              /*  University university = new University();
                university.setUniversity_ID(Integer.parseInt(cursor.getString(0)));
                university.setName(cursor.getString(1));
                university.setCity(cursor.getString(2));
                university.setIntroduction(cursor.getString(3));
                university.setImageData(cursor.getString(4));
                university.setAddress(cursor.getString(5));
                university.setWebsite(cursor.getString(6));
                university.setSector(cursor.getString(7));*/

        // Adding contact to list
               // UniversityList.add(university);
            } while (cursor.moveToNext());
        }
        // return contact list
        return UniversityList;
    }
    // Adding new program
    public void addProgram(Program program) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PROGRAMID, program.getProgram_ID());
        values.put(COLUMN_PROGRAMNAME, program.getName());
        values.put(COLUMN_SKILLSNEEDED, program.getSkills_Needed());
        values.put(COLUMN_EDUCATIONSCOPE, program.getEducation_Scope());
        values.put(COLUMN_EMPLOYMENTSCOPE, program.getEmployment_Scope());
        values.put(COLUMN_PROGRAMOVERVIEW, program.getProgram_Overview());

        db.insert(TAG_PROGRAM, null, values);
        db.close(); // Closing database connection
    }
    // Adding new program_university
    public void addProgramUniversity(Program_University programUniversity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PROGRAMUNIVERSITYID, programUniversity.getProgram_Uni_ID());
        values.put(COLUMN_PROGRAMID, programUniversity.getProgram_ID());
        values.put(COLUMN_UNIVERSITYID, programUniversity.getUniversity_ID());
        values.put(COLUMN_FEEID, programUniversity.getFee_ID());
        values.put(COLUMN_HSSCCRITERIA, programUniversity.getHSSC_Criteria());
        values.put(COLUMN_SSCCRITERIA, programUniversity.getSSC_Criteria());
        values.put(COLUMN_REFERENCE, programUniversity.getReference());

        db.insert(TAG_PROGRAMUNIVERSITY, null, values);
        db.close(); // Closing database connection
    }

    //Adding new Student
    public  void addStudent(Student student)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENTID, student.getStudent_ID());
        values.put(COLUMN_NAME, student.getName());
        values.put(COLUMN_USERNAME, student.getUsername());
        values.put(COLUMN_PASSWORD, student.getPassword());
        values.put(COLUMN_EMAILID, student.getEmail_ID());
        values.put(COLUMN_PICTURE, student.getPicture());
        values.put(COLUMN_GENDER, student.getGender());
        values.put(COLUMN_DOB, student.getDOB());
        values.put(COLUMN_CITY, student.getCity());
        values.put(COLUMN_PHONE, student.getPhone());


        db.insert(TAG_STUDENT, null, values);
        db.close(); // Closing database connection
    }

    //Adding new Test
    public  void addTest(Test test)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TESTID, test.getTest_ID());
        values.put(COLUMN_TESTTYPE, test.getType());
        db.insert(TAG_TEST, null, values);
        db.close(); // Closing database connection
    }

    //Adding new Question
    public  void addQuestion(Question question)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTIONID, question.getQuestion_ID());
        values.put(COLUMN_STATEMENT, question.getStatement());
        values.put(COLUMN_TYPE, question.getType());
        db.insert(TAG_QUESTION, null, values);
        db.close(); // Closing database connection

    }
    // Getting All Questions
    public List<Question> getAllQuestions() {

        List<Question> questionList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TAG_QUESTION;

        SQLiteDatabase db = this.getWritableDatabase();
        db.isOpen();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion_ID((cursor.getString(0)));
                question.setStatement(cursor.getString(1));
                question.setType(cursor.getString(2));
                // Adding question to list
                questionList.add(question);
            } while (cursor.moveToNext());
        }

        // return question list
        return questionList;
    }

    //Adding new Option
    public  void addOption(Option option)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_OPTIONID, option.getOption_ID());
        values.put(COLUMN_QUESTIONID, option.getQuestion_ID());
        values.put(COLUMN_STATEMENT, option.getStatement());
        db.insert(TAG_OPTION, null, values);
        db.close(); // Closing database connection
    }

    //Adding new Result
    public  void addResult(Result result)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESULTID, result.getResult_ID());
        values.put(COLUMN_PERSONALITYINTEREST, result.getPersonality_Interest());
        values.put(COLUMN_TESTID, result.getTest_ID());
        values.put(COLUMN_STUDENTID, result.getStudent_ID());
        db.insert(TAG_RESULT, null, values);
        db.close(); // Closing database connection
    }

    //Adding new Recommendation
    public  void addRecommendation(Recommendation recommendation)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECOMMENDATIONID, recommendation.getRecommendation_ID());
        values.put(COLUMN_RESULTID, recommendation.getResult_ID());
        values.put(COLUMN_PROGRAMID, recommendation.getProgram_ID());
        db.insert(TAG_RECOMMENDATION, null, values);
        db.close(); // Closing database connection
    }
}
