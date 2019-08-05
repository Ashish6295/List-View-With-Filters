package com.example.listviewwithfilters;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements FilterListView{

    ListView listViewChapterTitle;
    Spinner spinnerDropdown;

    List<DataModelListViewChaptersList> chapterTitleObjectList;
    ArrayList<String>listOfFilterParameters = new ArrayList<>();

    CustomAdapterListViewListOfTitle adapter;
    CheckedItemListSpinner checkedItemListSpinner;

    PostProcessingOnLocalJson processedLocalJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//-----------------------Declarations----------------------------------------------------------------------

        String url = "https://s3-eu-west-1.amazonaws.com/bbi.appsdata.2013/guideline-cdiff.json";
        String onlineJsonHolder ="";

        GetJsonFromUrl jsonFromUrl = new GetJsonFromUrl();

        String jsonReadFromInternalStorage;

        listViewChapterTitle = findViewById(R.id.listViewOfChapterTitles);

        List<DataModelSpinnerAsFilter> selectedStateAndTitleForSpinner = new ArrayList<>();

        checkedItemListSpinner = new CheckedItemListSpinner();

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
        jsonReadFromInternalStorage = readWriteOperationOnLocalJson.getReadContent();

        processedLocalJson = new PostProcessingOnLocalJson(jsonReadFromInternalStorage);

        chapterTitleObjectList = processedLocalJson.getListOfChaptersAndKeywords();

        adapter = new CustomAdapterListViewListOfTitle(this, R.layout.list_item_chapter_title_list_view, chapterTitleObjectList);
        listViewChapterTitle.setAdapter(adapter);

        for(String s:processedLocalJson.getSearchTagKeywords()){

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

        List<DataModelListViewChaptersList> chapterTitleObjectList1 = new ArrayList<>();

        if (data.size() == 0|| data.size()==listOfFilterParameters.size()){
            chapterTitleObjectList1 = chapterTitleObjectList;
        }else{
            for (int i = 0; i < chapterTitleObjectList.size(); i++){

                for (int j = 0; j < data.size(); j++){

                    if (chapterTitleObjectList.get(i).getSearchTagKeywords().contains(data.get(j)) || (chapterTitleObjectList.get(i).getSearchTagKeywords().equals(" "))) {
                        chapterTitleObjectList1.add(chapterTitleObjectList.get(i));
                        break;
                    }
                }
            }
        }

        adapter = new CustomAdapterListViewListOfTitle(this, R.layout.list_item_chapter_title_list_view, chapterTitleObjectList1);
        listViewChapterTitle.setAdapter(adapter);
    }
}