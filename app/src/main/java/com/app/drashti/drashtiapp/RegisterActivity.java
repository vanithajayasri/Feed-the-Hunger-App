package com.app.drashti.drashtiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtFirstName, edtLastName, edtEmailId, edtPassword, edtPhone, edtAddress, edtPincode;
    private Button btnSignUp;
    private TextView txtFormName;
    private AppUtilty appUtilty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        appUtilty = new AppUtilty(RegisterActivity.this);

        edtFirstName = (EditText) findViewById(R.id.edtFirstName);
        edtLastName = (EditText) findViewById(R.id.edtLastName);
        edtEmailId = (EditText) findViewById(R.id.edtEmailId);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtPincode = (EditText) findViewById(R.id.edtPincode);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        txtFormName = (TextView) findViewById(R.id.txtFormName);

        final Intent intent = getIntent();

        if (intent != null) {
            if (intent.getStringExtra("whichType").equalsIgnoreCase("social")) {
                String FirstName = intent.getStringExtra("FirstName");
                String LastName = intent.getStringExtra("LastName");

                edtFirstName.setText(FirstName);
                edtLastName.setText(LastName);
                txtFormName.setText("Please complete your registration by filling the form");
                btnSignUp.setText("Done");

            }
        }

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Valide() == true) {

                    if (intent.getStringExtra("whichType").equalsIgnoreCase("social")) {
                        doRegistration("social");

                    } else {
                        doRegistration("normal");

                    }
                }

            }
        });

    }

    public void doRegistration(final String type) {

        makeToast("Please Wait.....");

        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("method", "registration");
                map.put("firstname", edtFirstName.getText().toString());
                map.put("lastname", edtLastName.getText().toString());
                map.put("email", edtEmailId.getText().toString());
                map.put("password", edtPassword.getText().toString());
                map.put("phone", edtPhone.getText().toString());
                map.put("address", edtAddress.getText().toString());
                map.put("pincode", edtPincode.getText().toString());
                map.put("type", type);

                final JSONObject result;


                result = JSONParser.doGetRequest(map, Constant.server);

                Log.e("Result,", result + "");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (result.getString("status").equals("true")) {

                                JSONObject data = result.getJSONObject("data");
                                User user = new User();
                                user.setU_ID(data.getString("U_id"));
                                user.setU_firstname(data.getString("U_firstname"));
                                user.setU_lastname(data.getString("U_lastname"));
                                user.setU_email(data.getString("U_email"));
                                user.setU_password(data.getString("U_password"));
                                user.setU_phone(data.getString("U_phone"));
                                user.setU_address(data.getString("U_address"));
                                user.setU_pincode(data.getString("U_pincode"));
                                user.setU_type(data.getString("U_type"));

                                appUtilty.setUserData(user);

                                Intent intent1 = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent1);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);

                                makeToast(result.getString("message"));

                            } else {
                                Toast.makeText(RegisterActivity.this, result.getString("message"), Toast.LENGTH_LONG).show();
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
        } else if (edtPassword.getText().toString().length() < 4) {
            edtPassword.setError("Password must be minimum 4 char. long");
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
        Toast.makeText(RegisterActivity.this, name, Toast.LENGTH_LONG).show();
    }
}
