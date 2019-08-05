package com.example.listviewwithfilters;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetJsonFromUrl extends AsyncTask<String, Void, String> {

    private static final String REQUEST_METHOD = "GET";
    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECTION_TIMEOUT = 10000;

    @Override
    protected String doInBackground(String... strings) {

        String stringUrl = strings[0];
        String result ="";

        URL url;
        HttpURLConnection connection =null;

        try {

            url = new URL(stringUrl);
            connection =(HttpURLConnection)url.openConnection();

            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            connection.connect();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line;

            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line).append("\n");
            }

            bufferedReader.close();
            result = stringBuilder.toString();

        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (connection != null){

                try {
                    connection.disconnect();
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        }

        return result;

    }

}
