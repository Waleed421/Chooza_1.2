package com.example.wal.chooza_12;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Waleed-PC on 10/31/2016.
 */

public class TakeTest extends AppCompatActivity{
    //private String url="http://172.20.103.77/chooza/api/QuestionValues/GetAllQuestions";
    private String urlu="http://192.168.10.19/chooza/api/UniversityValues/GetAllUniversities";
    private MyDBHandler databaseHandler;
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> questions;
    private List<Question> questionList;
    public ArrayList<String> ProgramsList= new ArrayList<>();
    public ArrayAdapter<String> adapterProgramsList;

    ListView listview ;
    Context ctx;
    SparseBooleanArray sparseBooleanArray ;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    public String test="4";
    public int RealisticCount;
    public int InvestigativeCount;
    public int ArtisticCount;
    public int SocialCount;
    public int EnterprisingCount;
    public int ConventionalCount;
    private int[] personalityTypeArray= new int[6];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_take1);
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
        /*Log.d("Reading: ", "Reading all questions..");

        List<Question> questions = databaseHandler.getAllQuestions();

        for (Question qu : questions) {
            String log = "Id: " + qu.getQuestion_ID() + " ,Statement: " + qu.getStatement() + " ,Type: " + qu.getType();
            // Writing Questions to log
            Log.d("Name: ", log);
        }*/

    }
    private class GetRecords extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {

                ServiceHandler sh = new ServiceHandler();
                String url="http://192.168.100.138/chooza1/API/GetAllQuestions";
                String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
                System.out.println(jsonStr);
                databaseHandler.deleteAllQuestions();
                //MyJSON.saveData(TakeTest.this, jsonStr);
                //saveToFile("Test", jsonStr );
                //writeToFile("Test.txt", jsonStr);
                //JSONArray jr = new JSONArray(readFromFile(TakeTest.this, "Test.txt"));
                JSONArray jr = new JSONArray(jsonStr);
                for (int i = 0; i<jr.length(); i++)
                {
                    JSONObject jsonobject= (JSONObject) jr.get(i);
                    String id=jsonobject.optString("Id");
                    String statement=jsonobject.optString("Statement");
                    String type=jsonobject.optString("Type");
                    databaseHandler.addQuestion(new Question(id, statement, type));
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
            final String[] questionType= new String[questions.size()];

            int index = 0;
            for (Question qu : questions) {
                array[index]=qu.getStatement();
                questionType[index]= qu.getType();
                System.out.println(questionType[index]);
                String log = "Id: " + qu.getQuestion_ID() + " ,Statement: " + qu.getStatement() + " ,Type: " + qu.getType();
                System.out.println(log);
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
                    System.out.println(questionType[position]);
                    if(sparseBooleanArray.get(position)==true) {
                        if (questionType[position].equals("Realistic"))
                            RealisticCount = RealisticCount + 1;
                        else if (questionType[position].equals("Investigative"))
                            InvestigativeCount = InvestigativeCount + 1;
                        else if (questionType[position].equals("Artistic"))
                            ArtisticCount = ArtisticCount + 1;
                        else if (questionType[position].equals("Social"))
                            SocialCount = SocialCount + 1;
                        else if (questionType[position].equals("Enterprising"))
                            EnterprisingCount = EnterprisingCount + 1;
                        else if (questionType[position].equals("Conventional"))
                            ConventionalCount = ConventionalCount + 1;
                    }
                    else
                    {
                        if (questionType[position].equals("Realistic"))
                            RealisticCount = RealisticCount - 1;
                        else if (questionType[position].equals("Investigative"))
                            InvestigativeCount = InvestigativeCount - 1;
                        else if (questionType[position].equals("Artistic"))
                            ArtisticCount = ArtisticCount - 1;
                        else if (questionType[position].equals("Social"))
                            SocialCount = SocialCount - 1;
                        else if (questionType[position].equals("Enterprising"))
                            EnterprisingCount = EnterprisingCount - 1;
                        else if (questionType[position].equals("Conventional"))
                            ConventionalCount = ConventionalCount - 1;
                    }

                    System.out.println(RealisticCount);
                    System.out.println(InvestigativeCount);
                    System.out.println(ArtisticCount);
                    System.out.println(SocialCount);
                    System.out.println(EnterprisingCount);
                    System.out.println(ConventionalCount);




                    //array[listview.getCheckedItemPosition()]


                    String ValueHolder = "" ;

                    int i = 0 ;
                    /*List<Question> questions = databaseHandler.getAllQuestions();
                    final String[] array = new String[questions.size()];
                    int index = 0;
                    while (i < sparseBooleanArray.size()) {
                        for (Question qu : questions) {
                            array[index] = qu.getStatement();
                            if (sparseBooleanArray.valueAt(i)&& sparseBooleanArray.keyAt(i)==array[index])
                            index++;
                        }
                    }*/
                    while (i < sparseBooleanArray.size()) {

                        if (sparseBooleanArray.valueAt(i)) {

                            ValueHolder += array [ sparseBooleanArray.keyAt(i) ] + ",";
                        }

                        i++ ;
                    }


                    ValueHolder = ValueHolder.replaceAll("(,)*$", "");

                    //Toast.makeText(TakeTest.this, "ListView Selected Values = " + ValueHolder, Toast.LENGTH_LONG).show();

                }
            });

        }

    }
    public void displayResults(View view)
    {
        Button button= (Button)findViewById(R.id.buttonTest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String R=""+RealisticCount;
                String I=""+ArtisticCount;
                String A=""+ArtisticCount;
                String S=""+SocialCount;
                String E=""+EnterprisingCount;
                String C=""+ConventionalCount;
                Intent intent = new Intent(v.getContext(), DisplayResult.class);
                intent.putExtra("RCount", R);
                intent.putExtra("ICount", I);
                intent.putExtra("ACount", A);
                intent.putExtra("SCount", S);
                intent.putExtra("ECount", E);
                intent.putExtra("CCount", C);
                startActivity(intent);

            }
        });
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
