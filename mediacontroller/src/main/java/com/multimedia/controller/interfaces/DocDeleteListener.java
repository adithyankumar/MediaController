package com.multimedia.controller.interfaces;

import com.multimedia.controller.utils.Media;

import java.util.List;

public interface DocDeleteListener {
    void onDocDeleteSuccess(List<Media> mediaList, String message);
    void onDocDeleteFailure(String errorMessage);
}
