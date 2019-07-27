package com.multimedia.controller.interfaces;

import com.multimedia.controller.utils.Media;
import java.util.List;

public interface SuperUserImageControls extends NormalUserImageControls {
    void addImageList(List<Media> mediaList, ImageAddListener imageAddListener);
    void deleteImageList(List<Media> mediaList, ImageDeleteListener imageDeleteListener);
}
