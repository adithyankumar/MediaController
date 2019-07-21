package com.multimedia.controller.utils;

/**
 * Created by AKrishnakuma on 6/20/2019.
 */

public enum MediaTypeEnum {
    IMAGE("image"), AUDIO("audio"), VIDEO("video");
    private final String mimeType;
     MediaTypeEnum(String mimeType){
        this.mimeType = mimeType;
    }

    @Override
    public String toString() {
        return mimeType;
    }

    public static boolean isAudio(String mimeType) {
        return mimeType.equals(MediaTypeEnum.AUDIO.toString());
    }

    public static boolean isVideo(String mimeType) {
        return mimeType.equals(MediaTypeEnum.VIDEO.toString());
    }

    public static boolean isImage(String mimeType) {
        return mimeType.equals(MediaTypeEnum.IMAGE.toString());
    }

}
