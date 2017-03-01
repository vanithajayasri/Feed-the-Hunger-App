package com.app.drashti.drashtiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.drashti.drashtiapp.Model.Donate;
import com.app.drashti.drashtiapp.Model.Recive;
import com.app.drashti.drashtiapp.Utiliiyy.AppUtilty;
import com.app.drashti.drashtiapp.Utiliiyy.Constant;
import com.app.drashti.drashtiapp.Utiliiyy.JSONParser;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewFoodDetailActivityWithID extends AppCompatActivity {
    private EditText edtFoodName;
    private EditText edtDiscription;
    private EditText edtQuantity,edtTime,edtPincode;
    private ImageView imgPhoto;
    private TextView txtDatePicker;
    private EditText edtDatePicker;
    private EditText edtAddress;
    private EditText edtInstraction;
    private Button btnSave;
    private AppUtilty appUtilty;
    private String pick_up_date;
    private static int RESULT_LOAD_IMAGE = 1;
    private ArrayList<String> imgPathArray;
    Intent intent;
    Donate donate;
    EditText edtStaus;
    Recive recive;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_food_detail_activity);

        intent = getIntent();
        recive = (Recive) intent.getSerializableExtra("Receive");

        appUtilty = new AppUtilty(ViewFoodDetailActivityWithID.this);

        edtFoodName = (EditText)findViewById( R.id.edtFoodName );
        edtTime = (EditText) findViewById(R.id.edtTime);
        edtPincode = (EditText) findViewById(R.id.edtPincode);
        edtDiscription = (EditText)findViewById( R.id.edtDiscription );
        edtQuantity = (EditText)findViewById( R.id.edtQuantity );
        imgPhoto = (ImageView)findViewById( R.id.imgPhoto );
        txtDatePicker = (TextView)findViewById( R.id.txtDatePicker );
        edtDatePicker = (EditText)findViewById( R.id.edtDatePicker );
        edtAddress = (EditText)findViewById( R.id.edtAddress );
        edtInstraction = (EditText)findViewById( R.id.edtInstraction );
        btnSave = (Button)findViewById( R.id.btnSave );
        edtStaus = (EditText) findViewById(R.id.edtStaus);

        displayDonateData();




    }

    public void displayDonateData() {

        new Thread(new Runnable() {
            @Override
            public void run() {


                HashMap<String, String> map = new HashMap<String, String>();

                map.put("method", "view_food_details");
                map.put("food_id", recive.getFA_aid());



                final JSONObject result = JSONParser.doGetRequest(map, Constant.server);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            if (result.getString("status").equals("true")) {
                                JSONObject object = result.getJSONObject("data");





                                Donate donate = new Donate();



                                donate.setF_name(object.getString("F_name"));
                                donate.setF_description(object.getString("F_description"));
                                donate.setF_instaruction(object.getString("F_instruction"));
                                donate.setF_address(object.getString("F_address"));
                                donate.setF_quantity(object.getString("F_quantity"));
                                donate.setF_pickupDate(object.getString("F_pickupDate"));
                                donate.setF_image(object.getString("F_image"));
                                donate.setF_pincode(object.getString("F_pincode"));
                                donate.setF_pickupTime(object.getString("F_pickupTime"));
                                donate.setF_status(object.getString("F_status"));





                                edtFoodName.setText(donate.getF_name());
                                edtTime.setText(donate.getF_pickupTime());
                                edtPincode.setText(donate.getF_pincode());
                                edtDiscription.setText(donate.getF_description());
                                edtDatePicker.setText(donate.getF_pickupDate());
                                edtAddress.setText(donate.getF_address());
                                edtInstraction.setText(donate.getF_instaruction());
                                edtQuantity.setText(donate.getF_quantity());
                                edtStaus.setText(donate.getF_status());

                                Picasso.with(ViewFoodDetailActivityWithID.this).load(donate.getF_image()).into(imgPhoto);



                            }
                            else {
                                Toast.makeText(ViewFoodDetailActivityWithID.this,result.getString("message"),Toast.LENGTH_LONG).show();



                            }


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }



}




