package com.multimedia.controller.interfaces;

import com.multimedia.controller.utils.Media;

import java.util.List;


public interface SuperUserAudioControls extends NormalUserAudioControls {
    void addAudioList(List<Media> mediaList, AudioAddListener audioAddListener);
    void deleteAudioList(List<Media> mediaList, AudioDeleteListener audioDeleteListener);
}
