package com.multimedia.controller.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by AKrishnakuma on 6/22/2019.
 */

public class SnackBarUtils {
    public static void show(Context context, int resId){
        Snackbar.make(((AppCompatActivity)context).findViewById(android.R.id.content),
                resId, Snackbar.LENGTH_LONG).show();
    }

    public static void showSuccess(Context context, String message){
        Snackbar snack = Snackbar.make(((AppCompatActivity)context).
                findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        TextView tv = view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.GREEN);
        snack.show();
    }

    public static void showError(Context context, String message){
        Snackbar snack = Snackbar.make(((AppCompatActivity)context).
                findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        TextView tv = view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.RED);
        snack.show();
    }

    public static void showError(Context context, int resId){
        Snackbar snack = Snackbar.make(((AppCompatActivity)context).
                findViewById(android.R.id.content), context.getString(resId),Snackbar.LENGTH_LONG);
        View view = snack.getView();
        TextView tv = view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.RED);
        snack.show();
    }

    public static void show(AppCompatActivity appCompatActivity, int resId){
        Snackbar.make(appCompatActivity.findViewById(android.R.id.content),
                resId, Snackbar.LENGTH_LONG).show();
    }

    public static void show(Context context, int resId, final int actionStringId,
                 View.OnClickListener listener) {
        Snackbar.make(
                ((AppCompatActivity) context).findViewById(android.R.id.content),
                resId,
                Snackbar.LENGTH_INDEFINITE).show();
    }
}
