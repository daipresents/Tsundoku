package com.daipresents.tsundoku.book;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by daipr on 2016/12/11.
 */
public class BookImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = BookImageAsyncTask.class.getSimpleName();
    private ImageView imageView;
    private Context context;

    public BookImageAsyncTask(Context context, ImageView imageView) {
        this.imageView = imageView;
        this.context = context;
    }

    protected Bitmap doInBackground(String... params) {
        Log.v(TAG, "doInBackground: start");

        Bitmap bitmap = null;
        InputStream is = null;

        synchronized (context){
            try {
                String imageURLString = params[0];
                Log.v(TAG, "doInBackground: imageURLString: " + imageURLString);

                URL imageURL = new URL(imageURLString);
                is = imageURL.openStream();
                bitmap = BitmapFactory.decodeStream(is);

                Log.v(TAG, "doInBackground: Downloaded as bitmap.");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            return bitmap;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(bitmap != null){
            Log.v(TAG, "onPostExecute: Set bitmap.");
            imageView.setImageBitmap(bitmap);
        }
    }
}
