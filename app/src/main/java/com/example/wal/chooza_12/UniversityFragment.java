package com.example.wal.chooza_12;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;


public class UniversityFragment extends Fragment {
    // uicontrols
    Spinner spUniversities;

    ArrayList<String> uniList= new ArrayList<>();
    //Store unique items in result
    ArrayList<String> result= new ArrayList<>();
    ArrayAdapter<String> adapterUniList;
    public static String selectedCity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.universities, container, false);
        spUniversities = (Spinner)v.findViewById(R.id.spUniversities);
        new GetRecords().execute();
        /*adapterUniList = new ArrayAdapter<String>(this.getActivity(),
                R.layout.spinner_layout, uniList);
        adapterUniList.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spUniversities.setAdapter(adapterUniList);*/
        //return inflater.inflate(R.layout.universities,null);
        return v;
    }

    private class GetRecords extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Creating service handler class instance
                ServiceHandler sh = new ServiceHandler();
                String url="http://192.168.43.73/chooza1/API/GetAllUniversities";
                String jsonStr = (sh.makeServiceCall(url, ServiceHandler.GET));
                JSONArray jr = new JSONArray(jsonStr);
                for (int i = 0; i<jr.length(); i++)
                {
                    JSONObject jsonobject= (JSONObject) jr.get(i);
                    //System.out.println(jsonobject.optString("Id"));
                    //System.out.println(jsonobject.optString("Name"));
                    //if (i == 0)
                        //uniList.add(jsonobject.optString("City"));
                    //if(uniList!=null) {
                        //for (String item : uniList) {
                          //if (item != (jsonobject.optString("City")))
                    //for (int j = 0; j<jr.length(); j++) {
                        //if(uniList[j]!=(jsonobject.optString("City")))
                        uniList.add (jsonobject.optString("City"));

                   // }
                    System.out.println(uniList);
//                        }
                    }

                //Record encountered string in Hashmap
                HashSet<String> set= new HashSet<>();
                //Loop over uniList array
                for(String item: uniList)
                {
                    //If city is not in the set, add it to list and the set
                    if(!set.contains(item))
                    {
                        result.add(item);
                        set.add(item);
                    }
                }
                System.out.println(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapterUniList = new ArrayAdapter<String>((Activity)getActivity(),
                    R.layout.spinner_layout, result);
            adapterUniList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spUniversities.setAdapter(adapterUniList);

            //Checking the selected item
            spUniversities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedCity = (String) parent.getItemAtPosition(position);
                    // Notify the selected item text
                    //Toast.makeText
                            //(getActivity().getApplication(), "Selected : " + selectedCity, Toast.LENGTH_SHORT)
                            //.show();
                    System.out.println(selectedCity);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
}