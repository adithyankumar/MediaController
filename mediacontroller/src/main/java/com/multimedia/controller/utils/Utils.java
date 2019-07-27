package com.multimedia.controller.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by AKrishnakuma on 6/7/2019.
 */

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthDp / columnWidthDp + 0.5);
    }

    public static String toCapitialize(String string) {
        StringBuilder newStr = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (newStr.length() == 0)
                newStr.append(String.valueOf(c).toUpperCase());
            else
                newStr.append(c);
        }
        return newStr.toString();
    }


}
