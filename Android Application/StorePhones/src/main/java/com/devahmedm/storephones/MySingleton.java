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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Dev Ahmed Mahmoud on 25/5/2018
 * email : dev.ahmed.m@gmail.com
 * phone : +970597503338
 */

public class MySingleton {
    @SuppressLint("StaticFieldLeak")
    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    private MySingleton(Context context) {
        mContext = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public <T> void addToResquestQueue(Request<T> request) {
        requestQueue.add(request);
    }
}
