package com.deguffroy.adrien.mynews.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrien Deguffroy on 06/06/2018.
 */

public class NotificationsPreferences {
    public static final String DEFAULT_QUERY_TERM = "";
    public static final String DEFAULT_CATEGORY_LIST = "";

    private String queryTerm;
    private List<String> categoryList;
    private boolean isEnabled = false;

    public NotificationsPreferences(String queryTerm, List<String> categoryList, boolean isEnabled) {
        this.queryTerm = queryTerm;
        this.categoryList = categoryList;
        this.isEnabled = isEnabled;
    }

    public String getQueryTerm() {
        return queryTerm;
    }

    public void setQueryTerm(String queryTerm) {
        this.queryTerm = queryTerm;
    }

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
