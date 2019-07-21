package com.multimedia.controller.managers;

import android.content.Context;

import com.multimedia.controller.interfaces.AudioFetchListener;
import com.multimedia.controller.interfaces.ImageFetchListener;
import com.multimedia.controller.interfaces.NormalAudioUserControls;
import com.multimedia.controller.interfaces.NormalImageUserControls;
import com.multimedia.controller.interfaces.NormalVideoUserControls;
import com.multimedia.controller.interfaces.VideoFetchListener;

/**
 * Created by AKrishnakuma on 6/20/2019.
 */

public class NormalUserManager implements NormalImageUserControls,
        NormalVideoUserControls , NormalAudioUserControls{

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
}
