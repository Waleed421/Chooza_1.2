package com.example.wal.chooza_12;

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


public class ProgramFragment extends Fragment {
    Spinner spPrograms;
    ArrayList<String> ProgramsList= new ArrayList<>();
    //Store unique items in result
    ArrayList<String> result= new ArrayList<>();
    ArrayAdapter<String> adapterProgramsList;
    public static String selectedItemText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.programs, container, false);
        spPrograms = (Spinner)v.findViewById(R.id.spPrograms);
        //if(spPrograms == null && spPrograms.getSelectedItem() ==null )
        new GetRecords().execute();
        if(ProgramsList!=null) {
            adapterProgramsList = new ArrayAdapter<String>(getActivity(),
                    R.layout.spinner_layout, ProgramsList);
            adapterProgramsList.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spPrograms.setAdapter(adapterProgramsList);
        }
        return v;
    }
    private class GetRecords extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Creating service handler class instance
                ServiceHandler sh = new ServiceHandler();
                String url = "http://192.168.100.37/chooza1/API/GetAllPrograms";
                String jsonStr = (sh.makeServiceCall(url, ServiceHandler.GET));
                JSONArray jr = new JSONArray(jsonStr);
                for (int i = 0; i < jr.length(); i++) {
                    JSONObject jsonobject = (JSONObject) jr.get(i);
                    ProgramsList.add (jsonobject.optString("Name"));
                    System.out.println(ProgramsList);
                }
                System.out.println(ProgramsList);
            }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapterProgramsList = new ArrayAdapter<String>(getActivity(),
                    R.layout.spinner_layout, ProgramsList);
            adapterProgramsList.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spPrograms.setAdapter(adapterProgramsList);
            //Checking the selected item
            spPrograms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedItemText = (String) parent.getItemAtPosition(position);
                    // Notify the selected item text
                    //Toast.makeText
                            //(getActivity().getApplication(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            //.show();
                    System.out.println(selectedItemText);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
        });
    }

}
}