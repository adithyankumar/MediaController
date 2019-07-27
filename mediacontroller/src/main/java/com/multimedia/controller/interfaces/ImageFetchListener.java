package com.multimedia.controller.interfaces;

import com.multimedia.controller.utils.Media;

import java.util.List;

/**
 * Created by AKrishnakuma on 6/25/2019.
 */

public interface ImageFetchListener {
    void onImageFetchSuccess(List<Media> imageList);
    void onImageFetchFailure(String errorMessage);
}
