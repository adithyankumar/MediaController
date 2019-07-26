package com.example.multimedia.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.kidshome.multimediacontroller.R;
import com.multimedia.controller.interfaces.AudioFetchListener;
import com.multimedia.controller.interfaces.ImageAddListener;
import com.multimedia.controller.interfaces.ImageFetchListener;
import com.multimedia.controller.managers.NormalUserManager;
import com.multimedia.controller.managers.SuperUserManager;
import com.multimedia.controller.ui.fragment.MediaFragment;
import com.multimedia.controller.utils.Constants;
import com.multimedia.controller.utils.Media;
import com.multimedia.controller.utils.MediaTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends AppCompatActivity {

    private String TAG = DemoActivity.class.getSimpleName();
    private Intent intent;
    private MediaTypeEnum mediaTypeEnum;
    private boolean isSuperUser;

    public static void startDemoActivity(Context context, boolean isSuperUser, MediaTypeEnum mediaTypeEnum){
        Intent intent = new Intent(context, DemoActivity.class);
        intent.putExtra(Constants.IS_SUPER_USER, isSuperUser);
        intent.putExtra(Constants.MIME_TYPE, mediaTypeEnum);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.IS_SUPER_USER)
                && intent.hasExtra(Constants.MIME_TYPE)){
            isSuperUser = intent.getBooleanExtra(Constants.IS_SUPER_USER, false);
            mediaTypeEnum = (MediaTypeEnum) intent.getSerializableExtra(Constants.MIME_TYPE);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container,
                        MediaFragment.newInstance(isSuperUser, mediaTypeEnum))
                .commit();

     }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()){
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
