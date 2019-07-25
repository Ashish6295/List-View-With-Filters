package com.example.listviewwithfilters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;


class ReadWriteOnLocalJson {

    private String readContent="";
    private String onlineJsonHolder;

    private Context context;

    ReadWriteOnLocalJson(String onlineJsonHolder, Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        this.context = context;
        this.onlineJsonHolder = onlineJsonHolder;

        boolean isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        isOnline isOnline = new isOnline();
        boolean onlineStatus = false;

        try {
            onlineStatus = isOnline.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean localFileAvailabilityStatus = (new File(context.getFilesDir().getAbsolutePath()+"/content.json").isFile());

        if (isConnected) {
            if (onlineStatus) {

                Toast toast = Toast.makeText(context, "Internet is available", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

                rwOperationOnJson(1);

            }else if (!localFileAvailabilityStatus){

                Toast toast = Toast.makeText(context, "Internet is required for the first time to create local file", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

            }else {
                Toast toast = Toast.makeText(context, "Connected to network but no internet, local Json loaded", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

                rwOperationOnJson(2);
            }
        }else if (!localFileAvailabilityStatus){

            Toast toast = Toast.makeText(context, "Internet is required for the first time to create local file", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

        } else {
            Toast toast = Toast.makeText(context,"Local Json Loaded",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,0,0);
            toast.show();

            rwOperationOnJson(2);
        }
    }

    String getReadContent() {
        return readContent;
    }

    private void rwOperationOnJson(int status){

        if (status == 1){
            writeToFile();
            readFromFile();
        }else {
            readFromFile();
        }

    }

    private void writeToFile(){

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(context.getFilesDir().getAbsolutePath() + "/content.json"));
            fileOutputStream.write(onlineJsonHolder.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readFromFile(){

        try {

            FileInputStream fileInputStream = new FileInputStream(context.getFilesDir().getAbsolutePath()+"/content.json");
            int size = fileInputStream.available();

            byte[] buffer = new byte[size];
            int no_bytes_read = fileInputStream.read(buffer);
            fileInputStream.close();

            readContent = new String(buffer, StandardCharsets.ISO_8859_1);

        }catch (IOException ex){
            ex.printStackTrace();
        }

    }

}


