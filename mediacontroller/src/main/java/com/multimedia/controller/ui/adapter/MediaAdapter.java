package com.multimedia.controller.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.multimedia.controller.ui.OnListFragmentInteractionListener;
import com.multimedia.controller.utils.Media;
import com.multimedia.controller.utils.MediaTypeEnum;
import com.multimedia.controller.R;

import java.util.ArrayList;
import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder> {

    private static final String TAG = MediaAdapter.class.getSimpleName();
    private List<Media> mediaList = new ArrayList<>();
    private Context mContext;
    private final OnListFragmentInteractionListener listener;
    private boolean isSuperUser;

    public MediaAdapter(OnListFragmentInteractionListener listener, boolean isSuperUser){
        this.listener = listener;
        this.isSuperUser = isSuperUser;
    }
    public void setMediaList(List<Media> mediaList){
        this.mediaList = mediaList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.media_item, parent, false);
        return new MediaViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder holder, int position) {
        Media media =  mediaList.get(position);
        boolean isSelected = Boolean.parseBoolean(String.valueOf(holder.itemView.getTag()));
        holder.selectionView.setVisibility(isSelected ? View.VISIBLE : View.GONE);
        Bitmap bitmap = media.getThumbNail(mContext);
        holder.img.setScaleType( bitmap == null || MediaTypeEnum.isDoc(media.getMimeType())
                ? ImageView.ScaleType.FIT_CENTER
                : ImageView.ScaleType.CENTER_CROP);
        Glide.with(mContext)
                    .load(bitmap)
                    .thumbnail()
                    .error(R.drawable.ic_music)
                    .into(holder.img);

        holder.tvTitle.setText(media.getTitle());
        holder.tvTitle.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    class MediaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final ImageView img;
        private final View selectionView;
        private final TextView tvTitle;
        public MediaViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            selectionView = itemView.findViewById(R.id.selection_view);
            tvTitle = itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (Boolean.parseBoolean(String.valueOf(itemView.getTag()))){
                selectView();
                return;
            }
            listener.onListFragmentInteraction(false, mediaList.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            if (isSuperUser) selectView();
            return true;
        }
        private void selectView(){
            boolean isSelected = !Boolean.parseBoolean(String.valueOf(itemView.getTag()));
            itemView.setTag(String.valueOf(isSelected));
            listener.onListFragmentInteraction(true, mediaList.get(getAdapterPosition()));
            onBindViewHolder(this, getAdapterPosition());
        }
    }
}
