package com.example.wal.chooza_12;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Waleed-PC on 12/2/2016.
 */

public class login extends AppCompatActivity {
    AutoCompleteTextView txtusername, txtpassword;
    public String username, password, name;
    Button btnLogin;
    private MyDBHandler databaseHandler;
    private boolean recordFound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        databaseHandler = new MyDBHandler(login.this);
        txtusername = (AutoCompleteTextView) findViewById(R.id.txtusername);
        txtpassword = (AutoCompleteTextView) findViewById(R.id.txtpassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username= txtusername.getText().toString();
                password= txtpassword.getText().toString();
                new LoginStudent().execute();
            }
    });
    }

    public void signup(View view)
    {
        TextView signup= (TextView) findViewById(R.id.link_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), signup.class);
                //intent.putExtra("Pname", pname);
                startActivity(intent);
            }
        });
    }
    private class LoginStudent extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            String url="http://192.168.100.138/chooza1/API/StudentLogin?";
            String urldata= url+"username="+username+"&password="+password;
            String jsonStr = (sh.makeServiceCall(urldata, ServiceHandler.GET));
                if(jsonStr!="") {
                    JSONObject student = new JSONObject(jsonStr);
                    name = student.optString("Name");
                    System.out.println(name);
                    List<Student> studentList = databaseHandler.getAllStudents();
                    for (Student qu : studentList) {
                    if(name==qu.getName())
                        //Toast.makeText(getApplicationContext(),"Welcome", Toast.LENGTH_SHORT).show();
                        recordFound=true;
                    }
                    if(recordFound==false)
                        databaseHandler.addStudent(new Student(name, username, password));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(name!=null)
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //intent.putExtra("Pname", pname);
                startActivity(intent);
            }
            else
                Toast.makeText(getApplicationContext(),"Incorrect username or password", Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
        }
    }
}
