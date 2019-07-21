package com.multimedia.controller.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.multimedia.controller.ui.OnListFragmentInteractionListener;
import com.multimedia.controller.utils.Media;
import com.temp.mediacontroller.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AKrishnakuma on 6/15/2019.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryAdapterViewHolder> {

    private List<Media> mediaList = new ArrayList<>();
    private final Context mContext;
    private OnListFragmentInteractionListener listener;

    public GalleryAdapter(Context mContext, List<Media> mediaList) {
        this.mediaList = mediaList;
        this.mContext = mContext;
    }

    public void setOnListFragmentInteractionListener(OnListFragmentInteractionListener
                                                             listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public GalleryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.gallery_item,viewGroup,false);
        return new GalleryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapterViewHolder holder, int position) {
        Media media =  mediaList.get(position);
        Glide.with(mContext)
                .load(media.getMediaPath())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    class GalleryAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView imageView;
        public GalleryAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null)listener.onListFragmentInteraction(false, mediaList.get(getAdapterPosition()));
        }
    }
}
