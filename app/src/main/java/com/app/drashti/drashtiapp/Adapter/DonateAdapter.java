package com.app.drashti.drashtiapp.Adapter;

        import android.app.Activity;
        import android.app.Dialog;
        import android.content.Intent;
        import android.graphics.drawable.ColorDrawable;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.app.drashti.drashtiapp.AcceptViewFoodDetailActivity;
        import com.app.drashti.drashtiapp.Fragment.DonateFragment;
        import com.app.drashti.drashtiapp.MainActivity;
        import com.app.drashti.drashtiapp.Model.Donate;
        import com.app.drashti.drashtiapp.R;
        import com.app.drashti.drashtiapp.Utiliiyy.AppUtilty;
        import com.app.drashti.drashtiapp.Utiliiyy.Constant;
        import com.app.drashti.drashtiapp.Utiliiyy.JSONParser;
        import com.app.drashti.drashtiapp.ViewFoodDetailActivity;
        import com.squareup.picasso.Picasso;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.Serializable;
        import java.util.ArrayList;
        import java.util.HashMap;


public class DonateAdapter extends RecyclerView.Adapter<DonateAdapter.DonateHolder> {

    public Activity mContext;
    public ArrayList<Donate> mDonates;
    String address="";
    String status="Pick up";
    String pincode="";
    AppUtilty appUtilty;

    CheckBox checkMyLocation;
    CheckBox checkNewLocation;
    CheckBox checkPickUp;
    EditText edtAddress;
    CheckBox checkneedVolunteer;
    EditText edtPincode;
    TextView txtStatus;
    Button btnSubmit;

    public DonateAdapter(Activity mContext, ArrayList<Donate> mDonates) {
        this.mContext = mContext;
        appUtilty = new AppUtilty(mContext);
        this.mDonates = mDonates;

    }

