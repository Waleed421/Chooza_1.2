package com.example.wal.chooza_12;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchUniversity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    private List<University> universityList;
    private static String SelectedCity;


    //url
    //private String url = "http://project-demos.com/video/api/web/v1/products?lang=2&expand=cookbyweight,cookbythickness,productnames,cuts,cutnames,productlanguages";
    //private String url="http://192.168.0.137/chooza/api/UniversityValues/GetAllUniversities";
    Context ctx;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.university_search);
        UniversityFragment uf= new UniversityFragment();
        SelectedCity= uf.selectedCity;
        databaseHandler = new MyDBHandler(SearchUniversity.this);
        universities = new ArrayList<HashMap<String, String>>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList);



        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        //new GetRecords().execute();

        try {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            String url="http://172.20.103.38/Chooza/API/GetAllUniversities";
            //String jsonStr = (sh.makeServiceCall(url, ServiceHandler.GET));
            String jsonStr = (readFromFile(SearchUniversity.this , "uniJson.txt"));
            JSONArray jr = new JSONArray(jsonStr);
            for (int i = 0; i<jr.length(); i++)
            {
                JSONObject jsonobject= (JSONObject) jr.get(i);
                System.out.println(jsonobject.optString("Id"));
                System.out.println(jsonobject.optString("Name"));
                if(jsonobject.optString("City").equals(SelectedCity))
                prepareAlbums(jsonobject.optString("Name"), jsonobject.optString("City"), jsonobject.optString("Website"));
            }
            //JSONObject jj = new JSONObject();
            //jj = (JSONObject) jj.get("Name");
            //System.out.println(jj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            Glide.with(this).load(R.drawable.banner).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private class GetRecords extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            String url="http://192.168.100.113/Chooza/API/GetAllUniversities";
            String jsonStr = (sh.makeServiceCall(url, ServiceHandler.GET));

            if (jsonStr != null) {
                try {

                    JSONArray jr = new JSONArray(jsonStr);
                    for (int i = 0; i < jr.length(); i++) {
                        JSONObject jsonobject = jr.getJSONObject(i);
                        String name = jsonobject.getString("Name");
                        String city = jsonobject.getString("City");
                        String website = jsonobject.getString("Website");
                        System.out.println(name);
                        prepareAlbums(name, city, website);
                        //prepareAlbums(jsonobject.optString("Name"), jsonobject.optString("City"), jsonobject.optString("Website"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            /*try {
            JSONArray jr = new JSONArray(readFromFile(SearchUniversity.this , "Json.txt"));
            for (int i = 0; i<10; i++)
            {
                JSONObject jsonobject= (JSONObject) jr.get(i);
                //System.out.println(jsonobject.optString("Id"));
                System.out.println(jsonobject.optString("Name"));
                //prepareAlbums(jsonobject.optString("Name"), jsonobject.optString("City"));
            }
            //JSONObject jj = new JSONObject();
            //jj = (JSONObject) jj.get("Name");
            //System.out.println(jj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/
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

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums(String Name, String City, String Website) {
        int[] covers = new int[]{
                R.drawable.comsats,
                R.drawable.lahore,
                R.drawable.taxila,
                R.drawable.nust,
                R.drawable.fast,
                R.drawable.bahria,
                R.drawable.giki,
                R.drawable.pieas,
                R.drawable.ist,
                R.drawable.air,
                R.drawable.university};
        Album a = new Album(Name, 13, covers[8], Website);
        albumList.add(a);
        /*Album a = new Album("COMSATS Institute of Information Technology", 13, covers[0]);
        albumList.add(a);

        a = new Album("University of Engineering & Technology, Lahore", 8, covers[1]);
        albumList.add(a);

        a = new Album("University of Engineering & Technology, Taxila", 11, covers[2]);
        albumList.add(a);

        a = new Album("National University of Science and Technology", 12, covers[3]);
        albumList.add(a);

        a = new Album("National University of Compputer and Emerging Sciences", 14, covers[4]);
        albumList.add(a);

        a = new Album("Bahria University", 1, covers[5]);
        albumList.add(a);

        a = new Album("Ghulam Ishaq Khan Institute of Technology", 11, covers[6]);
        albumList.add(a);

        a = new Album("Pakistan Institute of Emerging & Applied Sciences", 14, covers[7]);
        albumList.add(a);

        a = new Album("Institute of Space Technology", 11, covers[8]);
        albumList.add(a);

        a = new Album("Air University", 17, covers[9]);
        albumList.add(a);*/

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    public static String readFromFile(Context context, String file) {
        try {
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
    public void saveToFile(String FILENAME, String Json)
    {
        FileOutputStream fos;
        String object=Json;

        try
        {
            fos = ctx.openFileOutput(FILENAME, Context.MODE_WORLD_WRITEABLE);//IT SHOULD BE PRIVATE MODE
            fos.write(object.getBytes());
            fos.close();
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }
        Toast.makeText(ctx,"Waleed:Saving in phone sucessfull",Toast.LENGTH_SHORT).show();
    }
}
