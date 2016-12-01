package com.example.wal.chooza_12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Waleed-PC on 11/27/2016.
*/

public class ResultAdapter extends ArrayAdapter<Result> {
    public ResultAdapter(Context context, ArrayList<Result> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Result result = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        TextView txtTitle2 = (TextView) convertView.findViewById(R.id.txtTitle2);
        TextView txtTitle3 = (TextView) convertView.findViewById(R.id.txtTitle3);
        // Populate the data into the template view using the data object
        txtTitle.setText(result.getName());
        txtTitle2.setText(result.getEducation_Scope());
        txtTitle3.setText(result.getEmployment_Scope());
        // Return the completed view to render on screen
        return convertView;
    }
}