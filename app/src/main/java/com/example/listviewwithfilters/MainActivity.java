package com.example.listviewwithfilters;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements FilterListView{

    ListView listViewChapterTitle;
    CustomAdapterListViewListOfTitle adapter;
    PostProcessingOnLocalJson processedLocalJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewChapterTitle = findViewById(R.id.listViewOfChapterTitles);
        Spinner spinnerDropdown = findViewById(R.id.spinnerPlaceHolder);
        GetJsonFromUrl jsonFromUrl = new GetJsonFromUrl();

        ReadWriteOnLocalJson readWriteOperationOnLocalJson = null;

        try {
            readWriteOperationOnLocalJson = new ReadWriteOnLocalJson(jsonFromUrl.execute("https://s3-eu-west-1.amazonaws.com/bbi.appsdata.2013/guideline-cdiff.json").get(),this);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        processedLocalJson = new PostProcessingOnLocalJson(Objects.requireNonNull(readWriteOperationOnLocalJson).getReadContent());

        adapter = new CustomAdapterListViewListOfTitle(this, R.layout.list_item_chapter_title_list_view, processedLocalJson.getListOfChaptersAndKeywords());
        listViewChapterTitle.setAdapter(adapter);

        CustomAdapterSpinner spinnerAdapter = new CustomAdapterSpinner(this,R.layout.spinner_checkbox_items, processedLocalJson.getSelectedStateAndTitleForSpinner(),this);
        spinnerDropdown.setAdapter(spinnerAdapter);
    }

    @Override
    public void onClickApplyFilter(List<String> data) {

        List<DataModelListViewChaptersList> chapterTitleObjectList1 = new ArrayList<>();

        if (data.size() == 0|| data.size() == processedLocalJson.getSelectedStateAndTitleForSpinner().size()){
            chapterTitleObjectList1 = processedLocalJson.getListOfChaptersAndKeywords();
        }else{
            for (int i = 0; i < processedLocalJson.getListOfChaptersAndKeywords().size(); i++){

                for (int j = 0; j < data.size(); j++){

                    if (processedLocalJson.getListOfChaptersAndKeywords().get(i).getSearchTagKeywords().contains(data.get(j)) || (processedLocalJson.getListOfChaptersAndKeywords().get(i).getSearchTagKeywords().equals(" "))) {
                        chapterTitleObjectList1.add(processedLocalJson.getListOfChaptersAndKeywords().get(i));
                        break;
                    }
                }
            }
        }

        adapter = new CustomAdapterListViewListOfTitle(this, R.layout.list_item_chapter_title_list_view, chapterTitleObjectList1);
        listViewChapterTitle.setAdapter(adapter);
    }
}