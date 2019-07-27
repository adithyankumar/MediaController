package com.multimedia.controller.interfaces;

import com.multimedia.controller.utils.Media;

import java.util.List;

public interface SuperUserDocControls extends NormalUserDocControls{
    void addDocList(List<Media> mediaList, DocAddListener docAddListener);
    void deleteDocList(List<Media> mediaList, DocDeleteListener docDeleteListener);
}
