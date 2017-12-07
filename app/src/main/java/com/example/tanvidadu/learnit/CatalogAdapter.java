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

    public CatalogAdapter(@NonNull Context context, @NonNull ArrayList<String> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.catalog_item, parent, false);
        }
        String currentString = getItem(position);
        TextView button = (TextView) listItemView.findViewById(R.id.catalog_item_display_button);
        button.setText(currentString);
        return listItemView;
    }
    public String getCatalog(int position){
        return getItem(position);
    }

}
