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
    private List<University> universityList;
    private static String SelectedCity;
    public String[] nameList;
    public String[] cityList;
    public String[] websiteList;
    public String[] sectorList;
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

        universityList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, universityList);



        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        AsyncTask<Void, Void, Void> myTask = new SearchUniversity.GetRecords(this);
        myTask.execute();

        /*try {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            String url="http://192.168.100.120/Chooza1/API/GetAllUniversities";
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
*/

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
        private Context mContext;
        public GetRecords(Context context) {
            this.mContext= context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            universityList = new ArrayList<>();
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            String url="http://192.168.43.73/Chooza1/API/GetAllUniversities";
            String jsonStr = (sh.makeServiceCall(url, ServiceHandler.GET));

            if (jsonStr != null) {
                try {

                    JSONArray jr = new JSONArray(jsonStr);
                    nameList=new String[jr.length()];
                    cityList=new String[jr.length()];
                    sectorList=new String[jr.length()];
                    websiteList=new String[jr.length()];

                    for (int i = 0; i < jr.length(); i++) {
                        JSONObject jsonobject = jr.getJSONObject(i);
                        nameList[i] = jsonobject.getString("Name");
                        cityList[i] = jsonobject.getString("City");
                        websiteList[i] = jsonobject.getString("Website");
                        sectorList[i] = jsonobject.getString("Sector");

                        University u= new University(nameList[i], cityList[i], websiteList[i], sectorList[i], R.drawable.ist);

                        if(cityList[i].equals(SelectedCity))
                        universityList.add(u);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            adapter.notifyDataSetChanged();
            adapter = new AlbumsAdapter(mContext, universityList);
            recyclerView.setAdapter(adapter);
            super.onPostExecute(result);
        }

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
