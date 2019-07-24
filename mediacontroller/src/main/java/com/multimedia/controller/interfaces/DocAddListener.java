package com.multimedia.controller.interfaces;

import com.multimedia.controller.utils.Media;

import java.util.List;

public interface DocAddListener {
    void onDocAddSuccess(List<Media> mediaList, String message);
    void onDocAddFailure(String errorMessage);
}
