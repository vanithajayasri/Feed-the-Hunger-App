package com.app.drashti.drashtiapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.drashti.drashtiapp.Utiliiyy.AppUtilty;
import com.app.drashti.drashtiapp.Utiliiyy.Constant;
import com.app.drashti.drashtiapp.Utiliiyy.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextView txtFormName;
    private EditText edtEmailId;
    private EditText edtPassword;
    private EditText edtOldPasswoed;
    private EditText edtNewPassword;
    private EditText edtRepetPassword;
    private Button btnChangePassword;

    private AppUtilty appUtilty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        appUtilty = new AppUtilty(ChangePasswordActivity.this);

        txtFormName = (TextView)findViewById( R.id.txtFormName );
        edtEmailId = (EditText)findViewById( R.id.edtEmailId );
        edtPassword = (EditText)findViewById( R.id.edtPassword );
        edtOldPasswoed = (EditText)findViewById( R.id.edtOldPasswoed );
        edtNewPassword = (EditText)findViewById( R.id.edtNewPassword );
        edtRepetPassword = (EditText)findViewById( R.id.edtRepetPassword );
        btnChangePassword = (Button)findViewById( R.id.btnChangePassword );


        edtEmailId.setText(appUtilty.getUserData().getU_email());

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Valide()){
                    doChangePassword();
                }
            }
        });


    }

    public void doChangePassword(){

        makeToast("Please Wait.....");



        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("method", "change_password");
                map.put("old_password",edtOldPasswoed.getText().toString());
                map.put("new_password",edtNewPassword.getText().toString());
                map.put("user_id",appUtilty.getUserData().getU_ID());




                final JSONObject result;


                result = JSONParser.doGetRequest(map, Constant.server);

                Log.e("Result,", result + "");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (result.getString("status").equals("true")) {




                                makeToast(result.getString("message"));

                                finish();

                            } else {
                                Toast.makeText(ChangePasswordActivity.this, result.getString("message"), Toast.LENGTH_LONG).show();
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


        if (edtOldPasswoed.getText().toString().length() < 4) {
            makeToast("Old Password must be minimum 4 char. long");
            return false;
        } else if (edtNewPassword.getText().toString().length() < 4) {
            makeToast("New Password must be minimum 4 char. long");
            return false;
        }else if (edtRepetPassword.getText().toString().length() < 4) {
            makeToast("Repet Pssword must be minimum 4 char. long");
            return false;
        }else if (!edtNewPassword.getText().toString().equalsIgnoreCase(edtRepetPassword.getText().toString())) {
        makeToast("New Password does not match with repet password ");
        return false;
    }



        return true;
    }


    public void makeToast(String name) {
        Toast.makeText(ChangePasswordActivity.this, name, Toast.LENGTH_LONG).show();
    }
}


