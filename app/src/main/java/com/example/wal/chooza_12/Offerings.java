package com.example.wal.chooza_12;

import android.content.Context;
import android.content.Intent;
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
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waleed-PC on 11/24/2016.
 */

public class Offerings extends AppCompatActivity{
    private RecyclerView recyclerView;
    private UniAlbumsAdapter adapter;
    private List<University> universityList;
    Context ctx;

    public String[] nameList;
    public String[] durationList;
    public String[] hsscList;
    public String[] sscList;
    public String[] cityList;
    public String[] sectorList;
    public String[] websiteList;
    public String Pname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offerings);

        TakeTest tt= new TakeTest();
        Intent i= getIntent();
        Pname= i.getExtras().getString("Pname");
        AsyncTask<Void, Void, Void> myTask = new GetResults(this);
        myTask.execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new Offerings.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new UniAlbumsAdapter(this, universityList);

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
    private class GetResults extends AsyncTask<Void,Void, Void> {

        private Context mContext;
        public GetResults(Context context) {
            this.mContext= context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params) {
            universityList = new ArrayList<>();
            try {
                ServiceHandler sh = new ServiceHandler();
                String url = "http://172.20.103.38/chooza/API/GetAllOfferingUniversities?";
                String selectedProgramUrl= url+"Pname="+Pname;
                String jsonStr = sh.makeServiceCall(selectedProgramUrl, ServiceHandler.GET);
                JSONArray jr = new JSONArray(jsonStr);
                nameList= new String[jr.length()];
                durationList= new String[jr.length()];
                hsscList= new String[jr.length()];
                sscList= new String[jr.length()];
                cityList= new String[jr.length()];
                sectorList= new String[jr.length()];
                websiteList= new String[jr.length()];
                for (int i = 0; i<jr.length(); i++) {
                    JSONObject jsonobject = (JSONObject) jr.get(i);
                    durationList[i]=jsonobject.optString("Duration");
                    hsscList[i] = jsonobject.optString("HSSC_Criteria");
                    sscList[i]=jsonobject.optString("SSC_Criteria");
                    JSONObject university= jsonobject.getJSONObject("University");
                    cityList[i]=(university.optString("City"));
                    nameList[i]=(university.optString("Name"));
                    sectorList[i]=(university.optString("Sector"));
                    websiteList[i]=(university.optString("Website"));

                    System.out.println(durationList[i]);
                    System.out.println(hsscList[i]);
                    System.out.println(sscList[i]);
                    System.out.println(cityList[i]);
                    System.out.println(nameList[i]);
                    System.out.println(websiteList[i]);
                    University a = new University(nameList[i], R.drawable.ist, cityList[i], websiteList[i], sectorList[i]+" Sector University", "Required HSSC Percentage: "+hsscList[i],"Required SSC Percentage: "+sscList[i], "Duration: "+durationList[i]+" years");

                    universityList.add(a);
                }


            }catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyDataSetChanged();
            adapter = new UniAlbumsAdapter(mContext, universityList);
            recyclerView.setAdapter(adapter);
            super.onPostExecute(aVoid);
        }
    }
}
