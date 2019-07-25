package com.example.listviewwithfilters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FilterKeyWordsExtractionFromJson {


    private String[] searchTagKeywords;
    private String[] listOfChapterTitle;

    FilterKeyWordsExtractionFromJson(String jsonStringforSearchTagKeywords){

        String searchKeywordsArrayItems = "";
        String chapterTitleStorage = "";

        try {

            JSONObject mainJsonObject = new JSONObject(jsonStringforSearchTagKeywords);
            JSONArray guidelinesArray = mainJsonObject.getJSONArray("guidelines");

            StringBuilder stringBuilderforExtractingSearchTagKeywords = new StringBuilder();
            StringBuilder stringBuilderForExtractionOfChapterTitles = new StringBuilder();

            for (int i = 0; i< guidelinesArray.length();i++){

                JSONObject objectsInsideGuidelinesArray = guidelinesArray.getJSONObject(i);
                JSONArray chaptersArray = objectsInsideGuidelinesArray.getJSONArray("chapters");

                for (int j = 0; j< chaptersArray.length(); j++){

                    JSONObject objectsInsideChaptersArray = chaptersArray.getJSONObject(j);
                    JSONArray searchKeywordsArray = objectsInsideChaptersArray.getJSONArray("search_tag_keywords");

                    stringBuilderForExtractionOfChapterTitles.append(objectsInsideChaptersArray.getString("chapter_title")).append("\n");

                    for (int k = 0; k< searchKeywordsArray.length(); k++){

                        stringBuilderforExtractingSearchTagKeywords.append(searchKeywordsArray.get(k)).append("\n");

                    }

                    JSONArray chaptersArrayInsideChaptersArray = objectsInsideChaptersArray.getJSONArray("chapters");

                    for (int l = 0; l< chaptersArrayInsideChaptersArray.length(); l++){

                        JSONObject objectsInside2ndLevelChaptersArray = chaptersArrayInsideChaptersArray.getJSONObject(l);
                        JSONArray searchKeywordsArray1 = objectsInside2ndLevelChaptersArray.getJSONArray("search_tag_keywords");

                        stringBuilderForExtractionOfChapterTitles.append(objectsInside2ndLevelChaptersArray.getString("chapter_title")).append("\n");

                        for (int m = 0; m< searchKeywordsArray1.length(); m++){

                            stringBuilderforExtractingSearchTagKeywords.append(searchKeywordsArray1.get(m)).append("\n");

                        }

                    }

                }

            }

            searchKeywordsArrayItems = stringBuilderforExtractingSearchTagKeywords.toString();
            chapterTitleStorage = stringBuilderForExtractionOfChapterTitles.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //the below loop is used to remove duplicate value from String, duplicate values are replaced with blank i.e ""
        //then in main activity list is used to add all values inside array except "". hence, we have a list which has no duplicates and only unique value
        //A better approach would have been to use Set to store unique elements but i would have had to read about the concept and then try them.
        //hence, for time being the following approach is used.

        searchTagKeywords = searchKeywordsArrayItems.split("\n");
        listOfChapterTitle = chapterTitleStorage.split("\n");

        for (int c = 0; c < searchTagKeywords.length; c++){

            if (searchTagKeywords[c]!=null){

                for(int j = c + 1; j < searchTagKeywords.length; j++)
                {

                    if(searchTagKeywords[c].equals(searchTagKeywords[j]))
                    {
                        searchTagKeywords[j]="";
                    }

                }

            }

        }

    }

    public String[] getSearchTagKeywords() {
        return searchTagKeywords;
    }

    public String[] getListOfChapterTitle() {
        return listOfChapterTitle;
    }
}
