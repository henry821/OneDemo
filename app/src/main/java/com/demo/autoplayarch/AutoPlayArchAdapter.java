package com.demo.autoplayarch;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by hengwei on 2020/9/18.
 */
public class AutoPlayArchAdapter extends RecyclerView.Adapter<AutoPlayArchAdapter.AutoPlayArchHolder> {

    @NonNull
    @Override
    public AutoPlayArchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AutoPlayItemView itemView = new AutoPlayItemView(parent.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                450);
        itemView.setLayoutParams(layoutParams);
        return new AutoPlayArchHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public void onBindViewHolder(@NonNull AutoPlayArchHolder holder, int position) {
        ((AutoPlayItemView) holder.itemView).setPosition(position + "");
    }

    static class AutoPlayArchHolder extends RecyclerView.ViewHolder {

        public AutoPlayArchHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
