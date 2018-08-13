/*
 * Copyright 2018 Ahmed Mahmoud.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.devahmedm.storephones;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

/**
 * Created by Dev Ahmed Mahmoud on 25/5/2018
 * email : dev.ahmed.m@gmail.com
 * phone : +970597503338
 */

public class PhoneFormAdd extends AppCompatActivity {
    String base_url = MyApplication.base_url;
    Realm realm;
    Button chooseBn, add;
    EditText quantity, price, type;
    private ImageView imageview;
    Bitmap bitmap;
    final int IMG_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        imageview = findViewById(R.id.img);
        chooseBn = findViewById(R.id.chooseBn);
        quantity = findViewById(R.id.etquantity);
        price = findViewById(R.id.etprice);
        type = findViewById(R.id.ettype);
        add = findViewById(R.id.add);

        chooseBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNetworkConnection())
                    addOnline();
                else
                    addOffline();
            }
        });
    }

    private void addOffline() {
        //Check input.... and Warning
        if (imageview.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.no).getConstantState() || quantity.getText().toString().trim().equals("") || price.getText().toString().trim().equals("")
                || type.getText().toString().trim().equals("")) {
            if (imageview.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.no).getConstantState()) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.rootLayout), "Image is required!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            if (quantity.getText().toString().trim().equals(""))
                quantity.setError("Quantity is required!");
            if (price.getText().toString().trim().equals(""))
                price.setError("Price is required!");
            if (type.getText().toString().trim().equals(""))
                type.setError("Type in is required!");
        } else {
            // Add from Realm (Offline) local DB by set id = 0
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    Phone phone = realm.createObject(Phone.class);
                    // Save Data by set id = 0
                    phone.setId(0);
                    phone.setQuantity(Integer.parseInt(quantity.getText().toString()));
                    phone.setPrice(Double.parseDouble(price.getText().toString()));
                    phone.setType(type.getText().toString());
                    phone.setImageName("0");
                    phone.setDate_modified(Calendar.getInstance().getTime());

                    // Save Image
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    phone.setImage(byteArray);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Snackbar.make(findViewById(R.id.rootLayout), "Add Successfully Offline", Snackbar.LENGTH_LONG).show();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(@NonNull Throwable error) {
                    Snackbar.make(findViewById(R.id.rootLayout), error.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    private void addOnline() {
        //Check input.... and Warning
        if (imageview.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.no).getConstantState() || quantity.getText().toString().trim().equals("") || price.getText().toString().trim().equals("")
                || type.getText().toString().trim().equals("")) {
            if (imageview.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.no).getConstantState()) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.rootLayout), "Image is required!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            if (quantity.getText().toString().trim().equals(""))
                quantity.setError("Quantity is required!");
            if (price.getText().toString().trim().equals(""))
                price.setError("Price is required!");
            if (type.getText().toString().trim().equals(""))
                type.setError("Type in is required!");
        } else {
            // Add from Mysql (Online)
            String url_api_add = base_url + "API/api_add.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_api_add, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.rootLayout), jsonObject.getString("response"), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        // Empty Inputs
                        //imageview.setImageResource(R.drawable.no);
                        //quantity.setText("");
                        //price.setText("");
                        //type.setText("");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.rootLayout), "Error in connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> map = new HashMap<>();
                    map.put("quantity", quantity.getText().toString());
                    map.put("price", price.getText().toString());
                    map.put("type", type.getText().toString());
                    map.put("image", imageToString(bitmap));
                    return map;
                }
            };
            MySingleton.getInstance(getApplicationContext()).addToResquestQueue(stringRequest);
        }
    }

    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageview.setImageBitmap(bitmap);
                imageview.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
        // True if Connection
    }
}
