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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dev Ahmed Mahmoud on 25/5/2018
 * email : dev.ahmed.m@gmail.com
 * phone : +9700597503338
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Phone> Phones;

    MyAdapter(Context context, List<Phone> Phones) {
        this.context = context;
        this.Phones = Phones;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public int getCount() {
        return Phones.size();
    }

    @Override
    public Object getItem(int position) {
        return Phones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            myView = inflater.inflate(R.layout.phone_view, parent, false);
            ViewHolder vh = new ViewHolder();

            vh.idText = myView.findViewById(R.id.id);
            vh.phoneImage = myView.findViewById(R.id.img);
            vh.priceText = myView.findViewById(R.id.pri);

            myView.setTag(vh);
        } else
            myView = convertView;


        Phone phone = Phones.get(position);
        ViewHolder vh = (ViewHolder) myView.getTag();

        vh.idText.setText(phone.getId() + "");
        byte[] data = phone.getImage();
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        vh.phoneImage.setImageBitmap(bmp);
        vh.priceText.setText(Double.toString(phone.getPrice()) + "$");

        return myView;
    }

    private class ViewHolder {
        TextView idText;
        ImageView phoneImage;
        TextView priceText;
    }
}
