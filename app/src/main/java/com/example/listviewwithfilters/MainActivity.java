package com.example.listviewwithfilters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ListView listViewChapterTitle;
    Spinner spinnerDropdown;

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

        listViewChapterTitle = findViewById(R.id.listViewOfChapterTitles);

        List<DataModelListViewChaptersList> chapterTitleObjectList = new ArrayList<>();

        List<DataModelSpinnerAsFilter> selectedStateAndTitleForSpinner = new ArrayList<>();

        spinnerDropdown = findViewById(R.id.spinnerPlaceHolder);
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

        for(String s:filterKeywords.getSearchTagKeywords()){

            if (!s.equals(""))
                listOfFilterParameters.add(s);

        }

        chapterTitleObjectList = filterKeywords.getListOfChaptersAndKeywords();

        CustomAdapterListViewListOfTitle adapter = new CustomAdapterListViewListOfTitle(this, R.layout.list_item_chapter_title_list_view, chapterTitleObjectList);
        listViewChapterTitle.setAdapter(adapter);

        for (int i = 0; i < listOfFilterParameters.size(); i++){

            DataModelSpinnerAsFilter spinnerAsFilter = new DataModelSpinnerAsFilter();
            spinnerAsFilter.setCheckBoxTitle(listOfFilterParameters.get(i));

            spinnerAsFilter.setSelected(false);

            selectedStateAndTitleForSpinner.add(spinnerAsFilter);

        }

        CustomAdapterSpinner spinnerAdapter = new CustomAdapterSpinner(this,R.layout.spinner_checkbox_items,selectedStateAndTitleForSpinner);
        spinnerDropdown.setAdapter(spinnerAdapter);

    }

}