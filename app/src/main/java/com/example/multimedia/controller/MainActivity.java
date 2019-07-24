package com.example.multimedia.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.multimedia.controller.utils.MediaTypeEnum;
import com.kidshome.multimediacontroller.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.btn_normal_user_image_layout).setOnClickListener(v ->
            DemoActivity.startDemoActivity(this, false, MediaTypeEnum.IMAGE)
        );

        findViewById(R.id.btn_super_user_image_layout).setOnClickListener(v ->
                DemoActivity.startDemoActivity(this, true, MediaTypeEnum.IMAGE)
        );

        findViewById(R.id.btn_normal_user_audio_layout).setOnClickListener(v ->
                DemoActivity.startDemoActivity(this, false, MediaTypeEnum.AUDIO)
        );

        findViewById(R.id.btn_super_user_audio_layout).setOnClickListener(v ->
                DemoActivity.startDemoActivity(this, true, MediaTypeEnum.AUDIO)
        );

        findViewById(R.id.btn_normal_user_video_layout).setOnClickListener(v ->
                DemoActivity.startDemoActivity(this, false, MediaTypeEnum.VIDEO)
        );

        findViewById(R.id.btn_super_user_video_layout).setOnClickListener(v ->
                DemoActivity.startDemoActivity(this, true, MediaTypeEnum.VIDEO)
        );


        findViewById(R.id.btn_normal_user_doc_layout).setOnClickListener(v ->
                DemoActivity.startDemoActivity(this, false, MediaTypeEnum.DOC)
        );

        findViewById(R.id.btn_super_user_doc_layout).setOnClickListener(v ->
                DemoActivity.startDemoActivity(this, true, MediaTypeEnum.DOC)
        );

    }

}
