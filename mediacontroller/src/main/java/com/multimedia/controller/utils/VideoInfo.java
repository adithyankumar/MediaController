package com.multimedia.controller.utils;

/**
 * Created by AKrishnakuma on 7/15/2019.
 */

public class VideoInfo {
    private final String title;
    private final long audioId;
    private final String artist;
    private final String album;
    private final long duration;
    private final long bookmark;

    public VideoInfo(String title, long audioId, String artist, String album, long duration,
                     long bookmark) {
        this.title = title;
        this.audioId = audioId;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.bookmark = bookmark;
    }

    public String getTitle() {
        return title;
    }

    public long getAudioId() {
        return audioId;
    }

    public String getArtist() {
        return artist;
    }

    public long getDuration(){
        return duration;
    }

    public String getAlbum() {
        return album;
    }

    public long getBookmark() {
        return bookmark;
    }
}
