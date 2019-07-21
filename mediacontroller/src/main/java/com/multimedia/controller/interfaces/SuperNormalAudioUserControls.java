package com.multimedia.controller.interfaces;

import com.multimedia.controller.utils.Media;

import java.util.List;

/**
 * Created by AKrishnakuma on 6/20/2019.
 */

public interface SuperNormalAudioUserControls extends NormalAudioUserControls {
    void addAudioList(List<Media> mediaList, AudioAddListener audioAddListener);
    void deleteAudioList(List<Media> mediaList, AudioDeleteListener audioDeleteListener);
}
