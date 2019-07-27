package com.multimedia.controller.utils;

public enum MediaTypeEnum {
    IMAGE("image"), AUDIO("audio"), VIDEO("video"), DOC ("document");
    private final String mimeType;
    public static String[] docMimeType = {"application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
            "application/vnd.ms-powerpoint",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
            "text/plain",
            "application/pdf"};

    MediaTypeEnum(String mimeType){
        this.mimeType = mimeType;
    }

    @Override
    public String toString() {
        return mimeType;
    }

    public String getValue(){
         if (mimeType.equals(DOC.toString())){
             String mimeTypesStr = "";
             for (String mimeType : docMimeType) {
                 mimeTypesStr += mimeType + "|";
             }
             return mimeTypesStr;
         }
         return mimeType;
    }
    public static boolean isAudio(String mimeType) {
        return mimeType!=null && MediaTypeEnum.AUDIO.getValue().contains(mimeType);
    }

    public static boolean isVideo(String mimeType) {
        return mimeType!=null && MediaTypeEnum.VIDEO.getValue().contains(mimeType);
    }

    public static boolean isImage(String mimeType) {
        return mimeType!=null && MediaTypeEnum.IMAGE.getValue().contains(mimeType);
    }

    public static boolean isDoc(String mimeType){
         return mimeType!=null && MediaTypeEnum.DOC.getValue().contains(mimeType);
    }
}
