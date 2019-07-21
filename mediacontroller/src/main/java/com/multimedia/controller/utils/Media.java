package com.multimedia.controller.utils;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AKrishnakuma on 6/6/2019.
 */
@Entity(tableName = "media_table")
public class Media implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "uri_string")
    private String uriString;
    @ColumnInfo(name = "view_count")
    private int viewCount;
    @ColumnInfo(name = "mime_type")
    private String mimeType;
    @ColumnInfo(name = "media_path")
    private String mediaPath;
    private String title;
    @Ignore
    private Bitmap thumbNail;
    @Ignore
    private UriParser uriParser;
    public Media(){

    }

    public Media(Context context, String uriString) {
        uriParser = new UriParser(context, uriString);
        setUriString(uriString);
        setViewCount(0);
        setMimeType(uriParser.getMimeType());
        setTitle(uriParser.getTitle());
        setMediaPath(uriParser.getMediaPath());
    }


    public String getUriString() {
        return uriString;
    }

    public int getId() {
        return id;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setUriString(String uriString) {
        this.uriString = uriString;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public Bitmap getThumbNail(Context context){
        if (uriParser == null){
            uriParser = new UriParser(context, getUriString());
        }
        return uriParser.getThumbNail();
    }
    public String getTitle(){
        return title;
    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public Media createFromParcel(Parcel source) {
            return new Media(source);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
    public Media(Parcel in){
        uriString = in.readString();
        viewCount = in.readInt();
        mimeType = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uriString);
        dest.writeInt(viewCount);
        dest.writeString(mimeType);
    }
}
