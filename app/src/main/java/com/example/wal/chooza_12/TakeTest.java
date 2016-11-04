package com.example.wal.chooza_12;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import static java.security.AccessController.getContext;


/**
 * Created by Waleed-PC on 10/31/2016.
 */

public class TakeTest extends AppCompatActivity{
    private String url="http://192.168.0.134/chooza/api/QuestionValues/GetAllQuestions";
    private String urlu="http://192.168.0.134/chooza/api/UniversityValues/GetAllUniversities";
    private MyDBHandler databaseHandler;
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> questions;
    private List<Question> questionList;
    ListView listview ;
    Context ctx;
    SparseBooleanArray sparseBooleanArray ;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_take);
        listview = (ListView)findViewById(R.id.listView);
        new GetRecords().execute();
        databaseHandler = new MyDBHandler(TakeTest.this);
        /**
         *Setup the DrawerLayout and NavigationView
         */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(TakeTest.this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        //questions = new ArrayList<HashMap<String, String>>();
        // Reading all questions
        Log.d("Reading: ", "Reading all questions..");

        List<Question> questions = databaseHandler.getAllQuestions();

        for (Question qu : questions) {
            String log = "Id: " + qu.getQuestion_ID() + " ,Statement: " + qu.getStatement() + " ,Type: " + qu.getType();
            // Writing Questions to log
            Log.d("Name: ", log);
        }

    }
    private class GetRecords extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {

                //ServiceHandler sh = new ServiceHandler();
                //String jsonStr = sh.makeServiceCall(urlu, ServiceHandler.GET);
                //MyJSON.saveData(TakeTest.this, jsonStr);
                //saveToFile("Test", jsonStr );
                //writeToFile("Test.txt", jsonStr);
                JSONArray jr = new JSONArray(readFromFile(TakeTest.this, "Test.txt"));
                for (int i = 0; i<jr.length(); i++)
                {
                    JSONObject jsonobject= (JSONObject) jr.get(i);
                    String id=jsonobject.optString("Id");
                    String statement=jsonobject.optString("Statement");
                    String type=jsonobject.optString("Type");
                    //databaseHandler.addQuestion(new Question(id, statement, type));
                    //prepareAlbums(jsonobject.optString("Name"), jsonobject.optString("City"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            List<Question> questions = databaseHandler.getAllQuestions();
            final String[] array = new String[questions.size()];
            int index = 0;
            for (Question qu : questions) {
                array[index]=qu.getStatement();
                index++;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (TakeTest.this,android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, array);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // TODO Auto-generated method stub

                    sparseBooleanArray = listview.getCheckedItemPositions();

                    String ValueHolder = "" ;

                    int i = 0 ;

                    while (i < sparseBooleanArray.size()) {

                        if (sparseBooleanArray.valueAt(i)) {

                            ValueHolder += array [ sparseBooleanArray.keyAt(i) ] + ",";
                        }

                        i++ ;
                    }

                    ValueHolder = ValueHolder.replaceAll("(,)*$", "");

                    Toast.makeText(TakeTest.this, "ListView Selected Values = " + ValueHolder, Toast.LENGTH_LONG).show();

                }
            });

        }

    }
    public static String readFromFile(Context context, String file) {
        try {
            //File f = new File(context.getFilesDir().getPath() + "/" + file);
            //check whether file exists
            //FileInputStream is = new FileInputStream(f);
            InputStream is = context.getAssets().open(file);
            int size = is.available();
            byte buffer[] = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            return "" ;
        }
    }
    private void writeToFile(String file, String data) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(
                    file, 0));

            out.write(data);
            out.close();
        }

        catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void saveToFile(String FILENAME, String Json)
    {
        FileOutputStream fos;
        String object=Json;

        try
        {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);//IT SHOULD BE PRIVATE MODE
            fos.write(object.getBytes());
            fos.close();
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }

    }
    private void ReadFromFile(String file)
    {
        try {
            InputStream in = openFileInput(file);
            if (in != null) {
                InputStreamReader tmp = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(tmp);
                String str;
                StringBuffer buf = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buf.append(str + "\n");
                }
                in.close();
                String temp = "Not Working";
                temp = buf.toString();
                Toast.makeText(this, temp, Toast.LENGTH_SHORT).show();
            }
        } catch (java.io.FileNotFoundException e) {
            // that's OK, we probably haven't created it yet
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
