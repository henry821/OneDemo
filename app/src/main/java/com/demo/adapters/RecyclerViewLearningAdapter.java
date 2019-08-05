package com.demo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baselibrary.utils.LogUtil;
import com.demo.one.R;

import java.util.List;

/**
 * Description RecyclerView学习类Adapter
 * Author wanghengwei
 * Date   2019/8/1 15:13
 */
public class RecyclerViewLearningAdapter extends RecyclerView.Adapter<RecyclerViewLearningAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> mDataList;

    public RecyclerViewLearningAdapter(Context mContext, List<String> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LogUtil.d("onCreateViewHolder, viewType = " + viewType);
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_view_learning, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LogUtil.e("onBindView, position = " + position);
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#0000ff"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ff0000"));
        }
        holder.tvNum.setText(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvNum;

        MyViewHolder(View itemView) {
            super(itemView);

            tvNum = itemView.findViewById(R.id.tv_num);
        }
    }
}
