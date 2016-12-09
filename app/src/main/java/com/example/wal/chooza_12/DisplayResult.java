package com.example.wal.chooza_12;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Waleed-PC on 11/24/2016.
 */

public class DisplayResult extends AppCompatActivity{
    public String[] ProgramsList;
    public String[] EducationScopeList;
    public String[] EmploymentScopeList;
    ListView listview ;
    ArrayList<Result> results;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    public String RealisticCount;
    public String InvestigativeCount;
    public String ArtisticCount;
    public String SocialCount;
    public String EnterprisingCount;
    public String ConventionalCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_display);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(DisplayResult.this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        TakeTest tt= new TakeTest();
        Intent i= getIntent();
        RealisticCount= i.getExtras().getString("RCount");
        InvestigativeCount= i.getExtras().getString("ICount");
        ArtisticCount= i.getExtras().getString("ACount");
        SocialCount= i.getExtras().getString("SCount");
        EnterprisingCount= i.getExtras().getString("ECount");
        ConventionalCount= i.getExtras().getString("CCount");
        listview = (ListView) findViewById(R.id.listView);
        new GetResults().execute();
        Button button = (Button) findViewById(R.id.buttonOk);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayResult.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private class GetResults extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            results= new ArrayList<>();
            try {
                ServiceHandler sh = new ServiceHandler();
                String url = "http://192.168.43.73/chooza1/API/GetAllResultPrograms?";
                String testResultUrl= url+"R="+RealisticCount+"&I="+InvestigativeCount+"&A="+ArtisticCount+"&S="+SocialCount+"&E="+EnterprisingCount+"&C="+ConventionalCount;
                String jsonStr = sh.makeServiceCall(testResultUrl, ServiceHandler.GET);
                JSONArray jr = new JSONArray(jsonStr);
                ProgramsList= new String[jr.length()];
                EducationScopeList= new String[jr.length()];
                EmploymentScopeList= new String[jr.length()];
                for (int i = 0; i<jr.length(); i++) {
                    JSONObject jsonobject = (JSONObject) jr.get(i);
                    String id = jsonobject.optString("Id");
                    String program_ID = jsonobject.optString("Program_ID");
                    String university = jsonobject.optString("University");
                    JSONObject program= jsonobject.getJSONObject("Program");
                    String name=(program.optString("Name"));
                    String educationScope=(program.optString("EducationScope"));
                    String employmentScope=(program.optString("EmploymentScope"));
                    Result obj= new Result(name, educationScope, employmentScope);
                    results.add(obj);
                }
                System.out.println(ProgramsList);
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ResultAdapter adapter = new ResultAdapter(DisplayResult.this, results);
            listview.setAdapter(adapter);
            super.onPostExecute(aVoid);
        }
    }


}
