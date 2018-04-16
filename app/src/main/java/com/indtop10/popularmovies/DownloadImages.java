package com.indtop10.popularmovies;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class DownloadImages extends AsyncTask<Void,Void,String> {

    String reponse = null;
    String movieUrl = null;

    public interface AsyncResponse{

        void processFinish(String output);
    }

    public AsyncResponse delegate = null;

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

            reponse = convertToString(in);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reponse;
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

        delegate.processFinish(reponse);

    }
}
