package com.app.drashti.drashtiapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.drashti.drashtiapp.Model.Donate;
import com.app.drashti.drashtiapp.Utiliiyy.AppUtilty;
import com.app.drashti.drashtiapp.Utiliiyy.Constant;
import com.app.drashti.drashtiapp.Utiliiyy.JSONParser;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class EditFoodDetailActivity extends AppCompatActivity {
    private EditText edtFoodName;
    private EditText edtDiscription;
    private EditText edtQuantity,edtTime,edtPincode;
    private ImageView imgPhoto;
    private TextView txtDatePicker;
    private EditText edtDatePicker;
    private EditText edtAddress;
    private EditText edtInstraction;
    private Button btnUpdate;
    private Button btnDelete;
    private AppUtilty appUtilty;
    private String pick_up_date="";
    private static int RESULT_LOAD_IMAGE = 1;
    private ArrayList<String> imgPathArray;
    Intent intent;
    Donate donate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edtit_view_detail_activity);

        intent = getIntent();
        donate = (Donate) intent.getSerializableExtra("Donate");
        imgPathArray = new ArrayList<>();

        appUtilty = new AppUtilty(EditFoodDetailActivity.this);

        edtFoodName = (EditText) findViewById(R.id.edtFoodName);
        edtTime = (EditText) findViewById(R.id.edtTime);
        edtPincode = (EditText) findViewById(R.id.edtPincode);
        edtDiscription = (EditText) findViewById(R.id.edtDiscription);
        edtQuantity = (EditText) findViewById(R.id.edtQuantity);
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        txtDatePicker = (TextView) findViewById(R.id.txtDatePicker);
        edtDatePicker = (EditText) findViewById(R.id.edtDatePicker);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtInstraction = (EditText) findViewById(R.id.edtInstraction);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        pick_up_date = donate.getF_pickupDate();


        edtFoodName.setText(donate.getF_name());
        edtTime.setText(donate.getF_pickupTime());
        edtPincode.setText(donate.getF_pincode());
        edtDiscription.setText(donate.getF_description());
        edtDatePicker.setText(donate.getF_pickupDate());
        edtAddress.setText(donate.getF_address());
        edtInstraction.setText(donate.getF_instaruction());
        edtQuantity.setText(donate.getF_quantity());

        Picasso.with(EditFoodDetailActivity.this).load(donate.getF_image()).into(imgPhoto);

        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimeDialogue(EditFoodDetailActivity.this,edtTime);
            }
        });
        edtDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDatePickerDialogue(EditFoodDetailActivity.this,edtDatePicker);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (isValide()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("method", "edit_food");
                            map.put("user_id",appUtilty.getUserData().getU_ID());
                            map.put("food_id", donate.getF_id());
                            map.put("foodname", edtFoodName.getText().toString());
                            map.put("description", edtDiscription.getText().toString());
                            map.put("quantity", edtQuantity.getText().toString());
                            map.put("pickup_date", pick_up_date);
                            map.put("address", edtAddress.getText().toString());
                            map.put("instruction", edtInstraction.getText().toString());
                            map.put("pickup_time", edtTime.getText().toString());
                            map.put("pincode", edtPincode.getText().toString());

                            final JSONObject result;

                            result = JSONParser.doGetRequest(map, Constant.server);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        if (result.getString("status").equals("true")) {


                                            finish();

                                            Toast.makeText(EditFoodDetailActivity.this, result.getString("message"), Toast.LENGTH_LONG).show();


                                        } else {

                                            Toast.makeText(EditFoodDetailActivity.this, result.getString("message"), Toast.LENGTH_LONG).show();

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
        });




        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("method", "delete_food");
                        map.put("food_id", donate.getF_id());

                        final JSONObject result;

                        result = JSONParser.doGetRequest(map, Constant.server);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (result.getString("status").equals("true")) {


                                        finish();

                                        Toast.makeText(EditFoodDetailActivity.this, result.getString("message"), Toast.LENGTH_LONG).show();


                                    } else {

                                        Toast.makeText(EditFoodDetailActivity.this, result.getString("message"), Toast.LENGTH_LONG).show();

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                }).start();
            }
        });

    }

    public boolean isValide() {

        if (edtFoodName.getText().toString().equalsIgnoreCase("")) {
            makeToast("Please Enter Food name");
            return false;
        }else if (edtDiscription.getText().toString().equalsIgnoreCase("")) {
            makeToast("Please Enter Food Description");

            return false;
        } else if (edtQuantity.getText().toString().equalsIgnoreCase("")) {
            makeToast("Please Enter Food Quantity");

            return false;
        }else if (edtAddress.getText().toString().equalsIgnoreCase("")) {
            makeToast("Please Enter Address");

            return false;
        }else if (edtPincode.getText().toString().equalsIgnoreCase("")) {
            makeToast("Please Enter Pincode");

            return false;
        }else if (edtInstraction.getText().toString().equalsIgnoreCase("")) {
            makeToast("Please Enter Instruction");

            return false;
        }else if (edtTime.getText().toString().equalsIgnoreCase("")) {
            makeToast("Please Enter Time");

            return false;
        }else if (edtDatePicker.getText().toString().equalsIgnoreCase("")) {
            makeToast("Please Enter Date");

            return false;
        }/*else if (imgPathArray.size()==0) {
            makeToast("Please pick Image");

            return false;
        }*/else {
            return true;
        }
    }


    public void makeToast(String name) {
        Toast.makeText(EditFoodDetailActivity.this, name, Toast.LENGTH_LONG).show();
    }

    public void showDatePickerDialogue(Activity activity , final EditText edtText) {

        DatePickerDialog datePickerDialog = new DatePickerDialog();
        final Calendar now = Calendar.getInstance();
        datePickerDialog.setMinDate(now);

        datePickerDialog.setTitle("Time Track");

        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear , int dayOfMonth) {
                String date = (dayOfMonth) + "-" + (monthOfYear+1) + "-" + year+"";
                edtText.setText(date);
                pick_up_date =  year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
            }
        });
        datePickerDialog.show(activity.getFragmentManager(), "date_picker_dialogue");
    }

    public void showTimeDialogue(Activity activity , final EditText edtText) {

        final Calendar now = Calendar.getInstance();

        TimePickerDialog tpd = TimePickerDialog.newInstance(
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

                        String formate = "AM";
                        if (hourOfDay < 12){
                            formate = " PM";
                        }else {
                            formate = " AM";
                        }
                        edtText.setText(hourOfDay+":"+minute );
                    }
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );

        tpd.show(activity.getFragmentManager(), "Timepickerdialog");
    }


}




