package com.example.tanvidadu.learnit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dell on 12/8/2017.
 */

public class CatalogAdapter extends ArrayAdapter<String> {

    private ArrayList<String> Color = new ArrayList<String>();


    public CatalogAdapter(@NonNull Context context, @NonNull ArrayList<String> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        Color.add("catalog_color_1");
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.catalog_item, parent, false);
        }
        String currentString = getItem(position);
        TextView button = (TextView) listItemView.findViewById(R.id.catalog_item_display_button);
        button.setText(currentString);
        button.setTextColor(getContext().getResources().getColor(R.color.white));
        if( position%4 == 0 || position%4 == 3) {
            button.setBackgroundColor(getContext().getResources().getColor(R.color.catalog_color_2));
        } else {
            button.setBackgroundColor(getContext().getResources().getColor(R.color.catalog_color_3));
        }
        return listItemView;
    }
    public String getCatalog(int position){
        return getItem(position);
    }

}
