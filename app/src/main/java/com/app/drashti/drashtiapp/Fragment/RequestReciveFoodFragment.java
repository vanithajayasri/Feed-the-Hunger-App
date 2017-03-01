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

import com.app.drashti.drashtiapp.Adapter.RequestReciveAdapter;
import com.app.drashti.drashtiapp.AddDonateActivity;
import com.app.drashti.drashtiapp.R;
import com.app.drashti.drashtiapp.Model.Recive;
import com.app.drashti.drashtiapp.Utiliiyy.AppUtilty;
import com.app.drashti.drashtiapp.Utiliiyy.Constant;
import com.app.drashti.drashtiapp.Utiliiyy.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestReciveFoodFragment extends Fragment {

    private Button btnAddNewDonate;
    private RecyclerView recyclerDonate;
    private LinearLayoutManager linearLayoutManager;
    private RequestReciveAdapter reqestAdapter;
    private ArrayList<Recive> donates;
    private AppUtilty appUtilty;

    private TextView txtStatus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        appUtilty = new AppUtilty(getActivity());
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

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("method", "food_request_received");
                map.put("user_id",appUtilty.getUserData().getU_ID() );

                final JSONObject result = JSONParser.doGetRequest(map, Constant.server);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        donates = new ArrayList<Recive>();
                        try {
                            if (result.getString("status").equals("true")) {

                                JSONArray others_list = result.getJSONArray("data");

                                for (int i = 0; i < others_list.length(); i++) {

                                    JSONObject object = others_list.getJSONObject(i);
                                    Recive donate = new Recive();

                                    //FA_id":"12","FA_fid":"3","FA_aid":"12","FA_status":"Waiting","F_confirmRequest":"yes"}



                                    donate.setFA_aid(object.getString("FA_fid"));
                                    donate.setF_confirmRequest(object.getString("F_confirmRequest"));
                                    donate.setFA_status(object.getString("FA_status"));
                                    donate.setU_email(object.getString("U_email"));
                                    donate.setFA_fid(object.getString("F_uid"));
                                    donate.setU_firstname(object.getString("U_firstname"));
                                    donate.setU_lastname(object.getString("U_lastname"));
                                    donate.setU_phone(object.getString("U_phone"));
                                    donate.setU_Address(object.getString("FA_shipping_address"));
                                    donate.setU_Pincod(object.getString("FA_shipping_pincode"));
                                    donate.setFA_id(object.getString("FA_aid"));

                                    donates.add(donate);
                                }

                                reqestAdapter = new RequestReciveAdapter(getActivity(),donates);
                                recyclerDonate.setAdapter(reqestAdapter);
                                reqestAdapter.notifyDataSetChanged();
                                txtStatus.setVisibility(View.GONE);
                            } else {

                                txtStatus.setVisibility(View.VISIBLE);
                                txtStatus.setText(result.getString("message"));
                                //   Toast.makeText(getActivity(),result.getString("message"),Toast.LENGTH_LONG).show();
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
