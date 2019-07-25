package com.example.listviewwithfilters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ListView listViewChapterTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//-----------------------Declarations----------------------------------------------------------------------
        String url = "https://s3-eu-west-1.amazonaws.com/bbi.appsdata.2013/guideline-cdiff.json";
        String onlineJsonHolder ="";

        GetJsonFromUrl jsonFromUrl = new GetJsonFromUrl();

        String jsonStringforSearchTagKeywords;

        ArrayList<String>listOfFilterParameters = new ArrayList<String>();
        ArrayList<String>listOfChapterTitle = new ArrayList<String>();

        listViewChapterTitle = findViewById(R.id.listViewOfChapterTitles);
//---------------------------------------------------------------------------------------------------------
        try {
            onlineJsonHolder = jsonFromUrl.execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ReadWriteOnLocalJson readWriteOperationOnLocalJson = new ReadWriteOnLocalJson(onlineJsonHolder,this);
        jsonStringforSearchTagKeywords = readWriteOperationOnLocalJson.getReadContent();

        FilterKeyWordsExtractionFromJson filterKeywords = new FilterKeyWordsExtractionFromJson(jsonStringforSearchTagKeywords);

        for(String s:filterKeywords.getListOfChapterTitle()){

            if (!s.equals("\n"))
                listOfChapterTitle.add(s);

        }

        for(String s:filterKeywords.getSearchTagKeywords()){

            if (!s.equals(""))
                listOfFilterParameters.add(s);

        }



    }

}