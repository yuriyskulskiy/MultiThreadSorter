package com.example.yuriy.multithreadsorter.prefferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.example.yuriy.multithreadsorter.Constants.BUBBLE_SORT_TYPE;


public class SharedPreferencesProvider {
    private static final String SORT_METHOD_TYPE = "sort_method_type";
//    public static final String BUBBLE_TYPE = "bubble";
//    public static final String QUICK_SORT = "quick_sort";


    public int getSelectedSortType(Context context) {
        return getDefaultSharedPref(context).getInt(SORT_METHOD_TYPE, BUBBLE_SORT_TYPE);
    }

    public void saveSortType(int sortType, Context context) {
        putInt(SORT_METHOD_TYPE, sortType, context);
    }

    private SharedPreferences getDefaultSharedPref(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences;
    }


//    private void putString(String key, String value, Context context) {
//        SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
//        editor.putString(key, value);
//        editor.apply();
//    }

    private void putInt(String key, int value, Context context) {
        SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }
}
