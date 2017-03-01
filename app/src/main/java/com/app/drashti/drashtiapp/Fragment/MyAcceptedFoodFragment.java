package com.app.drashti.drashtiapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.drashti.drashtiapp.Adapter.MyAcceptedDonationAdapter;
import com.app.drashti.drashtiapp.AddDonateActivity;
import com.app.drashti.drashtiapp.Model.Donate;
import com.app.drashti.drashtiapp.R;
import com.app.drashti.drashtiapp.Utiliiyy.AppUtilty;
import com.app.drashti.drashtiapp.Utiliiyy.Constant;
import com.app.drashti.drashtiapp.Utiliiyy.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAcceptedFoodFragment extends Fragment {

    private Button btnAddNewDonate;
    private RecyclerView recyclerDonate;
    private LinearLayoutManager linearLayoutManager;
    private MyAcceptedDonationAdapter myDonateAdapter;
    private ArrayList<Donate> donates;
    TextView    txtStatus;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donate, container, false);

        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerDonate = (RecyclerView) view.findViewById(R.id.recyclerDonate);

        txtStatus = (TextView) view.findViewById(R.id.txtStatus);
        recyclerDonate.setLayoutManager(linearLayoutManager);
        btnAddNewDonate = (Button) view.findViewById(R.id.btnAddNewDonate);

        btnAddNewDonate.setVisibility(View.GONE);

        btnAddNewDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddDonateActivity.class);
                startActivity(intent);
            }
        });

        displayDonateData();
        return view;
    }

    public void displayDonateData() {
        txtStatus.setVisibility(View.VISIBLE);
        txtStatus.setText("Please Wait...");


        new Thread(new Runnable() {
            @Override
            public void run() {



                AppUtilty  appUtilty  = new AppUtilty(getActivity());
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("method", "my_accepted_food_list");
                map.put("user_id", appUtilty.getUserData().getU_ID());
                //  map.put("user_id","12");


                final JSONObject result = JSONParser.doGetRequest(map, Constant.server);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        donates = new ArrayList<Donate>();
                        try {
                            if (result.getString("status").equals("true")) {

                                JSONArray others_list = result.getJSONArray("data");

                                for (int i = 0; i < others_list.length(); i++) {

                                    JSONObject object = others_list.getJSONObject(i);
                                    Donate donate = new Donate();

                                    donate.setF_name(object.getString("F_name"));
                                    donate.setF_description(object.getString("F_description"));
                                    donate.setF_id(object.getString("F_id"));
                                    donate.setF_instaruction(object.getString("F_instruction"));
                                    donate.setF_address(object.getString("F_address"));
                                    donate.setF_quantity(object.getString("F_quantity"));
                                    donate.setF_pickupDate(object.getString("F_pickupDate"));
                                    donate.setF_image(object.getString("F_image"));
                                    donate.setF_status(object.getString("F_status"));
                                    donate.setF_pincode(object.getString("F_pincode"));
                                    donate.setF_uid(object.getString("F_uid"));
                                    donate.setF_pickupTime(object.getString("F_pickupTime"));
                                    donate.setF_acceptedRequest(object.optString("F_acceptedRequest"));

                                    donates.add(donate);
                                }

                                txtStatus.setVisibility(View.GONE);
                                myDonateAdapter = new MyAcceptedDonationAdapter(getActivity(),donates);
                                recyclerDonate.setAdapter(myDonateAdapter);
                                myDonateAdapter.notifyDataSetChanged();
                            } else {
                                //Toast.makeText(getActivity(),result.getString("message"),Toast.LENGTH_LONG).show();

                                txtStatus.setVisibility(View.VISIBLE);
                                txtStatus.setText(result.getString("message"));
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
