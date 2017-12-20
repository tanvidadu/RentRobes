package com.example.tanvidadu.learnit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Dell on 12/21/2017.
 */

public class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

    public LoadImageTask(RobesAdapter listener) {

        mListener = (Listener) listener;
    }

    public interface Listener{

        void onClick(View view);

        void onImageLoaded(Bitmap bitmap);
        void onError();
    }

    private Listener mListener;
    @Override
    protected Bitmap doInBackground(String... args) {

        try {

            return BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if (bitmap != null) {

            mListener.onImageLoaded(bitmap);

        } else {

            mListener.onError();
        }
    }
}
