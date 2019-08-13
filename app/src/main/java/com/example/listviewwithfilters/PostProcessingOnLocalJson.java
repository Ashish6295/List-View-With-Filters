package com.example.listviewwithfilters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


class PostProcessingOnLocalJson {

    private List<DataModelSpinnerAsFilter> selectedStateAndTitleForSpinner = new ArrayList<>();
    private List<DataModelListViewChaptersList> listOfChaptersAndKeywords = new ArrayList<>();

    PostProcessingOnLocalJson(String jsonReadFromLocalFile){

        Set<String> listOfKeywords = new HashSet<>();

        try {

            JSONObject mainJsonObject = new JSONObject(jsonReadFromLocalFile);
            JSONArray guidelinesArray = mainJsonObject.getJSONArray("guidelines");

            for (int i = 0; i < guidelinesArray.length();i++){

                JSONObject objectsInsideGuidelinesArray = guidelinesArray.getJSONObject(i);
                JSONArray chaptersArray = objectsInsideGuidelinesArray.getJSONArray("chapters");

                for (int j = 0; j < chaptersArray.length(); j++){

                    JSONObject objectsInsideChaptersArray = chaptersArray.getJSONObject(j);
                    JSONArray searchKeywordsArray = objectsInsideChaptersArray.getJSONArray("search_tag_keywords");

                    listOfChaptersAndKeywords.add(new DataModelListViewChaptersList(objectsInsideChaptersArray));

                    for (int k = 0; k < searchKeywordsArray.length(); k++){
                        listOfKeywords.add(searchKeywordsArray.get(k).toString());
                    }

                    JSONArray chaptersArrayInsideChaptersArray = objectsInsideChaptersArray.getJSONArray("chapters");

                    for (int l = 0; l < chaptersArrayInsideChaptersArray.length(); l++){

                        JSONObject objectsInside2ndLevelChaptersArray = chaptersArrayInsideChaptersArray.getJSONObject(l);
                        JSONArray searchKeywordsArray1 = objectsInside2ndLevelChaptersArray.getJSONArray("search_tag_keywords");

                        listOfChaptersAndKeywords.add(new DataModelListViewChaptersList(objectsInside2ndLevelChaptersArray));

                        for (int m = 0; m < searchKeywordsArray1.length(); m++){
                            listOfKeywords.add(searchKeywordsArray1.get(m).toString());
                        }

                    }

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        listOfKeywords.remove("");

        List<String> listOfUniqueKeywords = new ArrayList<>(listOfKeywords);

        for (int i = 0; i < listOfUniqueKeywords.size(); i++){

            DataModelSpinnerAsFilter spinnerAsFilter = new DataModelSpinnerAsFilter();
            spinnerAsFilter.setCheckBoxTitle(listOfUniqueKeywords.get(i));

            spinnerAsFilter.setSelected(false);

            selectedStateAndTitleForSpinner.add(spinnerAsFilter);

        }

    }

    List<DataModelSpinnerAsFilter> getSelectedStateAndTitleForSpinner() {
        return selectedStateAndTitleForSpinner;
    }

    List<DataModelListViewChaptersList> getListOfChaptersAndKeywords() {
        return listOfChaptersAndKeywords;
    }
}
