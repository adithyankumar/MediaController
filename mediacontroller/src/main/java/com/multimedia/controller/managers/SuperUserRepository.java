package com.multimedia.controller.managers;

import android.content.Context;

import com.multimedia.controller.R;
import com.multimedia.controller.interfaces.DocAddListener;
import com.multimedia.controller.interfaces.DocDeleteListener;
import com.multimedia.controller.utils.Media;
import com.multimedia.controller.interfaces.AudioAddListener;
import com.multimedia.controller.interfaces.AudioDeleteListener;
import com.multimedia.controller.interfaces.ImageAddListener;
import com.multimedia.controller.interfaces.ImageDeleteListener;
import com.multimedia.controller.interfaces.VideoAddListener;
import com.multimedia.controller.interfaces.VideoDeleteListener;

import java.lang.ref.WeakReference;
import java.util.List;


 class SuperUserRepository extends NormalUserRepository {
    private static SuperUserRepository instance;
    private final Context context;
    //private MediaDatabase mediaDatabase;

    private SuperUserRepository(Context context) {
        super(context);
        this.context = new WeakReference<>(context).get();
      //  mediaDatabase = MediaDatabase.getInstance(context);
    }

    public static SuperUserRepository getInstance(Context context) {
        if (instance == null)
            instance = new SuperUserRepository(context);
        return instance;
    }

    public void addImageList(final List<Media> mediaList, final ImageAddListener imageAddListener) {
        mediaDatabase.runInTransaction(() -> {
            try {
                mediaDao.insert(mediaList);
                imageAddListener.onImageAddSuccess(mediaList,
                        context.getString(R.string.image_added_successfully));
            } catch (Exception e) {
                imageAddListener.onImageAddFailure(e.getMessage());
            }
        });
    }

    public void deleteImageList(final List<Media> mediaList, final ImageDeleteListener imageDeleteListener) {
        mediaDatabase.runInTransaction(() -> {
            try {
                mediaDao.delete(mediaList);
                imageDeleteListener.onImageDeleteSuccess(mediaList,
                        context.getString(R.string.image_deleted_successfully));
            } catch (Exception e) {
                imageDeleteListener.onImageDeleteFailure(e.getMessage());
            }
        });

    }

    public void addVideoList(final List<Media> mediaList, final VideoAddListener videoAddListener) {

        mediaDatabase.runInTransaction(() -> {
            try {
                mediaDao.insert(mediaList);
                videoAddListener.onVideoAddSuccess(mediaList,
                        context.getString(R.string.video_added_successfully));
            } catch (Exception e) {
                videoAddListener.onVideoAddFailure(e.getMessage());
            }
        });
    }

    public void deleteVideoList(List<Media> mediaList, VideoDeleteListener videoDeleteListener) {
        mediaDatabase.runInTransaction(() -> {

        });
        try {
            mediaDao.delete(mediaList);
            videoDeleteListener.onVideoDeleteSuccess(mediaList,
                    context.getString(R.string.video_deleted_successfully));
        } catch (Exception e) {
            videoDeleteListener.onVideoDeleteFailure(e.getMessage());
        }
    }

    public void addAudioList(final List<Media> mediaList, final AudioAddListener audioAddListener) {
        mediaDatabase.runInTransaction(() -> {
            try {
                mediaDao.insert(mediaList);
                audioAddListener.onAudioAddSuccess(mediaList,
                        context.getString(R.string.audio_added_successfully));
            } catch (Exception e) {
                audioAddListener.onAudioAddFailure(e.getMessage());
            }
        });

    }

    public void deleteAudioList(final List<Media> mediaList, final AudioDeleteListener audioDeleteListener) {
        mediaDatabase.runInTransaction(() -> {
            try {
                mediaDao.delete(mediaList);
                audioDeleteListener.onAudioDeleteSuccess(mediaList,
                        context.getString(R.string.audio_deleted_successfully));
            } catch (Exception e) {
                audioDeleteListener.onAudioDeleteFailure(e.getMessage());
            }
        });
    }

    public void addDocList(final List<Media> mediaList, final DocAddListener docAddListener) {
        mediaDatabase.runInTransaction(() -> {
            try {
                mediaDao.insert(mediaList);
                docAddListener.onDocAddSuccess(mediaList,
                        context.getString(R.string.doc_added_successfully));
            } catch (Exception e) {
                docAddListener.onDocAddFailure(e.getMessage());
            }
        });

    }

    public void deleteDocList(final List<Media> mediaList, final DocDeleteListener docDeleteListener) {
        mediaDatabase.runInTransaction(() -> {
            try {
                mediaDao.delete(mediaList);
                docDeleteListener.onDocDeleteSuccess(mediaList,
                        context.getString(R.string.doc_deleted_successfully));
            } catch (Exception e) {
                docDeleteListener.onDocDeleteFailure(e.getMessage());
            }
        });
    }
    
}
