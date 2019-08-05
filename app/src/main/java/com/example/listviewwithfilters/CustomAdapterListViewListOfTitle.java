package com.example.listviewwithfilters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomAdapterListViewListOfTitle extends ArrayAdapter<DataModelListViewChaptersList> {

    private List<DataModelListViewChaptersList> chaptersTitleList;

    CustomAdapterListViewListOfTitle(Context context, int resource, List<DataModelListViewChaptersList> chapterTitleList){
        super(context, resource, chapterTitleList);
        this.chaptersTitleList = chapterTitleList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_chapter_title_list_view, parent, false);
        }

        TextView textViewChapterTitle = convertView.findViewById(R.id.displayChapterTextView);
        TextView textViewSearchTagKeyword = convertView.findViewById(R.id.displaySearchKeywordTextView);

        textViewChapterTitle.setText(chaptersTitleList.get(position).getChapterTitle());
        textViewSearchTagKeyword.setText(chaptersTitleList.get(position).getSearchTagKeywords());

        return convertView;
    }
}
