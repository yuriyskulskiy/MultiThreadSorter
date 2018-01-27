package com.example.yuriy.multithredsorter.incommingData;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


class DataParser {

    private final static String TAG = DataParser.class.getSimpleName();

    static ArrayList<String> parseForMechanizmData(String jsonString) {

        if (TextUtils.isEmpty(jsonString)) {
            throw new RuntimeException("can't fetch data from empty jsonString");
        }
        ArrayList<String> resultNamesList = new ArrayList<>();
        try {

            JSONObject root = new JSONObject(jsonString);
            JSONArray mechanizmJsonArray = root.getJSONArray("data_array");
            for (int i = 0; i < mechanizmJsonArray.length(); i++) {

                String mechanizmName = mechanizmJsonArray.getString(i);
                resultNamesList.add(mechanizmName);
                Log.w(TAG, "mehanizm = " + mechanizmName);


            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Problem parsing the earthquake JSON results", e);
        }


        return resultNamesList;
    }
}
