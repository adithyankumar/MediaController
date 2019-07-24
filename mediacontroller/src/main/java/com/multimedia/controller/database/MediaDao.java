package com.multimedia.controller.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.multimedia.controller.utils.Media;

import java.util.List;

@Dao
public interface MediaDao {
    @Insert
    void insert(List<Media> media);

    @Update
    void update(Media media);

    @Delete
    void delete(List<Media> media);

    @Query("select * from media_table Order By view_count Desc")
    List<Media> getMediaList();

    @Query("select * from media_table where media_type = :mediaType Order By view_count Desc")
    //@Query("select * from media_table Order By viewCount Desc")
    List<Media> getMediaList(String mediaType);
}

