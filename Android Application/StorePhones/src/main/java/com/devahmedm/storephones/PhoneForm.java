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
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Dev Ahmed Mahmoud on 25/5/2018
 * email : dev.ahmed.m@gmail.com
 * phone : +9700597503338
 */

public class PhoneForm extends AppCompatActivity {
    private static int TIME_OUT = 3500; //Time to launch the Main Activity
    String base_url = MyApplication.base_url;
    Realm realm;
    String id;
    ImageView imageview;
    Button chooseBn, delete, edit, buy;
    EditText quantity, price, type;
    Bitmap bitmap;
    final int IMG_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        imageview = findViewById(R.id.img);
        chooseBn = findViewById(R.id.chooseBn);
        quantity = findViewById(R.id.etquantity);
        price = findViewById(R.id.etprice);
        type = findViewById(R.id.ettype);
        delete = findViewById(R.id.delete);
        edit = findViewById(R.id.edit);
        buy = findViewById(R.id.buy);

        id = getIntent().getStringExtra("id");

        // Fetch data from Realm DB
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                RealmResults<Phone> result = realm.where(Phone.class).equalTo("id", Integer.parseInt(id))
                        .findAll();
                for (Phone phone : result) {
                    quantity.setText(String.valueOf(phone.getQuantity()));
                    price.setText(String.valueOf(phone.getPrice()));
                    type.setText(phone.getType());
                    byte[] data = phone.getImage();
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    imageview.setImageBitmap(bitmap);
                }
            }
        });

        chooseBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNetworkConnection())
                    update_Online();
                else
                    update_Offline();
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNetworkConnection())
                    buyPhone();
                else
                    Snackbar.make(findViewById(R.id.rootLayout), R.string.offline_message, Snackbar.LENGTH_LONG).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNetworkConnection())
                    deleteOnline();
                else
                    deleteOffline();
            }
        });
    }

    public void update_Offline() {
        //Check input.... and Warning
        if (quantity.getText().toString().trim().equals("") || price.getText().toString().trim().equals("")
                || type.getText().toString().trim().equals("")) {
            if (quantity.getText().toString().trim().equals(""))
                quantity.setError("Quantity is required!");
            if (price.getText().toString().trim().equals(""))
                price.setError("Price is required!");
            if (type.getText().toString().trim().equals(""))
                type.setError("Type in is required!");
        } else {
            // Update from Realm (Offline) local DB
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    RealmResults<Phone> result = realm.where(Phone.class).equalTo("id", Integer.parseInt(id))
                            .findAll();
                    result.get(0).setQuantity(Integer.parseInt(quantity.getText().toString()));
                    result.get(0).setPrice(Double.parseDouble(price.getText().toString()));
                    result.get(0).setType(type.getText().toString());
                    // Save Image
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    result.get(0).setImage(byteArray);
                    // Save Date Modified
                    // new java.util.Date();
                    // Date currentTime = Calendar.getInstance().getTime();
                    result.get(0).setDate_modified(Calendar.getInstance().getTime());

                    // Update Data by set id = -id
                    result.get(0).setId(-Integer.parseInt(id));
                }
            });
            Snackbar.make(findViewById(R.id.rootLayout), "Update Successfully Offline", Snackbar.LENGTH_LONG).show();
        }
    }

    public void update_Online() {
        //Check input.... and Warning
        if (quantity.getText().toString().trim().equals("") || price.getText().toString().trim().equals("")
                || type.getText().toString().trim().equals("")) {
            if (quantity.getText().toString().trim().equals(""))
                quantity.setError("Quantity is required!");
            if (price.getText().toString().trim().equals(""))
                price.setError("Price is required!");
            if (type.getText().toString().trim().equals(""))
                type.setError("Type in is required!");
        } else {
            // Update from Mysql (Online) & Update from Realm (Offline) local DB
            String url_api_edit = base_url + "API/api_edit.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_api_edit, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        // Update.... response
                        Snackbar.make(findViewById(R.id.rootLayout), jsonObject.getString("response"), Snackbar.LENGTH_LONG).show();
                        if (jsonObject.getString("response").equals("Update Successfully")) {
                            // Update from Realm (Offline) local DB
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(@NonNull Realm realm) {
                                    RealmResults<Phone> result = realm.where(Phone.class).equalTo("id", Integer.parseInt(id))
                                            .findAll();
                                    // Update Data
                                    result.get(0).setQuantity(Integer.parseInt(quantity.getText().toString()));
                                    result.get(0).setPrice(Double.parseDouble(price.getText().toString()));
                                    result.get(0).setType(type.getText().toString());
                                    // Update Image
                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                    byte[] byteArray = stream.toByteArray();
                                    result.get(0).setImage(byteArray);
                                }
                            });
                        }
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
                    map.put("id", id);
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

    public void buyPhone() {
        //Decrement Quantity -1 from Mysql (Online) & Decrement Quantity -1 from Realm (Offline) local DB
        String url_api_buy = base_url + "API/api_buy.php?id=" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_api_buy, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    // Buy.... response
                    Snackbar.make(findViewById(R.id.rootLayout), jsonObject.getString("response"), Snackbar.LENGTH_LONG).show();
                    //Decrement Quantity -1 from Realm (Offline) local DB
                    if (jsonObject.getString("response").equals("Buy Successfully")) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(@NonNull Realm realm) {
                                RealmResults<Phone> result = realm.where(Phone.class).equalTo("id", Integer.parseInt(id))
                                        .findAll();
                                if (result.get(0).getQuantity() > 0)
                                    result.get(0).setQuantity(result.get(0).getQuantity() - 1);
                            }
                        });
                        quantity.setText(String.valueOf((((Integer.parseInt(quantity.getText().toString())) - 1) < 0) ? 0 : Integer.parseInt(String.valueOf(quantity.getText())) - 1));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Snackbar.make(findViewById(R.id.rootLayout), "Error in connection", Snackbar.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(getApplicationContext()).addToResquestQueue(jsonObjectRequest);
    }

    public void deleteOffline() {
        // Delete from Realm (Offline) local DB by set quantity = -1
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                RealmResults<Phone> result = realm.where(Phone.class).equalTo("id", Integer.parseInt(id))
                        .findAll();
                result.get(0).setQuantity(-1);
            }
        });
        imageview.setImageResource(R.drawable.no);
        quantity.setText("");
        price.setText("");
        type.setText("");
        Snackbar.make(findViewById(R.id.rootLayout), "Delete Successfully Offline", Snackbar.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, TIME_OUT);
    }

    public void deleteOnline() {
        //Delete from Mysql (Online) & Delete from Realm (Offline) local DB
        String url_api_delete = base_url + "API/api_delete.php?id=" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_api_delete, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    // Delete.... response
                    Snackbar.make(findViewById(R.id.rootLayout), jsonObject.getString("response"), Snackbar.LENGTH_LONG).show();
                    // Delete from Realm (Offline) local DB
                    if (jsonObject.getString("response").equals("Delete Successfully")) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(@NonNull Realm realm) {
                                RealmResults<Phone> result = realm.where(Phone.class).equalTo("id", Integer.parseInt(id))
                                        .findAll();
                                result.deleteAllFromRealm();
                            }
                        });
                        imageview.setImageResource(R.drawable.no);
                        quantity.setText("");
                        price.setText("");
                        type.setText("");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, TIME_OUT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Snackbar.make(findViewById(R.id.rootLayout), "Error in connection", Snackbar.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(getApplicationContext()).addToResquestQueue(jsonObjectRequest);
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
