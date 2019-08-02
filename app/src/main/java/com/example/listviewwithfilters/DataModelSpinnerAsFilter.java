package com.example.listviewwithfilters;

import java.util.ArrayList;
import java.util.List;

class DataModelSpinnerAsFilter {

    private String checkBoxTitle;
    private boolean isSelected;

    private boolean isSelectAllSelected = false;

    boolean isSelectAllSelected() {
        return isSelectAllSelected;
    }

    void setSelectAllSelected(boolean selectAllSelected) {
        isSelectAllSelected = selectAllSelected;
    }

    String getCheckBoxTitle() {
        return checkBoxTitle;
    }

    void setCheckBoxTitle(String checkBoxTitle) {
        this.checkBoxTitle = checkBoxTitle;
    }

    boolean isSelected() {
        return isSelected;
    }

    void setSelected(boolean selected) {
        isSelected = selected;
    }
    
}
