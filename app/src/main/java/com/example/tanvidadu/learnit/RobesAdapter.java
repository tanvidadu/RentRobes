package com.example.tanvidadu.learnit;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.tanvidadu.learnit.RequestSummary.decodeFromFirebaseBase64;

/**
 * Created by Dell on 12/7/2017.
 */

public class RobesAdapter extends ArrayAdapter<Robes> {

    public RobesAdapter(@NonNull Context context, @NonNull ArrayList<Robes> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.single_list_item_display, parent, false);
        }
        Robes currentRobe = getItem(position);

        TextView textView = (TextView) listItemView.findViewById(R.id.List_item_name);
        textView.setText(currentRobe.getName_of_product());
        textView = (TextView)listItemView.findViewById(R.id.List_item_brand);
        textView.setText(currentRobe.getBrand());
        textView = (TextView) listItemView.findViewById(R.id.List_item_Price);
        textView.setText(Float.toString(currentRobe.getCost_price()));
        /*ImageView imageView= (ImageView) listItemView.findViewById(R.id.List_item_Image);
        try {
            Bitmap imageBitmap = decodeFromFirebaseBase64(currentRobe.getImage_url());
            imageView.setImageBitmap(imageBitmap);
        } catch (IOException e) {
            e.printStackTrace();

        }*/
        return listItemView;
    }
}
