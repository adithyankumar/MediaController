package com.multimedia.controller.interfaces;

import com.multimedia.controller.utils.Media;

import java.util.List;


public interface SuperUserVideoControls extends NormalUserVideoControls {
    void addVideoList(List<Media> mediaList, VideoAddListener videoAddListener);
    void deleteVideoList(List<Media> mediaList, VideoDeleteListener videoDeleteListener);
}
