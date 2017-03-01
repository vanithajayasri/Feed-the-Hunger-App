package com.app.drashti.drashtiapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.drashti.drashtiapp.Model.Donate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by BaNkEr on 20-11-2016.
 */
public class MyDonateAdapter extends RecyclerView.Adapter<MyDonateAdapter.DonateHolder> {


    public Context mContext;
    public ArrayList<Donate> mDonates;

    public MyDonateAdapter(Context mContext, ArrayList<Donate> mDonates) {
        this.mContext = mContext;
        this.mDonates = mDonates;
    }

    @Override
    public DonateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_donate,parent,false);
        return new DonateHolder(view);
    }

    @Override
    public void onBindViewHolder(DonateHolder holder, int position) {


        Donate donate = mDonates.get(position);

        Picasso.with(mContext).load(donate.getF_image()).into(holder.imgPRofile);
        holder.txtName.setText(donate.getF_name());
        holder.txtDescription.setText(donate.getF_description());
        holder.txtQuantity.setText(donate.getF_quantity());
        holder.txtDatePicker.setText(donate.getF_pickupDate());

    }

    @Override
    public int getItemCount() {
        return mDonates.size();
    }


    public class  DonateHolder extends RecyclerView.ViewHolder{

        private ImageView imgPRofile;
        private TextView txtName;
        private TextView txtDescription;
        private TextView txtQuantity;
        private TextView txtDatePicker;

        public DonateHolder(View itemView) {
            super(itemView);
            imgPRofile = (ImageView)itemView.findViewById( R.id.imgPRofile );
            txtName = (TextView)itemView.findViewById( R.id.txtName );
            txtDescription = (TextView)itemView.findViewById( R.id.txtDescription );
            txtQuantity = (TextView)itemView.findViewById( R.id.txtQuantity );
            txtDatePicker = (TextView)itemView.findViewById( R.id.txtDatePicker );

        }
    }

}
