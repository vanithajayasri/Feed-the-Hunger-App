package com.app.drashti.drashtiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.drashti.drashtiapp.Model.Donate;
import com.app.drashti.drashtiapp.Utiliiyy.AppUtilty;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewFoodDetailActivity extends AppCompatActivity {
    private EditText edtFoodName;
    private EditText edtDiscription;
    private EditText edtQuantity,edtTime,edtPincode;
    private ImageView imgPhoto;
    private TextView txtDatePicker;
    private EditText edtDatePicker;
    private EditText edtAddress;
    private EditText edtInstraction;
    private EditText edtDonatedBy;
    private LinearLayout lnvDonatedBy;
    private Button btnSave;
    private AppUtilty appUtilty;
    private String pick_up_date;
    private static int RESULT_LOAD_IMAGE = 1;
    private ArrayList<String> imgPathArray;
    private EditText edtStaus;
    Intent intent;
    Donate donate;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_food_detail_activity);

        intent = getIntent();
        donate = (Donate) intent.getSerializableExtra("Donate");
        imgPathArray = new ArrayList<>();

        appUtilty = new AppUtilty(ViewFoodDetailActivity.this);

        edtFoodName = (EditText)findViewById( R.id.edtFoodName );
        edtStaus = (EditText) findViewById(R.id.edtStaus);
        edtDonatedBy = (EditText) findViewById(R.id.edtDonatedBy);

        lnvDonatedBy = (LinearLayout) findViewById(R.id.lnvDonatedBy);


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


        edtFoodName.setText(donate.getF_name());
        edtTime.setText(donate.getF_pickupTime());
        edtPincode.setText(donate.getF_pincode());
        edtDiscription.setText(donate.getF_description());
        edtDatePicker.setText(donate.getF_pickupDate());
        edtAddress.setText(donate.getF_address());
        edtInstraction.setText(donate.getF_instaruction());
        edtQuantity.setText(donate.getF_quantity());
        edtStaus.setText(donate.getF_status());

        Picasso.with(ViewFoodDetailActivity.this).load(donate.getF_image()).into(imgPhoto);

        if (donate.getF_USerLastNAme()!=null && donate.getF_UserFirstName()!=null){

            lnvDonatedBy.setVisibility(View.VISIBLE);
            edtDonatedBy.setText(donate.getF_UserFirstName()+" "+donate.getF_USerLastNAme());
        }



    }

}