    @Override
    public DonateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_donate, parent, false);
        return new DonateHolder(view);
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }



    @Override
    public void onBindViewHolder(final DonateHolder holder, final int position) {

        final Donate donate = mDonates.get(position);

        Picasso.with(mContext).load(donate.getF_image()).into(holder.imgPRofile);
        holder.txtName.setText(donate.getF_name());
        holder.txtDescription.setText(donate.getF_description());
        holder.txtQuantity.setText("Quantity : " + donate.getF_quantity());
        holder.txtDatePicker.setText(donate.getF_pickupDate());
        holder.txtDescription.setVisibility(View.VISIBLE);
        holder.txtDescription.setText("Donated By : " +donate.getF_UserFirstName() +" "+donate.getF_USerLastNAme());

        if (donate.getF_requestSent().equalsIgnoreCase("Yes")) {
            holder.btnAccept.setText(" Request Sent ");
            holder.btnAccept.setBackgroundResource(R.color.Grren);

        } else {

            holder.btnAccept.setText("Accept");

            holder.btnAccept.setBackgroundResource(R.color.colorPrimary);
            holder.btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showDialogue(position,holder.btnAccept);

                }
            });

        }

        holder.lnvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, AcceptViewFoodDetailActivity.class);
                intent.putExtra("Donate", (Serializable) donate);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDonates.size();
    }


    public class DonateHolder extends RecyclerView.ViewHolder {

        private ImageView imgPRofile;
        private TextView txtName;
        private TextView txtDescription;
        private TextView txtQuantity;
        private TextView txtDatePicker;
        private Button btnAccept;
        private LinearLayout lnvDetail;

        public DonateHolder(View itemView) {
            super(itemView);
            imgPRofile = (ImageView) itemView.findViewById(R.id.imgPRofile);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtQuantity = (TextView) itemView.findViewById(R.id.txtQuantity);
            txtDatePicker = (TextView) itemView.findViewById(R.id.txtDatePicker);
            btnAccept = (Button) itemView.findViewById(R.id.btnAccept);
            lnvDetail = (LinearLayout) itemView.findViewById(R.id.lnvDetail);

        }
    }

    private void doAccept(final int position, final Button btn, final Dialog dialog) {

        if (checkMyLocation.isChecked() == true){
            address  = appUtilty.getUserData().getU_address();
            pincode  = appUtilty.getUserData().getU_pincode();

        }else if (checkNewLocation.isChecked() == true){
            address = edtAddress.getText().toString();
            pincode = edtPincode.getText().toString();
        }else if (checkneedVolunteer.isChecked() == true){

            status = "Need Volunteer";
        }
        else if (checkPickUp.isChecked()==true){
            status = "Pick up";
        }


        if (address.equalsIgnoreCase("")){
            Toast.makeText(mContext, "Please Enter Address..", Toast.LENGTH_SHORT).show();

        }else  if (pincode.equalsIgnoreCase("")){
            Toast.makeText(mContext, "Please Enter Pincode", Toast.LENGTH_SHORT).show();

        }else {

            Toast.makeText(mContext, "Please Wait..", Toast.LENGTH_SHORT).show();

            Log.e("Status", status);
            Log.e("Address", address);
            Log.e("Pincode", pincode);

            new Thread(new Runnable() {
                @Override
                public void run() {

                    HashMap<String, String> map = new HashMap<String, String>();
                /*status=Pickup&shipping_address=USA&shipping_pincode=380001*/
                    map.put("method", "ask_to_accept_food");
                    map.put("user_id", appUtilty.getUserData().getU_ID());
                    map.put("food_id", mDonates.get(position).getF_id());
                    map.put("status", status);
                    map.put("shipping_address", address);
                    map.put("shipping_pincode", pincode);


                    final JSONObject result;

                    result = JSONParser.doGetRequest(map, Constant.server);
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (result.getString("status").equals("true")) {


                                    MainActivity mainActivityc = (MainActivity) mContext;
                                    mainActivityc.donateFragment.displayDonateData(appUtilty.getUserData().getU_pincode());
                                    dialog.dismiss();




                                    //  donateFragment.displayDonateData();
                                    //  notifyItemInserted(position);

                                /*    Donate donate = mDonates.get(position);
                                    donate.setF_status("Yes");
                                    mDonates.add(position,donate);
                                    notifyDataSetChanged();
*/
                                    //   mDonates.set(position,mDonates.get(position).setF_status("Yes"));
                                    btn.setBackgroundResource(R.color.Grren);

                                    Toast.makeText(mContext, result.getString("message"), Toast.LENGTH_LONG).show();


                                } else {

                                    Toast.makeText(mContext, result.getString("message"), Toast.LENGTH_LONG).show();

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


    public void showDialogue(final int position, final Button btn) {




        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialogue_accept);

        checkMyLocation = (CheckBox) dialog.findViewById(R.id.checkMyLocation);
        checkNewLocation = (CheckBox) dialog.findViewById(R.id.checkNewLocation);
        edtAddress = (EditText) dialog.findViewById(R.id.edtAddress);
        checkPickUp = (CheckBox) dialog.findViewById(R.id.checkPickUp);
        checkneedVolunteer = (CheckBox) dialog.findViewById(R.id.checkneedVolunteer);
        btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
        edtPincode = (EditText) dialog.findViewById(R.id.edtPincode);

        checkMyLocation.setChecked(true);
        checkPickUp.setChecked(true);

        checkNewLocation.setChecked(false);
        checkneedVolunteer.setChecked(false);

        edtAddress.setText(appUtilty.getUserData().getU_address());
        edtPincode.setText(appUtilty.getUserData().getU_pincode());


        edtAddress.setEnabled(false);
        edtPincode.setEnabled(false);


        checkMyLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                if (isChecked == true){

                    edtAddress.setText(appUtilty.getUserData().getU_address());
                    edtPincode.setText(appUtilty.getUserData().getU_pincode());
                    address = appUtilty.getUserData().getU_address();
                    pincode = appUtilty.getUserData().getU_pincode();
                    checkMyLocation.setChecked(true);
                    checkNewLocation.setChecked(false);

                    edtAddress.setEnabled(false);
                    edtPincode.setEnabled(false);

                }else {


                    if (checkNewLocation.isChecked() == false){
                        checkMyLocation.setChecked(true);
                    }else {

                        edtAddress.setEnabled(true);
                        edtPincode.setEnabled(true);
                        checkMyLocation.setChecked(false);

                        edtAddress.setText("");
                        edtPincode.setText("");


                        address = edtAddress.getText().toString();
                        pincode = edtPincode.getText().toString();
                    }
                }

            }
        });

        checkNewLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked == true){


                    edtAddress.setEnabled(true);
                    edtPincode.setEnabled(true);

                    edtAddress.setText("");
                    edtPincode.setText("");
                    checkNewLocation.setChecked(true);
                    checkMyLocation.setChecked(false);
                    address = edtAddress.getText().toString();
                    pincode = edtPincode.getText().toString();

                }else {

                    if (checkMyLocation.isChecked() == false){
                        checkNewLocation.setChecked(true);
                    }else {

                        edtAddress.setEnabled(false);
                        edtPincode.setEnabled(false);

                        edtAddress.setText(appUtilty.getUserData().getU_address());
                        edtPincode.setText(appUtilty.getUserData().getU_pincode());
                        checkNewLocation.setChecked(false);
                        address = appUtilty.getUserData().getU_address();
                        pincode = appUtilty.getUserData().getU_pincode();


                        edtAddress.setText(appUtilty.getUserData().getU_address());
                        edtPincode.setText(appUtilty.getUserData().getU_pincode());
                    }
                }

            }
        });

        checkPickUp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked == true){
                    status = "Pick up";
                    checkPickUp.setChecked(true);
                    checkneedVolunteer.setChecked(false);

                }else   if (checkneedVolunteer.isChecked() == false){
                    checkPickUp.setChecked(true);
                }else{


                    checkPickUp.setChecked(false);
                    status = "Need Volunteer";
                }

            }
        });
        checkneedVolunteer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked == true){
                    status = "Need Volunteer";
                    checkneedVolunteer.setChecked(true);
                    checkPickUp.setChecked(false);


                }else if (checkPickUp.isChecked() == false){
                    checkneedVolunteer.setChecked(true);
                }else {
                    checkneedVolunteer.setChecked(false);


                    status = "Pick up";
                }

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (address.equalsIgnoreCase(" ") && status.equalsIgnoreCase(" ")&&pincode.equalsIgnoreCase(" ")){
                    Toast.makeText(mContext,"Please Fill All Detail",Toast.LENGTH_LONG).show();
                }else {
                    doAccept(position,btn,dialog);
                }



            }
        });



        dialog.show();


    }


}
