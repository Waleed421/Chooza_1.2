package com.example.wal.chooza_12;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class UniAlbumsAdapter extends RecyclerView.Adapter<UniAlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<University> universityList;
    private final String YOUR_URL = "https://www.comsats.edu.pk/";
    public String[] URL= new String[196];

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, city, sector, website, duration, hssc, ssc;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            city = (TextView) view.findViewById(R.id.city);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            sector = (TextView) view.findViewById(R.id.sector);
            website = (TextView) view.findViewById(R.id.website);
            duration = (TextView) view.findViewById(R.id.duration);
            hssc = (TextView) view.findViewById(R.id.hssc);
            ssc = (TextView) view.findViewById(R.id.ssc);

            Linkify.addLinks(website, Linkify.WEB_URLS);
        }
    }


    public UniAlbumsAdapter(Context mContext, List<University> universityList) {
        this.mContext = mContext;
        this.universityList = universityList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.uni_card_offering, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        University university = universityList.get(position);
        holder.title.setText(university.getName());
        holder.city.setText(university.getCity());
        holder.sector.setText(university.getSector());
        holder.website.setText(university.getWebsite());
        holder.duration.setText(university.getDuration());
        holder.hssc.setText(university.getHSSC_Criteria());
        holder.ssc.setText(university.getSSC_Criteria());

        String url=university.getWebsite();


        // loading album cover using Glide library
        Glide.with(mContext).load(university.getThumbnail()).into(holder.thumbnail);

    }



    @Override
    public int getItemCount() {
        return universityList.size();
    }
}