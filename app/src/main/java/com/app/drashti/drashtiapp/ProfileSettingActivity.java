package com.app.drashti.drashtiapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.drashti.drashtiapp.Model.User;
import com.app.drashti.drashtiapp.Utiliiyy.AppUtilty;
import com.app.drashti.drashtiapp.Utiliiyy.Constant;
import com.app.drashti.drashtiapp.Utiliiyy.JSONParser;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProfileSettingActivity extends Activity {

    private EditText edtFirstName, edtLastName, edtEmailId, edtPassword, edtPhone, edtAddress, edtPincode;
    private Button btnSignUp , txtLogOut;
    private TextView txtFormName,txtChangePassword;
    private AppUtilty appUtilty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        appUtilty = new AppUtilty(ProfileSettingActivity.this);

        edtFirstName = (EditText) findViewById(R.id.edtFirstName);
        edtLastName = (EditText) findViewById(R.id.edtLastName);
        edtEmailId = (EditText) findViewById(R.id.edtEmailId);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtPincode = (EditText) findViewById(R.id.edtPincode);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        txtFormName = (TextView) findViewById(R.id.txtFormName);
        txtChangePassword = (TextView) findViewById(R.id.txtChangePassword);
        txtLogOut = (Button) findViewById(R.id.txtLogOut);


        edtFirstName.setText(appUtilty.getUserData().getU_firstname());
        edtLastName.setText(appUtilty.getUserData().getU_lastname());
        edtPincode.setText(appUtilty.getUserData().getU_pincode());
        edtAddress.setText(appUtilty.getUserData().getU_address());
        edtEmailId.setText(appUtilty.getUserData().getU_email());
        edtPhone.setText(appUtilty.getUserData().getU_phone());

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Valide() == true) {



                    doRegistration(appUtilty.getUserData().getU_type());


                }
            }
        });

        txtChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileSettingActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        txtLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(ProfileSettingActivity.this)
                        .setTitle("LOGOUT")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                appUtilty.setUserData(null);
                                LoginManager.getInstance().logOut();
                                Intent intent = new Intent(ProfileSettingActivity.this,LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

    }

    public void doRegistration(final String type) {






        makeToast("Please Wait.....");

        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("method", "edit_profile");
                map.put("user_id",appUtilty.getUserData().getU_ID());
                map.put("firstname", edtFirstName.getText().toString());
                map.put("lastname", edtLastName.getText().toString());
                map.put("phone", edtPhone.getText().toString());
                map.put("address", edtAddress.getText().toString());
                map.put("pincode", edtPincode.getText().toString());

                final JSONObject result;


                result = JSONParser.doGetRequest(map,Constant.server) ;

                Log.e("Result,", result + "");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (result.getString("status").equals("true")) {


                                User user = appUtilty.getUserData();
                                user.setU_email(edtEmailId.getText().toString());
                                user.setU_firstname(edtFirstName.getText().toString());
                                user.setU_lastname(edtLastName.getText().toString());
                                user.setU_phone(edtPhone.getText().toString());
                                user.setU_address(edtAddress.getText().toString());
                                user.setU_pincode(edtPincode.getText().toString());
                                user.setU_ID(appUtilty.getUserData().getU_ID());
                                user.setU_type(appUtilty.getUserData().getU_type());

                                appUtilty.setUserData(user);

                                makeToast(result.getString("message"));

                                edtFirstName.setText(appUtilty.getUserData().getU_firstname());
                                edtLastName.setText(appUtilty.getUserData().getU_lastname());
                                edtPincode.setText(appUtilty.getUserData().getU_pincode());
                                edtAddress.setText(appUtilty.getUserData().getU_address());
                                edtEmailId.setText(appUtilty.getUserData().getU_email());
                                edtPhone.setText(appUtilty.getUserData().getU_phone());


                                finish();

                            } else {
                                Toast.makeText(ProfileSettingActivity.this, result.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }).start();
    }


    public boolean Valide() {
        String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (edtFirstName.getText().toString().equalsIgnoreCase("")) {
            makeToast("Please Enter FirstName");
            return false;
        } else if (edtLastName.getText().toString().equalsIgnoreCase("")) {
            makeToast("Please Enter LastName");
            return false;
        } else if (!edtEmailId.getText().toString().matches(emailPattern)) {
            edtEmailId.setError("Please enter a valid email address");

            return false;
        } else if (edtPhone.getText().toString().equalsIgnoreCase("")) {
            makeToast("Please enter phone number");
            return false;
        } else if (edtAddress.getText().toString().equalsIgnoreCase("")) {
            makeToast("Please Enter Address");
            return false;
        } else if (edtPincode.getText().toString().length() < 5) {
            makeToast("Please Enter valide pincode");
            return false;
        }


        return true;
    }


    public void makeToast(String name) {
        Toast.makeText(ProfileSettingActivity.this, name, Toast.LENGTH_LONG).show();
    }
}
