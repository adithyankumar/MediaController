package com.multimedia.controller.interfaces;

import com.multimedia.controller.utils.Media;

import java.util.List;

/**
 * Created by AKrishnakuma on 6/25/2019.
 */

public interface ImageAddListener {
    /**
     * @param mediaList list of media added
     * @param message success message
     */
    void onImageAddSuccess(List<Media> mediaList, String message);

    /**
     * @param errorMessage error message
     */
    void onImageAddFailure(String errorMessage);
}
