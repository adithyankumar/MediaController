package com.multimedia.controller.utils;

import android.support.v7.app.AppCompatActivity;

import com.multimedia.controller.R;


public class CheckPermissionUtil {
    public static final int READ_SD_REQ_CODE = 201;
    public static void checkReadSd(AppCompatActivity activity,
                                    PermissionUtil.ReqPermissionCallback callback) {
        PermissionUtil.checkPermission(activity,
                activity.getString(R.string.request_reason),
                callback);
    }
}
