package com.multimedia.controller.interfaces;

import com.multimedia.controller.utils.Media;
import java.util.List;

/**
 * Created by AKrishnakuma on 6/20/2019.
 */

public interface SuperNormalImageUserControls extends NormalImageUserControls {
    void addImageList(List<Media> mediaList, ImageAddListener imageAddListener);
    void deleteImageList(List<Media> mediaList, ImageDeleteListener imageDeleteListener);
}
