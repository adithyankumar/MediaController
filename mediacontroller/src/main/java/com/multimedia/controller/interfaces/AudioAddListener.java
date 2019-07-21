package com.multimedia.controller.interfaces;

import com.multimedia.controller.utils.Media;

import java.util.List;

/**
 * Created by AKrishnakuma on 6/25/2019.
 */

public interface AudioAddListener {
    void onAudioAddSuccess(List<Media> mediaList, String message);
    void onAudioAddFailure(String errorMessage);
}
