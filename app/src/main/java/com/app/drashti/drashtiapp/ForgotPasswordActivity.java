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

public class ForgotPasswordActivity extends AppCompatActivity {



    private TextView txtFormName;
    private EditText edtPassword;
    private EditText edtEmail;
    private Button btnSubmit;




    private AppUtilty appUtilty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_passeord_activity);

        appUtilty = new AppUtilty(ForgotPasswordActivity.this);

        txtFormName = (TextView)findViewById( R.id.txtFormName );
        edtPassword = (EditText)findViewById( R.id.edtPassword );
        edtEmail = (EditText)findViewById( R.id.edtEmail );
        btnSubmit = (Button)findViewById( R.id.btnSubmit );

        btnSubmit.setOnClickListener(new View.OnClickListener() {
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
                map.put("method", "forget_password");
                map.put("email",edtEmail.getText().toString());



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
                                Toast.makeText(ForgotPasswordActivity.this, result.getString("message"), Toast.LENGTH_LONG).show();
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


        if (!edtEmail.getText().toString().matches(emailPattern)) {
            edtEmail.setError("Please enter a valid email address");

            return false;
        }
        return true;
    }







    public void makeToast(String name) {
        Toast.makeText(ForgotPasswordActivity.this, name, Toast.LENGTH_LONG).show();
    }
}


