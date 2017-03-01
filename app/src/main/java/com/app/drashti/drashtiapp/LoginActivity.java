package com.app.drashti.drashtiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.app.drashti.drashtiapp.Model.User;
import com.app.drashti.drashtiapp.Utiliiyy.AppUtilty;
import com.app.drashti.drashtiapp.Utiliiyy.Constant;
import com.app.drashti.drashtiapp.Utiliiyy.JSONParser;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnRegister;
    private ImageView imgLoginWithGoogle;
    private LoginButton imgLoginWithFB;
    private CallbackManager callbackManager;
    private ProfileTracker mProfileTracker;

    private SignInButton signInButton;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 100;

    private TextView textViewName;
    private TextView textViewEmail;
    private NetworkImageView profilePhoto;
    AppUtilty appUtilty;
    private ImageLoader imageLoader;
    private TextView txtForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        imgLoginWithFB = (LoginButton) findViewById(R.id.imgLoginWithFB);

        appUtilty = new AppUtilty(LoginActivity.this);
        if (appUtilty.getUserData()!=null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
            finish();
        }else {

        }

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Valide()==true) {
                    doLogin(edtEmail.getText().toString(), edtPassword.getText().toString(), "normal");
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                intent.putExtra("whichType","normal");
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                finish();
            }
        });

        imgLoginWithFB.setReadPermissions("user_friends");

        imgLoginWithFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            Log.v("facebook - profile", profile2.getFirstName());
                            Log.v("facebook - profile", profile2.getId());
                            Log.v("facebook - profile", profile2.getMiddleName());
                            Log.v("facebook - profile", profile2.getLinkUri() + "");
                            Log.v("facebook - profile", profile2.getProfilePictureUri(500, 500) + "");
                            Log.v("facebook - profile", profile2.getName());

                            mProfileTracker.stopTracking();

                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();

                            // doLogin(profile2.getName()+"@gmail.com","123456","social");

                            Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
                            intent.putExtra("whichType","social");
                            intent.putExtra("FirstName",profile2.getFirstName());
                            intent.putExtra("lastName",profile2.getLastName());
                            startActivity(intent);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);

                        }
                    };

                } else {
                    Profile profile = Profile.getCurrentProfile();
                    Log.v("facebook - profile", profile.getFirstName());
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });



        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        profilePhoto = (NetworkImageView) findViewById(R.id.profileImage);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton.setOnClickListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

            textViewName.setText(acct.getDisplayName());
            textViewEmail.setText(acct.getEmail());

            imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                    .getImageLoader();

            imageLoader.get(acct.getPhotoUrl().toString(),
                    ImageLoader.getImageListener(profilePhoto,
                            R.mipmap.ic_launcher,
                            R.mipmap.ic_launcher));

            profilePhoto.setImageUrl(acct.getPhotoUrl().toString(), imageLoader);

            Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);

            intent.putExtra("FirstName" , acct.getDisplayName() );
            intent.putExtra("UserId" ,acct.getEmail() );
            intent.putExtra("GID" ,acct.getId() );
            intent.putExtra("whichType","social");
            intent.putExtra("lastName",acct.getDisplayName());
            intent.putExtra("Email",acct.getEmail());
            intent.putExtra("whichType","social");
            doLogin(acct.getEmail(),"123456","social");

            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);


        } else {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == signInButton) {
            signIn();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void doLogin(final String email, final String password, final String type){
        makeToast("Please Wait.....");

        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("method","login");
                map.put("email",email);
                map.put("password",password);
                map.put("type",type);

                final JSONObject result;

                result = JSONParser.doGetRequest(map, Constant.server);
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

                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);

                                finish();
                                //startActivity(intent);

                            }else {

                                makeToast(result.getString("message"));
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
        } else if (edtPassword.getText().toString().length() < 4) {
            edtPassword.setError("Password must be minimum 4 char. long");
            return false;
        } else {
            return true;

        }


    }

    public void makeToast(String name) {
        Toast.makeText(LoginActivity.this, name, Toast.LENGTH_LONG).show();
    }


}