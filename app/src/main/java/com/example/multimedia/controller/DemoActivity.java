package com.example.multimedia.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.kidshome.multimediacontroller.R;
import com.multimedia.controller.managers.SuperUserManager;
import com.multimedia.controller.ui.fragment.MediaFragment;
import com.multimedia.controller.utils.Constants;
import com.multimedia.controller.utils.MediaTypeEnum;

public class DemoActivity extends BaseActivity {

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


        //User user = UserGenrator.getUser(USER_NORMAL)

        // user.getImageList();
        //user.getVideos();


//       SuperUserManager userManager = SuperUserManager.getInstance(this);

        /*userManager.addImageList(new ArrayList<>(), new ImageAddListener() {
            @Override
            public void onImageAddSuccess(String message) {

            }

            @Override
            public void onImageAddFailure(String errorMessage) {

            }
        });
       userManager.getVideoList(new VideoFetchListener() {
           @Override
           public void onVideoFetchSuccess(List<Media> imageList) {
                textView.setText("onVideoFetchSuccess = "+imageList.toString());
               Log.e(TAG, "onVideoFetchSuccess: "+imageList.size());
           }

           @Override
           public void onVideoFetchFailure(String errorMessage) {
                textView.setText("onVideoFetchFailure" +errorMessage);
               Log.e(TAG, "onVideoFetchFailure" +errorMessage);

           }
       });*/


/*
        SuperUserManager superUserManager = SuperUserManager.getInstance(this);
        superUserManager.getImageList(new ImageFetchListener() {
            @Override
            public void onImageFetchSuccess(List<Media> imageList) {

            }

            @Override
            public void onImageFetchFailure(String errorMessage) {

            }
        });
*/

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()){
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
