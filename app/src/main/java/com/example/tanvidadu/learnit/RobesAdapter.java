package com.example.tanvidadu.learnit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.tanvidadu.learnit.RequestSummary.decodeFromFirebaseBase64;

/**
 * Created by Dell on 12/7/2017.
 */

public class RobesAdapter extends ArrayAdapter<RobesForRent>  {
    private Context context;

    public RobesAdapter(@NonNull Context context, @NonNull ArrayList<RobesForRent> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.single_list_item_display, parent, false);
        }
        RobesForRent currentRobe = getItem(position);

        TextView textView = (TextView) listItemView.findViewById(R.id.List_item_name);
        textView.setText(currentRobe.getName());
        textView = (TextView)listItemView.findViewById(R.id.List_item_brand);
        textView.setText(currentRobe.getBrand());
        textView = (TextView) listItemView.findViewById(R.id.List_item_Price);
        textView.setText(Float.toString(currentRobe.getPrice()));
        ImageView imageView= (ImageView) listItemView.findViewById(R.id.List_item_Image);
        /*try {
            Bitmap imageBitmap = decodeFromFirebaseBase64(currentRobe.getImage_url());
            imageView.setImageBitmap(imageBitmap);
        } catch (IOException e) {
            e.printStackTrace();

        }*/
        Picasso.with(context).load(currentRobe.getUrl()).into(imageView);

        return listItemView;
    }

}
