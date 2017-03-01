package com.app.drashti.drashtiapp.Utiliiyy;

import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class JSONParser {

    private static String TAG = "API PARSING URL";


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    public static JSONObject doGetRequest(HashMap<String, String> param, String url) {
        JSONObject result = null;

        String TAG="GET";
        String response;
        Set keys = param.keySet();

        int count = 0;
        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            count++;
            String key = (String) i.next();
            String value = (String) param.get(key);
            if (count == param.size()) {
                Log.e(TAG, "KEY "+key + "");
                Log.e(TAG,"VALUE "+ value + "");
                url += key + "=" + URLEncoder.encode(value);

            } else {
                Log.e("KEY ", key + "");
                Log.e(TAG,"VALUE "+ value + "");

                url += key + "=" + URLEncoder.encode(value) + "&";
            }

        }
/*
        try {
            url=  URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        Log.e("URL ",url);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response responseClient = null;
        try {


            responseClient = client.newCall(request).execute();
            response = responseClient.body().string();
            result = new JSONObject(response);
            Log.e("RESPONSE ", response +"" );
        } catch (Exception e) {
            Log.e( "Other Error: ",  e.getMessage());
            result = new JSONObject();

            try {
                result.put("status", "false");
                result.put("message", "Please Check Your Network Connection !!!");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }


        }

        return result;

    }






    public static JSONObject doPostRequest(HashMap<String, String> data,String url, ArrayList<String> imageList, String fileParamName) {

        try {
            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

            Log.e(TAG,"Method");
            RequestBody requestBody;
            MultipartBuilder mBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);

            for (String key : data.keySet()) {
                String value = data.get(key);
                Log.e(TAG,"Key Values "+key + "-----------------" + value);

                mBuilder.addFormDataPart(key, value);

            }
            if(imageList!=null) {
                for (int i = 0; i < imageList.size(); i++) {
                    File f = new File(imageList.get(i));

                    Log.e(TAG,"File Name" + f.getName() + "===========");
                    if (f.exists()) {
                        Log.e(TAG,"File Exits===================");
                        mBuilder.addFormDataPart(fileParamName, f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
                    }
                }
            }
            requestBody = mBuilder.build();


            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();


            String result = response.body().string();

            Log.e(TAG,"Response" +result + "");
            return new JSONObject(result);

        }  catch (Exception e) {
            Log.e("Other Error: " , e.getMessage());
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("status", "false");
                jsonObject.put("message", "Please Check Your Network Connection !!!");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

            Log.e(TAG,"==================================***===================================");
            return jsonObject;

        }

    }



    public static JSONObject doPostRequest(String url,HashMap<String, String> data) {

        try {
            RequestBody requestBody;
            MultipartBuilder mBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);

            if (data != null) {


                for (String key : data.keySet()) {
                    String value = data.get(key);
                    Log.e("Key Values", key + "-----------------" + value);

                    mBuilder.addFormDataPart(key, value);

                }
            } else {
                mBuilder.addFormDataPart("temp", "temp");
            }
            requestBody = mBuilder.build();


            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            Log.e("URL", url);
            Log.e("Response", responseBody);
            return new JSONObject(responseBody);

        } catch (UnknownHostException | UnsupportedEncodingException e) {

            JSONObject jsonObject=new JSONObject();

            try {
                jsonObject.put("status","false");
                jsonObject.put("message",e.getLocalizedMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.e("ERROR", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jsonObject=new JSONObject();

            try {
                jsonObject.put("status","false");
                jsonObject.put("message",e.getLocalizedMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.e("OTHER ERROR", "Other Error: " + e.getLocalizedMessage());
        }
        return null;
    }


}

