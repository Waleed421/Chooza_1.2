package com.example.wal.chooza_12;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private UniversityAdapter adapter;
    private List<University> universityList;
    //url
    //private String url = "http://project-demos.com/video/api/web/v1/products?lang=2&expand=cookbyweight,cookbythickness,productnames,cuts,cutnames,productlanguages";

    // JSON Node names
    private  static final String TAG_STUDENT= "Student";
    private  static final String TAG_TEST= "Test";
    private  static final String TAG_OPTION= "Option";
    private  static final String TAG_QUESTION= "Question";
    private  static final String TAG_RESULT= "Result";
    private  static final String TAG_RECOMMENDATION= "Recommendation";
    private static final String TAG_UNIVERSITY = "University";
    private static final String TAG_PROGRAM = "Program";
    private static final String TAG_PROGRAM_UNIVERSITY = "Program_University";
    private static final String TAG_FEESTRUCTURE = "Fee_Structure";
    private static final String TAG_ASPNET_ROLES = "aspnet_Roles";
    private static final String TAG_ASPNET_USERS = "aspnet_Users";
    private static final String TAG_ASPNET_USERSINROLES = "aspnet_UsersInRoles";
    private MyDBHandler databaseHandler;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> universities;


    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHandler = new MyDBHandler(MainActivity.this);
        universities = new ArrayList<HashMap<String, String>>();

        /**
         *Setup the DrawerLayout and NavigationView
         */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();



                if (menuItem.getItemId() == R.id.test) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new TestFragment()).commit();

                }

                if (menuItem.getItemId() == R.id.univerity) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new UniversityFragment()).commit();
                }

                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }
    public void viewUniversities(View view) {
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchUniversity.class);
                startActivity(intent);
            }
        });
    }
    private class GetRecords extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // CustomJSONadapter adapter1 = new CustomJSONadapter(getApplicationContext(),animals) ;
           /* ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, product,
                    R.layout.list_item, new String[] { TAG_ID, TAG_PRODUCTS_ID, TAG_CUT_NAME}, new int[] { R.id.name,
                    R.id.id, R.id.productname});
            setListAdapter(adapter);*/
        }

    }
}