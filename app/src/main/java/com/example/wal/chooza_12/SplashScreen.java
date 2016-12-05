package com.example.wal.chooza_12;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.List;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    private MyDBHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                databaseHandler = new MyDBHandler(SplashScreen.this);
                List<Student> studentList = databaseHandler.getAllStudents();
                if(studentList.isEmpty()) {
                //if(true){
                    Intent i = new Intent(SplashScreen.this, signup.class);
                    startActivity(i);
                }
                else
                {
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
