package com.multimedia.controller.interfaces;

import com.multimedia.controller.utils.Media;

import java.util.List;

public interface DocFetchListener {
    void onDocFetchSuccess(List<Media> imageList);
    void onDocFetchFailure(String errorMessage);
}
