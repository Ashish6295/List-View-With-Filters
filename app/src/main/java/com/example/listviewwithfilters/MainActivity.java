package com.example.listviewwithfilters;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements FilterListView{

    ListView listViewChapterTitle;
    Spinner spinnerDropdown;

    List<DataModelListViewChaptersList> chapterTitleObjectList;

    CustomAdapterListViewListOfTitle adapter;
    CheckedItemListSpinner checkedItemListSpinner;

    FilterKeyWordsExtractionFromJson filterKeywords;

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

        List<DataModelSpinnerAsFilter> selectedStateAndTitleForSpinner = new ArrayList<>();

        checkedItemListSpinner = new CheckedItemListSpinner();

        spinnerDropdown = findViewById(R.id.spinnerPlaceHolder);
        //Button btnApplyFilter = findViewById(R.id.buttonApplyFilter);
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

        filterKeywords = new FilterKeyWordsExtractionFromJson(jsonStringforSearchTagKeywords);

        chapterTitleObjectList = filterKeywords.getListOfChaptersAndKeywords();

        adapter = new CustomAdapterListViewListOfTitle(this, R.layout.list_item_chapter_title_list_view, chapterTitleObjectList);
        listViewChapterTitle.setAdapter(adapter);

        for(String s:filterKeywords.getSearchTagKeywords()){

            if (!s.equals(""))
                listOfFilterParameters.add(s);

        }

        for (int i = 0; i < listOfFilterParameters.size(); i++){

            DataModelSpinnerAsFilter spinnerAsFilter = new DataModelSpinnerAsFilter();
            spinnerAsFilter.setCheckBoxTitle(listOfFilterParameters.get(i));

            spinnerAsFilter.setSelected(false);

            selectedStateAndTitleForSpinner.add(spinnerAsFilter);

        }

        CustomAdapterSpinner spinnerAdapter = new CustomAdapterSpinner(this,R.layout.spinner_checkbox_items,selectedStateAndTitleForSpinner,this);
        spinnerDropdown.setAdapter(spinnerAdapter);

    }

    @Override
    public void onClickApplyFilter(List<String> data) {

        List<DataModelListViewChaptersList> chapterTitleObjectList1 = chapterTitleObjectList;

        if (data.size() == 0|| data.size()==5){
            adapter = new CustomAdapterListViewListOfTitle(this, R.layout.list_item_chapter_title_list_view, chapterTitleObjectList);
            listViewChapterTitle.setAdapter(adapter);

        }else{

            for (int i = 0; i < chapterTitleObjectList1.size(); i++){

                for (int j = 0; j < data.size(); j++){

                    if (!chapterTitleObjectList1.get(i).getSearchTagKeywords().contains(data.get(j))) {
                        chapterTitleObjectList1.remove(i);
                    }

                }

            }

        }

        adapter = new CustomAdapterListViewListOfTitle(this, R.layout.list_item_chapter_title_list_view, chapterTitleObjectList1);
        listViewChapterTitle.setAdapter(adapter);

    }
}