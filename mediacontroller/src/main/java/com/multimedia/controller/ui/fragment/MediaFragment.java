package com.multimedia.controller.ui.fragment;


import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.multimedia.controller.BuildConfig;
import com.multimedia.controller.R;
import com.multimedia.controller.interfaces.AudioAddListener;
import com.multimedia.controller.interfaces.AudioDeleteListener;
import com.multimedia.controller.interfaces.AudioFetchListener;
import com.multimedia.controller.interfaces.DocAddListener;
import com.multimedia.controller.interfaces.DocDeleteListener;
import com.multimedia.controller.interfaces.DocFetchListener;
import com.multimedia.controller.interfaces.ImageAddListener;
import com.multimedia.controller.interfaces.ImageDeleteListener;
import com.multimedia.controller.interfaces.ImageFetchListener;
import com.multimedia.controller.interfaces.VideoAddListener;
import com.multimedia.controller.interfaces.VideoDeleteListener;
import com.multimedia.controller.interfaces.VideoFetchListener;
import com.multimedia.controller.managers.SuperUserManager;
import com.multimedia.controller.ui.OnListFragmentInteractionListener;
import com.multimedia.controller.ui.PlayerActivity;
import com.multimedia.controller.ui.adapter.MediaAdapter;
import com.multimedia.controller.utils.CheckPermissionUtil;
import com.multimedia.controller.utils.Constants;
import com.multimedia.controller.utils.Media;
import com.multimedia.controller.utils.MediaTypeEnum;
import com.multimedia.controller.utils.SnackBarUtils;
import com.multimedia.controller.utils.UriParser;
import com.multimedia.controller.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.multimedia.controller.utils.Constants.PICK_MULTI_MEDIA_MULTIPLE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MediaFragment extends Fragment implements OnListFragmentInteractionListener,
        AudioDeleteListener, VideoDeleteListener, ImageDeleteListener, ImageAddListener,
        VideoAddListener, AudioAddListener, AudioFetchListener, VideoFetchListener,
        ImageFetchListener, DocDeleteListener, DocAddListener, DocFetchListener {


    private static final String TAG = MediaFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private TextView tvNoDataFound;
    private MediaAdapter mediaAdapter;
    private List<Media> mediaArrayList = new ArrayList<>();
    private Context mContext;
    private FloatingActionButton fab;
    private boolean isSuperUser = true;
    private final List<Media> tempList = new ArrayList<>();
    private boolean itemDeleted;
    private Toolbar toolbar;
    private MediaTypeEnum mimeType = MediaTypeEnum.IMAGE;
    private ProgressBar progressBar;

    public MediaFragment() {
        // Required empty public constructor
    }


    public static MediaFragment newInstance(boolean isSuperUser, MediaTypeEnum mediaTypeEnum) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.IS_SUPER_USER, isSuperUser);
        bundle.putSerializable(Constants.MIME_TYPE, mediaTypeEnum);
        MediaFragment mediaFragment = new MediaFragment();
        mediaFragment.setArguments(bundle);
        return mediaFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_media, container, false);

        if (String.valueOf(getArguments().getBoolean(Constants.IS_SUPER_USER)) != null
                && getArguments().getSerializable(Constants.MIME_TYPE) != null) {
            isSuperUser = getArguments().getBoolean(Constants.IS_SUPER_USER);
            mimeType = (MediaTypeEnum) getArguments().getSerializable(Constants.MIME_TYPE);
        }

        toolbar = view.findViewById(R.id.toolbar);
        progressBar = view.findViewById(R.id.progress_bar);
        tvNoDataFound = view.findViewById(R.id.tv_no_data_found);
        recyclerView = view.findViewById(R.id.rv_media);
        fab = view.findViewById(R.id.fab);

        toolbar.setTitle(Utils.toCapitialize(mimeType.toString()));

        mediaAdapter = new MediaAdapter(this, isSuperUser);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,
                Constants.COLUMN_COUNT));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mediaAdapter);


        fab.setOnClickListener(v -> {
            if (tempList.size() == 0) {
                CheckPermissionUtil.checkReadSd((AppCompatActivity) getActivity(), success -> {
                    if (success) {
                        Intent intent = new Intent();
                        if (MediaTypeEnum.DOC == mimeType){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                intent.setType(MediaTypeEnum.docMimeType.length == 1
                                        ? MediaTypeEnum.docMimeType[0]
                                        : "*/*");
                                if (MediaTypeEnum.docMimeType.length > 0) {
                                    intent.putExtra(Intent.EXTRA_MIME_TYPES, MediaTypeEnum.docMimeType);
                                }
                            } else {
                                String mimeTypesStr = "";
                                for (String mimeType : MediaTypeEnum.docMimeType) {
                                    mimeTypesStr += mimeType + "|";
                                }
                                intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
                            }
                        }else {
                            intent.setType(String.format(getString(R.string.mime_type), mimeType.getValue()));
                        }
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(Intent.createChooser(intent, "Select " + mimeType),
                                PICK_MULTI_MEDIA_MULTIPLE);
                    } else {
                        Toast.makeText(mContext, getString(R.string.request_reason), Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                itemDeleted = true;
                deleteItems();
            }
        });

        fetchItems();

        fab.setVisibility(isSuperUser ? View.VISIBLE : View.GONE);

        return view;
    }

    private void deleteItems() {
        progressBar.setVisibility(View.VISIBLE);

        if (MediaTypeEnum.isAudio(mimeType.getValue())) {
            SuperUserManager.getInstance(mContext).deleteAudioList(tempList, this);
        } else if (MediaTypeEnum.isVideo(mimeType.getValue())) {
            SuperUserManager.getInstance(mContext).deleteVideoList(tempList, this);
        } else if (MediaTypeEnum.isImage(mimeType.getValue())) {
            SuperUserManager.getInstance(mContext).deleteImageList(tempList, this);
        } else {
            SuperUserManager.getInstance(mContext).deleteDocList(tempList, this);
        }
    }

    private void addItems(ArrayList<Media> tempMediaList) {
        progressBar.setVisibility(View.VISIBLE);
        if (MediaTypeEnum.isAudio(mimeType.getValue())) {
            SuperUserManager.getInstance(mContext).addAudioList(tempMediaList, this);
        } else if (MediaTypeEnum.isVideo(mimeType.getValue())) {
            SuperUserManager.getInstance(mContext).addVideoList(tempMediaList, this);
        } else if (MediaTypeEnum.isImage(mimeType.getValue())) {
            SuperUserManager.getInstance(mContext).addImageList(tempMediaList, this);
        } else {
            SuperUserManager.getInstance(mContext).addDocList(tempMediaList, this);
        }
    }

    private void fetchItems() {
        progressBar.setVisibility(View.VISIBLE);
        if (MediaTypeEnum.isAudio(mimeType.getValue())) {
            SuperUserManager.getInstance(mContext).getAudioList(this);
        } else if (MediaTypeEnum.isVideo(mimeType.getValue())) {
            SuperUserManager.getInstance(mContext).getVideoList(this);
        } else if (MediaTypeEnum.isImage(mimeType.getValue())) {
            SuperUserManager.getInstance(mContext).getImageList(this);
        } else {
            SuperUserManager.getInstance(mContext).getDocList(this);
        }
    }

    private void setItems(List<Media> media) {

        progressBar.setVisibility(View.GONE);

        mediaArrayList = media;

        if (mediaArrayList != null) {
            Log.i(TAG, "setItems: observe mediaArrayList =" + mediaArrayList);
            boolean isListEmpty = (mediaArrayList.size() == 0);
            mediaAdapter = new MediaAdapter(MediaFragment.this, isSuperUser);
            mediaAdapter.setMediaList(mediaArrayList);
            recyclerView.setAdapter(mediaAdapter);
            tvNoDataFound.setVisibility(isListEmpty ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(isListEmpty ? View.GONE : View.VISIBLE);
        }

        tvNoDataFound.setText(String.format(mContext.getString(R.string.no_item_found), mimeType));
        if (itemDeleted) {
            tempList.clear();
            itemDeleted = false;
        }
        changeFab();
    }

    private void changeFab() {
        boolean isItemSelected = tempList.size() != 0;
        fab.setImageResource(isItemSelected
                ? R.drawable.ic_delete
                : R.drawable.ic_add);

        toolbar.setTitle(isItemSelected
                ? String.format(getString(R.string.item_selected), tempList.size())
                : Utils.toCapitialize(mimeType.toString()));
    }

    @Override
    public void onListFragmentInteraction(boolean isLongClick, Media item) {

        if (isLongClick) {

            if (!tempList.contains(item))
                tempList.add(item);
            else
                tempList.remove(item);

            changeFab();

        } else {
            if (MediaTypeEnum.isImage(item.getMimeType())) {
                ImageViewFragment imageViewFragment = new ImageViewFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constants.MEDIA_LIST, (ArrayList<? extends Parcelable>) mediaArrayList);
                bundle.putInt(Constants.MEDIA_IMAGE_CLICKED_POS, mediaArrayList.indexOf(item));
                imageViewFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, imageViewFragment)
                        .addToBackStack(null)
                        .commit();
            } else if (MediaTypeEnum.isDoc(item.getMimeType())) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri mediaUri = FileProvider.getUriForFile(
                        mContext,
                        BuildConfig.APPLICATION_ID + ".provider", new File(item.getMediaPath()));
                intent.setDataAndType(mediaUri,
                        /*String.format(getString(R.string.mime_type), item.getMimeType())*/
                        item.getMimeType() + "/" + item.getMediaFormat()
                );
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    SnackBarUtils.showError(mContext, R.string.no_app_available);
                }
            } else {
                if (MediaTypeEnum.isAudio(item.getMimeType()) &&
                        UriParser.isNotSupportFormat(item.getMediaFormat())) {
                    SnackBarUtils.showError(mContext,
                            R.string.format_not_supported);
                    return;
                }

                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra(Constants.MEDIA_ITEM, item);
                startActivity(intent);

            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (/*requestCode == PICK_MULTI_MEDIA_MULTIPLE && */resultCode == RESULT_OK && data != null) {
                ArrayList<Media> tempMediaList = new ArrayList<>();
                if (data.getData() != null) {
                    Uri uri = data.getData();
                    tempMediaList.add(new Media(mContext, uri.toString(), mimeType.toString()));
                } else if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        Uri uri = mClipData.getItemAt(i).getUri();
                        tempMediaList.add(new Media(mContext, uri.toString(), mimeType.toString()));
                    }
                }

                if (tempMediaList != null) {
                    addItems(tempMediaList);
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "onActivityResult: " + e.getMessage());
            SnackBarUtils.showError(mContext, e.getMessage());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void refreshItems(List<Media> mediaList, boolean isAdded) {
        if (isAdded)
            mediaArrayList.addAll(mediaList);
        else
            mediaArrayList.removeAll(mediaList);
        setItems(mediaArrayList);
    }


    @Override
    public void onAudioDeleteSuccess(List<Media> mediaList, String message) {
        progressBar.setVisibility(View.GONE);
        SnackBarUtils.showSuccess(mContext, message);
        refreshItems(mediaList, false);
    }

    @Override
    public void onAudioDeleteFailure(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        SnackBarUtils.showError(mContext, errorMessage);
    }

    @Override
    public void onVideoDeleteSuccess(List<Media> mediaList, String message) {
        progressBar.setVisibility(View.GONE);
        SnackBarUtils.showSuccess(mContext, message);
        refreshItems(mediaList, false);
    }

    @Override
    public void onVideoDeleteFailure(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        SnackBarUtils.showError(mContext, errorMessage);
    }

    @Override
    public void onImageDeleteSuccess(List<Media> mediaList, String message) {
        progressBar.setVisibility(View.GONE);
        SnackBarUtils.showSuccess(mContext, message);
        refreshItems(mediaList, false);
    }


    @Override
    public void onImageDeleteFailure(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        SnackBarUtils.showError(mContext, errorMessage);
    }

    @Override
    public void onDocDeleteSuccess(List<Media> mediaList, String message) {
        progressBar.setVisibility(View.GONE);
        SnackBarUtils.showSuccess(mContext, message);
        refreshItems(mediaList, false);

    }

    @Override
    public void onDocDeleteFailure(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        SnackBarUtils.showError(mContext, errorMessage);
    }

    @Override
    public void onImageAddSuccess(List<Media> mediaList, String message) {
        progressBar.setVisibility(View.GONE);
        SnackBarUtils.showSuccess(mContext, message);
        refreshItems(mediaList, true);
    }

    @Override
    public void onImageAddFailure(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        SnackBarUtils.showError(mContext, errorMessage);
    }

    @Override
    public void onVideoAddSuccess(List<Media> mediaList, String message) {
        progressBar.setVisibility(View.GONE);
        SnackBarUtils.showSuccess(mContext, message);
        refreshItems(mediaList, true);
    }

    @Override
    public void onVideoAddFailure(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        SnackBarUtils.showError(mContext, errorMessage);
    }

    @Override
    public void onAudioAddSuccess(List<Media> mediaList, String message) {
        progressBar.setVisibility(View.GONE);
        SnackBarUtils.showSuccess(mContext, message);
        refreshItems(mediaList, true);
    }

    @Override
    public void onAudioAddFailure(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        SnackBarUtils.showError(mContext, errorMessage);
    }

    @Override
    public void onDocAddSuccess(List<Media> mediaList, String message) {
        progressBar.setVisibility(View.GONE);
        SnackBarUtils.showSuccess(mContext, message);
        refreshItems(mediaList, true);
    }

    @Override
    public void onDocAddFailure(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        SnackBarUtils.showError(mContext, errorMessage);
    }

    @Override
    public void onAudioFetchSuccess(List<Media> imageList) {
        setItems(imageList);
    }

    @Override
    public void onAudioFetchFailure(String errorMessage) {
        setItems(null);
    }

    @Override
    public void onVideoFetchSuccess(List<Media> imageList) {
        setItems(imageList);
    }

    @Override
    public void onVideoFetchFailure(String errorMessage) {
        setItems(null);
    }

    @Override
    public void onImageFetchSuccess(List<Media> imageList) {
        setItems(imageList);
    }

    @Override
    public void onImageFetchFailure(String errorMessage) {
        setItems(null);
    }


    @Override
    public void onDocFetchSuccess(List<Media> imageList) {
        setItems(imageList);
    }

    @Override
    public void onDocFetchFailure(String errorMessage) {
        setItems(null);
    }

}
