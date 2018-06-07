package com.deguffroy.adrien.mynews;

import com.deguffroy.adrien.mynews.Models.NotificationsPreferences;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Adrien Deguffroy on 06/06/2018.
 */


public class NotificationsPreferencesUnitTest {

    @Test
    public void createNotificationsPreferences() throws Exception{
        List<String> categoryList = new ArrayList<>();
        String queryTerm = "test";

        NotificationsPreferences notificationsPref = new NotificationsPreferences(queryTerm, categoryList, false);

        assertEquals("test", notificationsPref.getQueryTerm());
        assertEquals(true, notificationsPref.getCategoryList().isEmpty());
        assertEquals(false, notificationsPref.isEnabled());
    }

    @Test
    public void setQueryTerm() throws Exception {
        NotificationsPreferences notificationsPref = new NotificationsPreferences("",null, false);

        notificationsPref.setQueryTerm("test");

        assertEquals("test", notificationsPref.getQueryTerm());
    }

    @Test
    public void setCategoryList() throws Exception {
        NotificationsPreferences notificationsPref = new NotificationsPreferences("",null, false);

        List<String> categoryList = new ArrayList<>();
        String entry1 = "Arts";
        String entry2 = "Politics";
        categoryList.add(entry1);
        categoryList.add(entry2);

        notificationsPref.setCategoryList(categoryList);

        assertEquals("Politics", notificationsPref.getCategoryList().get(1));
    }

    @Test
    public void setEnabled() throws Exception {
        NotificationsPreferences notificationsPref = new NotificationsPreferences("",null, false);

        notificationsPref.setEnabled(true);

        assertEquals(true, notificationsPref.isEnabled());
    }
}
