package com.example.listviewwithfilters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterSpinner extends ArrayAdapter<DataModelSpinnerAsFilter> {

    boolean isCheckedFromCustomAdapterView = false;
    List<DataModelSpinnerAsFilter>listOfKeywordsAndCheckedStatus = new ArrayList<>();

    SelectAllStatusHolderSpinner selectAllCheckBoxCheckedStatus = new SelectAllStatusHolderSpinner();

    public CustomAdapterSpinner(Context context, int resource, List<DataModelSpinnerAsFilter> listOfKeywordsAndCheckedStatus){
        super(context,resource,listOfKeywordsAndCheckedStatus);
        this.listOfKeywordsAndCheckedStatus = listOfKeywordsAndCheckedStatus;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final DataModelSpinnerAsFilter listOfFilterParameters = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_checkbox_items, parent,false);
        }

        LinearLayout selectAllLinearLayout = convertView.findViewById(R.id.layoutForSelectAllCheckBox);
        TextView textViewForDefaultText = convertView.findViewById(R.id.defaultTextForSpinner);

        TextView textViewCheckBoxTitle = convertView.findViewById(R.id.textForCheckBox);
        CheckBox checkBox = convertView.findViewById(R.id.Checkbox);
        final CheckBox checkBoxSelectAll = convertView.findViewById(R.id.selectAllCheckbox);

        if (position!=0){
            selectAllLinearLayout.setVisibility(View.GONE);
            textViewForDefaultText.setVisibility(View.GONE);
        }else {
            selectAllLinearLayout.setVisibility(View.VISIBLE);
            textViewForDefaultText.setVisibility(View.VISIBLE);
        }

        if (selectAllCheckBoxCheckedStatus.isSelectAllSelected()){
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);
        }

        textViewCheckBoxTitle.setText(listOfFilterParameters.getCheckBoxTitle());

        checkBoxSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {
                    selectAllCheckBoxCheckedStatus.setSelectAllSelected(true);
                }else{
                    selectAllCheckBoxCheckedStatus.setSelectAllSelected(false);
                }

                notifyDataSetChanged();

            }
        });

        return convertView;
    }
}
