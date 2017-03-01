package com.app.drashti.drashtiapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.drashti.drashtiapp.Utiliiyy.AppUtilty;
import com.app.drashti.drashtiapp.Utiliiyy.Constant;
import com.app.drashti.drashtiapp.Utiliiyy.JSONParser;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddDonateActivity extends AppCompatActivity {
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
    private String encodedString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donate);

        imgPathArray = new ArrayList<>();
        appUtilty = new AppUtilty(AddDonateActivity.this);
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

        edtTime.setFocusable(false);
        edtDatePicker.setFocusable(false);
        edtDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogue(AddDonateActivity.this,edtDatePicker);
            }
        });

        edtAddress.setText(appUtilty.getUserData().getU_address());
        edtPincode.setText(appUtilty.getUserData().getU_pincode());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValide() == true){
                    doAddProduct();

                }
            }
        });

        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimeDialogue(AddDonateActivity.this,edtTime);
            }
        });
        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);*/

            }
        });



    }

    public void doAddProduct(){

        Toast.makeText(AddDonateActivity.this,"Please Wait...",Toast.LENGTH_LONG).show();



        new Thread(new Runnable() {
            @Override
            public void run() {


                HashMap<String,String> map = new HashMap<String, String>();

                map.put("method","imageupload");
                map.put("id","1");
                map.put("method","add_food");
                map.put("user_id",appUtilty.getUserData().getU_ID());
                map.put("foodname",edtFoodName.getText().toString());
                map.put("description",edtDiscription.getText().toString());
                map.put("quantity",edtQuantity.getText().toString());
                map.put("pickup_date",pick_up_date);
                map.put("address",edtAddress.getText().toString());
                map.put("instruction",edtInstraction.getText().toString());
                map.put("pickup_time",edtTime.getText().toString());
                map.put("pincode",edtPincode.getText().toString());

                // map.put("food_image",encodedString);

              /*  map.put("method","updateprofile");
                map.put("id","1");
                map.put("first_name","chirag");
                map.put("last_name","rami");
                map.put("location","4");
                map.put("birthdate","2016-08-10");


                String server = "http://dragonwebsol.com/workspace/reward-point/api/api.php?";
*/
                final JSONObject result;
                result = JSONParser.doGetRequest( map , Constant.server );

                Log.e("Result,",result+"");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (result.getString("status").equals("true")){



                                finish();

                            }else {
                                Toast.makeText(AddDonateActivity.this,result.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        }).start();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            imgPathArray.clear();
            imgPathArray.add(picturePath);
            Log.e("Image PAth",picturePath);

            InputStream inputStream = null;//You can get an inputStream using any IO API
            try {
                inputStream = new FileInputStream(picturePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            byte[] bytes;
            byte[] buffer = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            bytes = output.toByteArray();
            encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);

            Log.e("Encode------------ ", encodedString);



            cursor.close();
            Picasso.with(this).load(new File(picturePath)).into(imgPhoto);


            //imgProfile.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


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
        Toast.makeText(AddDonateActivity.this, name, Toast.LENGTH_LONG).show();
    }
}




