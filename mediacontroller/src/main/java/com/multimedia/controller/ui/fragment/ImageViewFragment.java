package com.multimedia.controller.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.multimedia.controller.ui.adapter.GalleryAdapter;
import com.multimedia.controller.utils.Constants;
import com.multimedia.controller.utils.Media;
import com.multimedia.controller.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageViewFragment extends Fragment {

    private List<Media> mediaList = new ArrayList<>();
    private int clickedItemPos = 0;

    public ImageViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_image, container, false);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.getParcelableArrayList(Constants.MEDIA_LIST) != null &&
                String.valueOf(bundle.getInt(Constants.MEDIA_IMAGE_CLICKED_POS)) != null ) {
            mediaList = bundle.getParcelableArrayList(Constants.MEDIA_LIST);
            clickedItemPos = bundle.getInt(Constants.MEDIA_IMAGE_CLICKED_POS);
        }

        RecyclerView rvGalleryList = view.findViewById(R.id.rv_gallery_list);

        GalleryAdapter adapter = new GalleryAdapter(getContext(), mediaList);
        rvGalleryList.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        rvGalleryList.setHasFixedSize(true);
        rvGalleryList.setAdapter(adapter);


        rvGalleryList.scrollToPosition(clickedItemPos);

        return view;
    }

}
