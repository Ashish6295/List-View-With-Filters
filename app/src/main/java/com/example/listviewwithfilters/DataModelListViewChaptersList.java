package com.example.listviewwithfilters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class DataModelListViewChaptersList {

    private String chapterTitle;
    private String searchTagKeywords;

    DataModelListViewChaptersList(JSONObject jsonObject){

        if (jsonObject != null){

            StringBuilder stringBuilder = new StringBuilder();

            try {

                chapterTitle = jsonObject.getString("chapter_title");
                JSONArray searchTagKeywordsArray = jsonObject.getJSONArray("search_tag_keywords");

                for (int i = 0; i < searchTagKeywordsArray.length(); i++){

                    stringBuilder.append(searchTagKeywordsArray.get(i)).append(" ");

                }

                searchTagKeywords = stringBuilder.toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    String getChapterTitle() {
        return chapterTitle;
    }


    String getSearchTagKeywords() {
        return searchTagKeywords;
    }

}
