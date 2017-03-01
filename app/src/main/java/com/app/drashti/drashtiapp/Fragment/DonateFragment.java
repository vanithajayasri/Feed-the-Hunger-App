package com.app.drashti.drashtiapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.drashti.drashtiapp.AddDonateActivity;
import com.app.drashti.drashtiapp.Utiliiyy.AppUtilty;
import com.app.drashti.drashtiapp.Utiliiyy.Constant;
import com.app.drashti.drashtiapp.Model.Donate;
import com.app.drashti.drashtiapp.Adapter.DonateAdapter;
import com.app.drashti.drashtiapp.Utiliiyy.JSONParser;
import com.app.drashti.drashtiapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;


public class DonateFragment extends Fragment {

    private Button btnAddNewDonate;
    private RecyclerView recyclerDonate;
    private LinearLayoutManager linearLayoutManager;
    private DonateAdapter donateAdapter;
    private ArrayList<Donate> donates;
    private AppUtilty appUtilty;
    private TextView txtStatus;

    private Button btnSearch;
    private EditText edtSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donate, container, false);

        appUtilty = new AppUtilty(getActivity());

        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerDonate = (RecyclerView) view.findViewById(R.id.recyclerDonate);
        txtStatus = (TextView) view.findViewById(R.id.txtStatus);

        edtSearch = (EditText) view.findViewById(R.id.edtSearchPin);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);

        edtSearch.setVisibility(View.VISIBLE);
        btnSearch.setVisibility(View.VISIBLE);
        recyclerDonate.setLayoutManager(linearLayoutManager);
        btnAddNewDonate = (Button) view.findViewById(R.id.btnAddNewDonate);

        edtSearch.setText(appUtilty.getUserData().getU_pincode());

        btnAddNewDonate.setVisibility(View.GONE);

        btnAddNewDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddDonateActivity.class);
                startActivity(intent);
            }
        });

        displayDonateData(edtSearch.getText().toString());

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayDonateData(edtSearch.getText().toString());

            }
        });

        return view;
    }

    public void displayDonateData(final String pincode) {
        txtStatus.setVisibility(View.VISIBLE);
        txtStatus.setText("Please Wait...");

        new Thread(new Runnable() {
            @Override
            public void run() {


                HashMap<String, String> map = new HashMap<String, String>();
                AppUtilty appUtilty =new AppUtilty(getActivity());
                map.put("method", "food_list");
                map.put("user_id", appUtilty.getUserData().getU_ID());
                map.put("pincode", pincode);


                final JSONObject result = JSONParser.doGetRequest(map, Constant.server);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        donates = new ArrayList<Donate>();
                        donates.clear();
                        try {
                            if (result.getString("status").equals("true")) {
                                JSONObject data = result.getJSONObject("data");


                                JSONArray others_list = data.getJSONArray("others_list");

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
                                    donate.setF_pincode(object.getString("F_pincode"));
                                    donate.setF_pickupTime(object.getString("F_pickupTime"));
                                    donate.setF_status(object.getString("F_status"));
                                    donate.setF_requestSent(object.getString("F_requestSent"));
                                    donate.setF_UserFirstName(object.getString("U_firstname"));
                                    donate.setF_USerLastNAme(object.getString("U_lastname"));

                                    txtStatus.setVisibility(View.GONE);

                                    donates.add(donate);
                                }

                                Collections.reverse(donates);

                                Log.e("Donate Sixe",donates.size()+"");
                                if (donates.size() == 0){
                                    txtStatus.setVisibility(View.VISIBLE);
                                    txtStatus.setText("No Search Found");
                                }else {

                                }

                                donateAdapter = new DonateAdapter(getActivity(), donates);
                                recyclerDonate.setAdapter(donateAdapter);
                                donateAdapter.notifyDataSetChanged();

                            } else {

                                //txtStatus.setVisibility(View.VISIBLE);
                                //txtStatus.setText(result.getString("txtStatus"));
                                Toast.makeText(getActivity(),result.getString("message"),Toast.LENGTH_LONG).show();



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
