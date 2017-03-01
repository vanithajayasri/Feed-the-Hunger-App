package com.app.drashti.drashtiapp.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.drashti.drashtiapp.MainActivity;
import com.app.drashti.drashtiapp.R;
import com.app.drashti.drashtiapp.Model.Recive;
import com.app.drashti.drashtiapp.Utiliiyy.AppUtilty;
import com.app.drashti.drashtiapp.Utiliiyy.Constant;
import com.app.drashti.drashtiapp.Utiliiyy.JSONParser;
import com.app.drashti.drashtiapp.ViewFoodDetailActivityWithID;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestReciveAdapter extends RecyclerView.Adapter<RequestReciveAdapter.DonateHolder> {

    public Activity mContext;
    public ArrayList<Recive> mDonates;
    AppUtilty appUtilty;
    MainActivity mainActivity;

    public RequestReciveAdapter(Activity mContext, ArrayList<Recive> mDonates) {
        this.mContext = mContext;
        appUtilty = new AppUtilty(mContext);
        this.mDonates = mDonates;
        mainActivity = (MainActivity) mContext;
    }

    @Override
    public DonateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_request_recive, parent, false);
        return new DonateHolder(view);
    }

    @Override
    public void onBindViewHolder(final DonateHolder holder, final int position) {

        final Recive recive = mDonates.get(position);

        holder.txtName.setText(recive.getU_firstname() +" " + recive.getU_lastname());
        holder.txtAddress.setText("Phone No : "+recive.getU_phone() );
        holder.txtEmail.setText("E-mail : "+recive.getU_email() );
        holder.txtAddress.setText("Address : "+recive.getU_Address() +", Pincode : "+recive.getU_Pincod() );

        if (recive.getFA_status().equalsIgnoreCase("Rejected")){
            holder.btnReject.setText("Rejected");
            holder.btnReject.setVisibility(View.VISIBLE);
            holder.btnStaus.setVisibility(View.GONE);

        }else if (recive.getFA_status().equalsIgnoreCase("Accepted"))
        {

            holder.btnStaus.setText("Accepted");
            holder.btnStaus.setVisibility(View.VISIBLE);
            holder.btnReject.setVisibility(View.GONE);

        } else if (recive.getFA_status().equalsIgnoreCase("Need Volunteer")) {
            holder.btnStaus.setText("Accept");

            holder.btnStaus.setVisibility(View.VISIBLE);
            holder.btnReject.setVisibility(View.VISIBLE);

            holder.btnStaus.setBackgroundResource(R.color.colorPrimary);

            holder.btnStaus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doAccept(position, holder.btnStaus);

                    holder.btnReject.setVisibility(View.GONE);
                    holder.btnStaus.setText("Accepted");

                }
            });

            holder.btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    doReject(position, holder.btnReject);
                }
            });



        } else {
            holder.btnReject.setVisibility(View.VISIBLE);
            holder.btnStaus.setVisibility(View.VISIBLE);

            holder.btnStaus.setText("Accept");

            holder.btnStaus.setBackgroundResource(R.color.colorPrimary);
            holder.btnStaus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doAccept(position, holder.btnStaus);

                    holder.btnReject.setVisibility(View.GONE);
                    holder.btnStaus.setText("Accepted");

                }
            });

            //holder.btnReject.setVisibility(View.VISIBLE);

            holder.btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    doReject(position, holder.btnReject);
                }
            });
        }

        holder.lnvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ViewFoodDetailActivityWithID.class);
                intent.putExtra("Receive", recive );
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDonates.size();
    }

    public class DonateHolder extends RecyclerView.ViewHolder {

        LinearLayout lnvDetail;
        private TextView txtName;
        private TextView txtDescription;
        private TextView txtAddress;
        private TextView txtEmail;
        private Button btnStaus;
        private Button btnReject;

        public DonateHolder(View itemView) {
            super(itemView);

            txtName = (TextView)itemView.findViewById( R.id.txtName );
            txtDescription = (TextView)itemView.findViewById( R.id.txtDescription );
            txtAddress = (TextView)itemView.findViewById( R.id.txtAddress );
            txtEmail = (TextView)itemView.findViewById( R.id.txtEmail );
            btnStaus = (Button)itemView.findViewById( R.id.btnStaus );
            btnReject = (Button) itemView.findViewById(R.id.btnReject);

            lnvDetail = (LinearLayout) itemView.findViewById(R.id.lnvDetail);

        }
    }

    private void doAccept(final int position, final Button btn) {

        Toast.makeText(mContext, "Please Wait..", Toast.LENGTH_SHORT).show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("method", "accept_food");
                map.put("user_id", mDonates.get(position).getFA_id());
                map.put("food_id", mDonates.get(position).getFA_aid());
                map.put("accepter_id",appUtilty.getUserData().getU_ID());
                map.put("status","Accepted");

                final JSONObject result;

                result = JSONParser.doGetRequest(map, Constant.server);
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (result.getString("status").equals("true")) {

                             /*   btn.setText("Accepted");
                                btn.setBackgroundResource(R.color.Grren);
*/

                                mainActivity.requestReciveFoodFragment.displayDonateData();
                                Toast.makeText(mContext, result.getString("message"), Toast.LENGTH_LONG).show();


                            } else {

                                Toast.makeText(mContext, result.getString("message"), Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }).start();
    }

    private void doReject(final int position, final Button btn) {

        Toast.makeText(mContext, "Please Wait..", Toast.LENGTH_SHORT).show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("method", "reject_food");
                map.put("user_id", mDonates.get(position).getFA_id());
                map.put("food_id", mDonates.get(position).getFA_aid());
                map.put("status","Rejected");

                final JSONObject result;

                result = JSONParser.doGetRequest(map, Constant.server);
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (result.getString("status").equals("true")) {

                              /*  btn.setText("Rejected");
                                btn.setBackgroundResource(R.color.Red);

                                mDonates.remove(position);
                                notifyDataSetChanged();
*/
                                mainActivity.requestReciveFoodFragment.displayDonateData();
                                Toast.makeText(mContext, result.getString("message"), Toast.LENGTH_LONG).show();

                            } else {

                                Toast.makeText(mContext, result.getString("message"), Toast.LENGTH_LONG).show();

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
