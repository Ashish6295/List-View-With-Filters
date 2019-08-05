package com.example.listviewwithfilters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomAdapterSpinner extends ArrayAdapter<DataModelSpinnerAsFilter> {

     private List<DataModelSpinnerAsFilter> listOfKeywordsAndCheckedStatus;
     private CheckedItemListSpinner checkedItemListSpinner = new CheckedItemListSpinner();
     private FilterListView filterListView;

    CustomAdapterSpinner(Context context, int resource, List<DataModelSpinnerAsFilter> listOfKeywordsAndCheckedStatus,FilterListView filterListView) {
        super(context, resource,listOfKeywordsAndCheckedStatus);
        this.listOfKeywordsAndCheckedStatus = listOfKeywordsAndCheckedStatus;
        this.filterListView = filterListView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return listOfKeywordsAndCheckedStatus.size();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_checkbox_items, parent, false);
        }

//---------------------------------------------------------------------------------------------------------------
        LinearLayout selectAllLinearLayout = convertView.findViewById(R.id.layoutForSelectAllCheckBox);
        TextView textViewForDefaultText = convertView.findViewById(R.id.defaultTextForSpinner);

        LinearLayout buttonsLayout = convertView.findViewById(R.id.layoutForButtons);

        TextView textViewCheckBoxTitle = convertView.findViewById(R.id.textForCheckBox);
        final CheckBox checkBox = convertView.findViewById(R.id.Checkbox);
        final CheckBox checkBoxSelectAll = convertView.findViewById(R.id.selectAllCheckbox);

        Button applyFilterButton = convertView.findViewById(R.id.buttonApplyFilter);
//----------------------------------------------------------------------------------------------------------------

        textViewCheckBoxTitle.setText(listOfKeywordsAndCheckedStatus.get(position).getCheckBoxTitle());

        if (position != 0) {
            selectAllLinearLayout.setVisibility(View.GONE);
            textViewForDefaultText.setVisibility(View.GONE);
        } else {
            selectAllLinearLayout.setVisibility(View.VISIBLE);
            textViewForDefaultText.setVisibility(View.VISIBLE);
        }

        if (position == (listOfKeywordsAndCheckedStatus.size()-1)) {
            buttonsLayout.setVisibility(View.VISIBLE);
        } else {
            buttonsLayout.setVisibility(View.GONE);
        }

        if (listOfKeywordsAndCheckedStatus.get(position).isSelectAllSelected())
            checkBox.setEnabled(false);
        else if (!listOfKeywordsAndCheckedStatus.get(position).isSelectAllSelected())
            checkBox.setEnabled(true);
//---------------------------------------------------------------------------------------------------------------
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {
                    listOfKeywordsAndCheckedStatus.get(position).setSelected(true);
                    //checkedItemListSpinner.getCheckedFilterParameters().add(listOfKeywordsAndCheckedStatus.get(position).getCheckBoxTitle());
                }else {
                    listOfKeywordsAndCheckedStatus.get(position).setSelected(false);
                    //checkedItemListSpinner.getCheckedFilterParameters().remove(listOfKeywordsAndCheckedStatus.get(position).getCheckBoxTitle());
                }

                if (listOfKeywordsAndCheckedStatus.get(position).isSelected() && !checkedItemListSpinner.getCheckedFilterParameters().contains(listOfKeywordsAndCheckedStatus.get(position).getCheckBoxTitle())){
                    checkedItemListSpinner.getCheckedFilterParameters().add(listOfKeywordsAndCheckedStatus.get(position).getCheckBoxTitle());
                }else if (!listOfKeywordsAndCheckedStatus.get(position).isSelected()){
                    checkedItemListSpinner.getCheckedFilterParameters().remove(listOfKeywordsAndCheckedStatus.get(position).getCheckBoxTitle());
                }

            }
        });

        checkBoxSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                for (int i = 0; i < listOfKeywordsAndCheckedStatus.size(); i++) {
                    if (isChecked) {
                        listOfKeywordsAndCheckedStatus.get(i).setSelected(true);
                        listOfKeywordsAndCheckedStatus.get(i).setSelectAllSelected(true);
                    } else {
                        listOfKeywordsAndCheckedStatus.get(i).setSelected(false);
                        listOfKeywordsAndCheckedStatus.get(i).setSelectAllSelected(false);
                    }
                }
                notifyDataSetChanged();
            }
        });

        checkBox.setChecked(listOfKeywordsAndCheckedStatus.get(position).isSelected());

//---------------------------------------------------------------------------------------------------------------

        applyFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  filterListView.onClickApplyFilter(checkedItemListSpinner.getCheckedFilterParameters());
                  
            }
        });

//---------------------------------------------------------------------------------------------------------------
        return convertView;
    }
}
