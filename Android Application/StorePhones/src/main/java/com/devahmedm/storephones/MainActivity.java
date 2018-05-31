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
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Dev Ahmed Mahmoud on 25/5/2018
 * email : dev.ahmed.m@gmail.com
 * phone : +9700597503338
 */

public class MainActivity extends AppCompatActivity {
    Realm realm;
    private RealmChangeListener realmListener;
    GridView gridView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Realm Configuration
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        // Send Intent to MyService to Start Synchronization Data
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        Calendar c = Calendar.getInstance();
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 123, intent, 0);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert manager != null;
        manager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 60000, pendingIntent);

        // Swipe Refresh Layout and Action
        final SwipeRefreshLayout SwipeRefresh = findViewById(R.id.SwipeRefresh);
        SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(@NonNull Realm realm) {
                        RealmResults<Phone> results = realm.where(Phone.class).findAll();
                        myAdapter = new MyAdapter(getBaseContext(), results);
                        gridView.setAdapter(myAdapter);
                        SwipeRefresh.setRefreshing(false);
                    }
                });
            }
        });

        // Realm Change Listener
        realmListener = new RealmChangeListener() {
            @Override
            public void onChange(@NonNull Object o) {
                myAdapter.notifyDataSetChanged();
            }
        };
        realm.addChangeListener(realmListener);


        // GridView and OnItemClickListener
        gridView = findViewById(R.id.items);
        registerForContextMenu(gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView id = view.findViewById(R.id.id);
                Intent intent = new Intent(getApplicationContext(), PhoneForm.class);
                intent.putExtra("id", id.getText().toString());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                RealmResults<Phone> results = realm.where(Phone.class).findAll();
                myAdapter = new MyAdapter(getBaseContext(), results);
                gridView.setAdapter(myAdapter);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the listener.
        realm.removeChangeListener(realmListener);
        // Close the Realm instance.
        realm.close();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflater Menu in Action bar......
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action, menu);
        MenuBuilder m = (MenuBuilder) menu;
        m.setOptionalIconsVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Menu in Action bar Add and Async
        switch (item.getItemId()) {
            case R.id.menueadd:
                Intent intentAdd = new Intent(getApplicationContext(), PhoneFormAdd.class);
                startActivity(intentAdd);
                return true;

            case R.id.async:
                if (checkNetworkConnection()) {
                    // Send Intent to MyService to Start Synchronization Data
                    Intent intentAsync = new Intent(getApplicationContext(), MyService.class);
                    startService(intentAsync);
                    Toast.makeText(getApplicationContext(), "Data Synchronization", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
        // True if Connection
    }
}
