package com.multimedia.controller.managers;

import android.content.Context;

import com.multimedia.controller.interfaces.AudioFetchListener;
import com.multimedia.controller.interfaces.DocFetchListener;
import com.multimedia.controller.interfaces.ImageFetchListener;
import com.multimedia.controller.interfaces.NormalUserAudioControls;
import com.multimedia.controller.interfaces.NormalUserDocControls;
import com.multimedia.controller.interfaces.NormalUserImageControls;
import com.multimedia.controller.interfaces.NormalUserVideoControls;
import com.multimedia.controller.interfaces.VideoFetchListener;

public class NormalUserManager implements NormalUserImageControls,
        NormalUserVideoControls, NormalUserAudioControls, NormalUserDocControls {

    private static NormalUserManager instance;
    private final NormalUserRepository normalUserRepository;

    private NormalUserManager(Context context){
        normalUserRepository = NormalUserRepository.getInstance(context);
    }
    public static NormalUserManager getInstance(Context context){
        if (instance == null)
            instance = new NormalUserManager(context);
        return instance;
    }

    @Override
    public void getImageList(ImageFetchListener imageFetchListener) {
        normalUserRepository.getImageList(imageFetchListener);
    }

    @Override
    public void getAudioList(AudioFetchListener audioFetchListener) {
        normalUserRepository.getAudioList(audioFetchListener);
    }

    @Override
    public void getVideoList(VideoFetchListener videoFetchListener) {
        normalUserRepository.getVideoList(videoFetchListener);
    }

    @Override
    public void getDocList(DocFetchListener docFetchListener) {
        normalUserRepository.getDocList(docFetchListener);
    }
}
