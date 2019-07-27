package com.multimedia.controller.utils;

/**
 * Created by AKrishnakuma on 7/15/2019.
 */

public class AudioInfo {
    private final String title;
    private final long audioId;
    private final String artist;
    private final String albumn;
    private final long albumnId;
    private final long duration;
    private final long bookmark;

    public AudioInfo(String title, long audioId, String artist, String albumn, long albumnId,
                     long duration, long bookmark) {
        this.title = title;
        this.audioId = audioId;
        this.artist = artist;
        this.albumn = albumn;
        this.albumnId = albumnId;
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

    public long getAlbumnId() {
        return albumnId;
    }

    public long getDuration(){
        return duration;
    }

    public String getAlbumn() {
        return albumn;
    }

    public long getBookmark() {
        return bookmark;
    }
}
