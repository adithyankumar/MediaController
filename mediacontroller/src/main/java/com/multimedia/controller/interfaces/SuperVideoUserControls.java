package com.multimedia.controller.interfaces;

import com.multimedia.controller.utils.Media;

import java.util.List;

/**
 * Created by AKrishnakuma on 6/20/2019.
 */

public interface SuperVideoUserControls extends NormalVideoUserControls {
    void addVideoList(List<Media> mediaList, VideoAddListener videoAddListener);
    void deleteVideoList(List<Media> mediaList, VideoDeleteListener videoDeleteListener);
}
