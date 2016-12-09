package com.example.wal.chooza_12;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<University> universityList;
    public String[] URL= new String[196];

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, city, sector, website;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            city = (TextView) view.findViewById(R.id.city);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            website = (TextView) view.findViewById(R.id.website);
            sector = (TextView) view.findViewById(R.id.sector);

        }
    }


    public AlbumsAdapter(Context mContext, List<University> universityList) {
        this.mContext = mContext;
        this.universityList = universityList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.uni_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        University university = universityList.get(position);
        holder.name.setText(university.getName());
        holder.city.setText(university.getCity());
        holder.sector.setText(university.getSector());
        holder.website.setText(university.getWebsite());


        // loading album cover using Glide library
        Glide.with(mContext).load(university.getThumbnail()).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return universityList.size();
    }
}