package com.app.drashti.drashtiapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.drashti.drashtiapp.Model.Donate;
import com.app.drashti.drashtiapp.R;
import com.app.drashti.drashtiapp.ViewFoodDetailActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class MyAcceptedDonationAdapter extends RecyclerView.Adapter<MyAcceptedDonationAdapter.DonateHolder> {


    public Context mContext;
    public ArrayList<Donate> mDonates;

    public MyAcceptedDonationAdapter(Context mContext, ArrayList<Donate> mDonates) {
        this.mContext = mContext;
        this.mDonates = mDonates;
    }

    @Override
    public DonateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_mydonate, parent, false);
        return new DonateHolder(view);
    }

    @Override
    public void onBindViewHolder(DonateHolder holder, int position) {


        final Donate donate = mDonates.get(position);
        Picasso.with(mContext).load(donate.getF_image()).into(holder.imgPRofile);
        holder.txtName.setText(donate.getF_name());
        holder.txtDescription.setText(donate.getF_description());
        holder.txtQuantity.setText("Quantity : " + donate.getF_quantity());
        holder.txtDatePicker.setText(donate.getF_pickupDate());
        holder.btnAccept.setText("Accepted");
        //  holder.btnAccept.setVisibility(View.INVISIBLE);


        holder.lnvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ViewFoodDetailActivity.class);
                intent.putExtra("Donate", (Serializable) donate);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDonates.size();
    }


    public class DonateHolder extends RecyclerView.ViewHolder {

        private ImageView imgPRofile;
        private TextView txtName;
        private TextView txtDescription;
        private TextView txtQuantity;
        private Button btnAccept;
        private TextView txtDatePicker;

        private LinearLayout lnvDetail;

        public DonateHolder(View itemView) {
            super(itemView);
            imgPRofile = (ImageView) itemView.findViewById(R.id.imgPRofile);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtQuantity = (TextView) itemView.findViewById(R.id.txtQuantity);
            btnAccept = (Button) itemView.findViewById(R.id.btnAccept);
            txtDatePicker = (TextView) itemView.findViewById(R.id.txtDatePicker);
            lnvDetail = (LinearLayout) itemView.findViewById(R.id.lnvDetail);
        }
    }

}
