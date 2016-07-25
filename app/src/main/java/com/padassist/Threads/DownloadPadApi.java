package com.padassist.Threads;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Thomas on 7/12/2015.
 */
public class DownloadPadApi extends Thread {
    @Override
    public void run() {
        super.run();
        try {
            //   'source' is the URL of the image
            URL url = new URL("https://www.padherder.com/api/active_skills/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //   Value of stale indicates how old the cache content can be
            //   before using it. Here the value is 1 hour(in secs)
            conn.addRequestProperty("Cache-Control", "max-stale=" + 60 * 60);

            //   Indicating the connection to use caches
            conn.setUseCaches(true);

            //   Using the stream to download the image
            InputStream stream = conn.getInputStream();
            Log.d("stream", "Stream: " + stream);
            Bitmap image = BitmapFactory.decodeStream(stream);
            stream.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
