package com.multimedia.controller.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;

import com.multimedia.controller.R;

import java.io.File;

public class UriParser {
    private final Context mContext;
    private final Uri mUri;


    public UriParser(Context context, String uriString) {
        mContext = context;
        mUri = Uri.parse(uriString);
    }

    public String getMimeType() {
        return mContext.getContentResolver().getType(mUri).split("/")[0];
    }

    public String getMediaFormat() {
        return mContext.getContentResolver().getType(mUri).split("/")[1];
    }


    public AudioInfo getAudioInfo() {
        AudioInfo audioInfo = null;

        ContentResolver musicResolver = mContext.getContentResolver();
        Cursor audioCursor = musicResolver.query(mUri, null, null, null, null);
        //iterate over results if valid
        if (audioCursor != null && audioCursor.moveToFirst()) {
            //get columns
            int titleColumn = audioCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = audioCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = audioCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int albumIdColumn = audioCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM_ID);
            int albumColumn = audioCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

            int durationColumn = audioCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_KEY);

            int bookmarkColumn = audioCursor.getColumnIndex(MediaStore.Audio.Media.BOOKMARK);

            audioInfo = new AudioInfo(audioCursor.getString(titleColumn),
                    audioCursor.getLong(idColumn),
                    audioCursor.getString(artistColumn),
                    audioCursor.getString(albumColumn),
                    audioCursor.getLong(albumIdColumn),
                    audioCursor.getLong(durationColumn),
                    audioCursor.getLong(bookmarkColumn));

