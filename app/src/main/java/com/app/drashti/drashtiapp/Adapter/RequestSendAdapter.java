package com.app.drashti.drashtiapp.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.drashti.drashtiapp.Model.Donate;
import com.app.drashti.drashtiapp.R;
import com.app.drashti.drashtiapp.Utiliiyy.AppUtilty;
import com.app.drashti.drashtiapp.Utiliiyy.Constant;
import com.app.drashti.drashtiapp.Utiliiyy.JSONParser;
import com.app.drashti.drashtiapp.ViewFoodDetailActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class RequestSendAdapter extends RecyclerView.Adapter<RequestSendAdapter.DonateHolder> {


    public Activity mContext;
    public ArrayList<Donate> mDonates;
    AppUtilty appUtilty;

    public RequestSendAdapter(Activity mContext, ArrayList<Donate> mDonates) {
        this.mContext = mContext;
        appUtilty = new AppUtilty(mContext);
        this.mDonates = mDonates;
    }

    @Override
    public DonateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_myacceptor,parent,false);
        return new DonateHolder(view);
    }

    @Override
    public void onBindViewHolder(final DonateHolder holder, final int position) {


        final Donate donate = mDonates.get(position);

        Picasso.with(mContext).load(donate.getF_image()).into(holder.imgPRofile);
        holder.txtName.setText(donate.getF_name());
        holder.txtDescription.setText(donate.getF_description());
        holder.txtQuantity.setText("Quantity : "+donate.getF_quantity());
        holder.txtDatePicker.setText(donate.getF_pickupDate());
        holder.btnAccept.setText(donate.getF_status());




        holder.lnvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,ViewFoodDetailActivity.class);
                intent.putExtra("Donate", (Serializable) donate);
                mContext.startActivity(intent);
            }
        });

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
        private Button btnAccept;
        private LinearLayout lnvDetail;

        public DonateHolder(View itemView) {
            super(itemView);
            imgPRofile = (ImageView)itemView.findViewById( R.id.imgPRofile );
            txtName = (TextView)itemView.findViewById( R.id.txtName );
            txtDescription = (TextView)itemView.findViewById( R.id.txtDescription );
            txtQuantity = (TextView)itemView.findViewById( R.id.txtQuantity );
            txtDatePicker = (TextView)itemView.findViewById( R.id.txtDatePicker );
            btnAccept = (Button) itemView.findViewById(R.id.btnAccept);
            lnvDetail = (LinearLayout) itemView.findViewById(R.id.lnvDetail);

        }
    }

    private void doAccept(final int position,final  Button btn) {

        Toast.makeText(mContext,"Please Wait..",Toast.LENGTH_SHORT).show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("method","accept_food");
                map.put("user_id",appUtilty.getUserData().getU_ID());
                map.put("food_id",mDonates.get(position).getF_id());
                map.put("accepter_id",mDonates.get(position).getF_uid());


                final JSONObject result;

                result = JSONParser.doGetRequest(map, Constant.server);
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (result.getString("status").equals("true")) {

                                btn.setText("Send ");
                                btn.setBackgroundResource(R.color.Grren);
                                Toast.makeText(mContext,result.getString("message"),Toast.LENGTH_LONG).show();



                            }else {

                                Toast.makeText(mContext,result.getString("message"),Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }).start();
    }




}
