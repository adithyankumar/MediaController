package com.multimedia.controller.managers;

import android.content.Context;

import com.multimedia.controller.utils.Media;
import com.multimedia.controller.interfaces.AudioAddListener;
import com.multimedia.controller.interfaces.AudioDeleteListener;
import com.multimedia.controller.interfaces.AudioFetchListener;
import com.multimedia.controller.interfaces.ImageAddListener;
import com.multimedia.controller.interfaces.ImageDeleteListener;
import com.multimedia.controller.interfaces.ImageFetchListener;
import com.multimedia.controller.interfaces.SuperNormalAudioUserControls;
import com.multimedia.controller.interfaces.SuperNormalImageUserControls;
import com.multimedia.controller.interfaces.SuperVideoUserControls;
import com.multimedia.controller.interfaces.VideoAddListener;
import com.multimedia.controller.interfaces.VideoDeleteListener;
import com.multimedia.controller.interfaces.VideoFetchListener;

import java.util.List;

/**
 * Created by AKrishnakuma on 6/20/2019.
 */

public class SuperUserManager implements SuperNormalImageUserControls,
        SuperNormalAudioUserControls, SuperVideoUserControls{

    private static SuperUserManager instance;
    private final SuperUserRepository superUserRepository;

    private SuperUserManager(Context context) {
        superUserRepository = SuperUserRepository.getInstance(context);
    }
    public static SuperUserManager getInstance(Context context){
        if (instance == null)
            instance = new SuperUserManager(context);
       return instance;
    }


    @Override
    public void getImageList(ImageFetchListener imageFetchListener) {
        superUserRepository.getImageList(imageFetchListener);
    }

    @Override
    public void addImageList(List<Media> mediaList, ImageAddListener imageAddListener) {
        superUserRepository.addImageList(mediaList, imageAddListener);
    }

    @Override
    public void deleteImageList(List<Media> mediaList, ImageDeleteListener imageDeleteListener) {
        superUserRepository.deleteImageList(mediaList, imageDeleteListener);
    }

    @Override
    public void getVideoList(VideoFetchListener videoFetchListener) {
        superUserRepository.getVideoList(videoFetchListener);
    }

    @Override
    public void getAudioList(AudioFetchListener audioFetchListener) {
        superUserRepository.getAudioList(audioFetchListener);
    }

    @Override
    public void addVideoList(List<Media> mediaList, VideoAddListener videoAddListener) {
        superUserRepository.addVideoList(mediaList, videoAddListener);
    }

    @Override
    public void deleteVideoList(List<Media> mediaList, VideoDeleteListener videoDeleteListener) {
        superUserRepository.deleteVideoList(mediaList, videoDeleteListener);
    }

    @Override
    public void addAudioList(List<Media> mediaList, AudioAddListener audioAddListener) {
        superUserRepository.addAudioList(mediaList, audioAddListener);
    }

    @Override
    public void deleteAudioList(List<Media> mediaList, AudioDeleteListener audioDeleteListener) {
        superUserRepository.deleteAudioList(mediaList, audioDeleteListener);
    }
}