            audioCursor.close();
        }

        return audioInfo;
    }

    public VideoInfo getVideoInfo() {
        VideoInfo videoInfo = null;

        ContentResolver musicResolver = mContext.getContentResolver();
        Cursor videoCursor = musicResolver.query(mUri, null, null, null, null);
        //iterate over results if valid
        if (videoCursor != null && videoCursor.moveToFirst()) {
            //get columns
            int titleColumn = videoCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = videoCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = videoCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);

            int albumColumn = videoCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

            int durationColumn = videoCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_KEY);

            int bookmarkColumn = videoCursor.getColumnIndex(MediaStore.Audio.Media.BOOKMARK);

            videoInfo = new VideoInfo(videoCursor.getString(titleColumn),
                    videoCursor.getLong(idColumn),
                    videoCursor.getString(artistColumn),
                    videoCursor.getString(albumColumn),
                    videoCursor.getLong(durationColumn),
                    videoCursor.getLong(bookmarkColumn));

            videoCursor.close();
        }
        return videoInfo;
    }

    public String getMediaPath() {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(mContext, mUri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(mUri)) {
                final String docId = DocumentsContract.getDocumentId(mUri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                } else {
                    return "/storage" + "/" + split[0] + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(mUri)) {


                /*final String id = DocumentsContract.getDocumentId(mUri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getColumnForImageData(mContext, contentUri, null, null);*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    final String id;
                    Cursor cursor = null;
                    try {
                        cursor = mContext.getContentResolver()
                                .query(mUri,
                                        new String[]{MediaStore.MediaColumns.DISPLAY_NAME},
                                        null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            String fileName = cursor.getString(0);
                            String path = Environment.
                                    getExternalStorageDirectory().toString() + "/Download/" + fileName;
                            if (!TextUtils.isEmpty(path)) {
                                return path;
                            }
                        }
                    } finally {
                        if (cursor != null)
                            cursor.close();
                    }
                    id = DocumentsContract.getDocumentId(mUri);
                    if (!TextUtils.isEmpty(id)) {
                        if (id.startsWith("raw:")) {
                            return id.replaceFirst("raw:", "");
                        }
                        String[] contentUriPrefixesToTry = new String[]{
                                "content://downloads/public_downloads",
                                "content://downloads/my_downloads"
                        };
                        for (String contentUriPrefix : contentUriPrefixesToTry) {
                            try {
                                final Uri contentUri = ContentUris.
                                        withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));
                                return getColumnForImageData(mContext, contentUri,
                                        null, null);
                            } catch (NumberFormatException e) {
                                //In Android 8 and Android P the id is not a number
                                return mUri.getPath()
                                        .replaceFirst("^/document/raw:", "")
                                        .replaceFirst("^raw:", "");
                            }
                        }
                    }
                }

            }
            // MediaProvider
            else if (isMediaDocument(mUri)) {
                final String docId = DocumentsContract.getDocumentId(mUri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if (MediaTypeEnum.isImage(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if (MediaTypeEnum.isVideo(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if (MediaTypeEnum.isAudio(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getColumnForImageData(mContext, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(mUri.getScheme())) {
            if (isGooglePhotosUri(mUri)) {
                return mUri.getLastPathSegment();
            }

            return getColumnForImageData(mContext, mUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(mUri.getScheme())) {
            return mUri.getPath();
        }

        return null;
    }

    public String getTitle() {
        String title = null;
        if (mUri != null) {
            if (mUri.getScheme().equals("content")) {
                Cursor cursor = mContext.getContentResolver().
                        query(mUri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        title = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            }
            if (title == null)
                title = new File(getMediaPath()).getName();
        }
        return title;
    }

    public Bitmap getThumbNail() {
        Bitmap thumbImage = null;
        String mimeType = getMimeType();
        String filePath = getMediaPath();
        if (MediaTypeEnum.isAudio(mimeType)) {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            try {
                retriever.setDataSource(filePath);
                byte[] art = retriever.getEmbeddedPicture();
                thumbImage = BitmapFactory
                        .decodeByteArray(art, 0, art.length);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                retriever.release();
            }

        } else if (MediaTypeEnum.isImage(mimeType)) {
            int rotation = 0;
            try {
                ExifInterface exifInterface = new ExifInterface(filePath);
                int exifRotation = exifInterface.
                        getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);
                switch (exifRotation) {
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotation = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotation = 270;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotation = 90;
                        break;
                }
                byte[] thumbNailBytes = exifInterface.getThumbnail();
                if (rotation != 0 && thumbNailBytes != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(thumbNailBytes,
                            0, thumbNailBytes.length - 1);
                    Matrix matrix = new Matrix();
                    matrix.setRotate(rotation);
                    thumbImage = Bitmap.createBitmap(bitmap, 0, 0,
                            bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                }else
                     thumbImage = BitmapFactory.decodeFile(getMediaPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (MediaTypeEnum.isVideo(mimeType)) {
            thumbImage = ThumbnailUtils.createVideoThumbnail(filePath,
                    MediaStore.Images.Thumbnails.MINI_KIND);
        } else {
            thumbImage = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_doc);
        }
        return thumbImage;
    }

    public static boolean isNotSupportFormat(String format) {
        return format.contains("wma");
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param mContext      The mContext.
     * @param mUri          The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getColumnForImageData(Context mContext, Uri mUri, String selection,
                                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = MediaStore.Images.Media.DATA;
        final String[] projection = {
                column
        };

        try {
            cursor = mContext.getContentResolver().query(mUri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param mUri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri mUri) {
        return "com.android.externalstorage.documents".equals(mUri.getAuthority());
    }

    /**
     * @param mUri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri mUri) {
        return "com.android.providers.downloads.documents".equals(mUri.getAuthority());
    }

    /**
     * @param mUri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri mUri) {
        return "com.android.providers.media.documents".equals(mUri.getAuthority());
    }


    /**
     * @param mUri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri mUri) {
        return "com.google.android.apps.photos.content".equals(mUri.getAuthority());
    }


    /**
     * @param mUri The Uri to check.
     * @return Whether the Uri authority is Google Docs.
     */
    private static boolean isGoogleDocsUri(Uri mUri) {
        return "com.google.android.apps.docs.storage".equals(mUri.getAuthority());
    }
}
