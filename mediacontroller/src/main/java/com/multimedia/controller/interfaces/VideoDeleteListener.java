package com.multimedia.controller.interfaces;

import com.multimedia.controller.utils.Media;

import java.util.List;

/**
 * Created by AKrishnakuma on 6/25/2019.
 */

public interface VideoDeleteListener {
    void onVideoDeleteSuccess(List<Media> mediaList, String message);
    void onVideoDeleteFailure(String errorMessage);
}
