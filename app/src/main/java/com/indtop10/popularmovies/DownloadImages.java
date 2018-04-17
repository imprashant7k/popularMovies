package com.indtop10.popularmovies;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class DownloadImages extends AsyncTask<Void,Void,String> {

    private String response = null;
    private final String movieUrl;

    public interface AsyncResponse{

        void processFinish(String output);
    }

    private final AsyncResponse delegate;

    public DownloadImages(AsyncResponse delegate,String url)
    {
        this.delegate = delegate;
        this.movieUrl = url;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {

            URL url = new URL(movieUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            InputStream in = new BufferedInputStream(conn.getInputStream());

            response = convertToString(in);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private String convertToString(InputStream in)
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;

        try {

            while ((line = reader.readLine()) != null){
                sb.append(line).append('\n');

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        delegate.processFinish(response);
    }
}
