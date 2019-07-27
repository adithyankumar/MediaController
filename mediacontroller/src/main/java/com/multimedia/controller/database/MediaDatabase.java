package com.multimedia.controller.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.multimedia.controller.utils.Media;

@Database(entities = {Media.class}, version = 1, exportSchema = false)
public abstract class MediaDatabase extends RoomDatabase {


    public abstract MediaDao getMediaDao();
    private static MediaDatabase sInstance;
    public static Context mediaControllerContext = null;
    public static synchronized  MediaDatabase getInstance(Context context){
        if (mediaControllerContext == null)
            mediaControllerContext = context;
        if (sInstance == null)
             sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MediaDatabase.class, "media_db")
                     .fallbackToDestructiveMigration()
                     .allowMainThreadQueries()
                     .build();
        return sInstance;
    }
}
