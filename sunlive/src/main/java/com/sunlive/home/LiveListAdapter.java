package com.sunlive.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.sunlive.R;
import com.sunlive.data.LiveInfo;

import java.util.List;

public class LiveListAdapter extends Adapter<LiveListAdapter.LiveListHolder> {

    private List<LiveInfo> mDataList;

    public LiveListAdapter(List<LiveInfo> mDataList) {
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public LiveListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LiveListHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_live_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LiveListHolder holder, int position) {
        holder.tvTitle.setText(mDataList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setDataList(List<LiveInfo> liveInfoList){
        mDataList.clear();
        mDataList.addAll(liveInfoList);
        notifyDataSetChanged();
    }

    static class LiveListHolder extends RecyclerView.ViewHolder {

        private ImageView ivCover;
        private TextView tvTitle;

        public LiveListHolder(@NonNull View itemView) {
            super(itemView);

            ivCover = itemView.findViewById(R.id.iv_cover);
            tvTitle = itemView.findViewById(R.id.tv_title);

        }
    }
}