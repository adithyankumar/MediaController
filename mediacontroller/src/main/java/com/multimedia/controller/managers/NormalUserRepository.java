package com.multimedia.controller.managers;

import android.content.Context;

import com.multimedia.controller.interfaces.DocFetchListener;
import com.multimedia.controller.utils.MediaTypeEnum;
import com.multimedia.controller.database.MediaDao;
import com.multimedia.controller.database.MediaDatabase;
import com.multimedia.controller.interfaces.AudioFetchListener;
import com.multimedia.controller.interfaces.ImageFetchListener;
import com.multimedia.controller.interfaces.VideoFetchListener;


 class NormalUserRepository {
    final MediaDao mediaDao;
    private static NormalUserRepository instance;
    final MediaDatabase mediaDatabase;

    NormalUserRepository(Context context) {
        mediaDatabase = MediaDatabase.getInstance(context);
        mediaDao = mediaDatabase.getMediaDao();
    }

    public static NormalUserRepository getInstance(Context context){
        if (instance == null)
            instance = new NormalUserRepository(context);
        return instance;
    }

    public void getImageList(final ImageFetchListener imageFetchListener) {
        mediaDatabase.runInTransaction(() -> {
            try {
                imageFetchListener.onImageFetchSuccess(mediaDao.getMediaList(MediaTypeEnum.IMAGE.toString()));
            }catch (Exception e){
                imageFetchListener.onImageFetchFailure(e.getMessage());
            }
        });
    }

    public void getVideoList(final VideoFetchListener videoFetchListener){
        mediaDatabase.runInTransaction(() -> {
            try {
                videoFetchListener.onVideoFetchSuccess(mediaDao.getMediaList(MediaTypeEnum.VIDEO.toString()));
            }catch (Exception e){
                videoFetchListener.onVideoFetchFailure(e.getMessage());
            }
        });
    }

    public void getDocList(final DocFetchListener docFetchListener){
        mediaDatabase.runInTransaction(() -> {
            try {
                docFetchListener.onDocFetchSuccess(mediaDao.getMediaList(MediaTypeEnum.DOC.toString()));
            }catch (Exception e){
                docFetchListener.onDocFetchFailure(e.getMessage());
            }
        });
    }

    public void getAudioList(final AudioFetchListener audioFetchListener){
        mediaDatabase.runInTransaction(() -> {
            try {
                audioFetchListener.onAudioFetchSuccess(mediaDao.getMediaList(MediaTypeEnum.AUDIO.toString()));
            }catch (Exception e){
                audioFetchListener.onAudioFetchFailure(e.getMessage());
            }
        });
    }


}
