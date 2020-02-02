package com.demo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.beans.TitleBean;
import com.demo.utils.DensityUtil;

import java.util.List;

/**
 * Description 首页列表Adapter,不使用DataBinding
 * <br>Author wanghengwei
 * <br>Date   2018/11/19 16:58
 */
public class TitleListNormalAdapter extends RecyclerView.Adapter<TitleListNormalAdapter.MyViewHolder> {

    private Context mContext;
    private List<TitleBean> mDataList;
    private OnItemClickListener itemClickListener;
    private int mPadding;

    public TitleListNormalAdapter(Context context, List<TitleBean> mDataList, OnItemClickListener itemClickListener) {
        mContext = context;
        this.mDataList = mDataList;
        this.itemClickListener = itemClickListener;
        mPadding = DensityUtil.dip2px(context, 10);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView tvContent = new TextView(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvContent.setLayoutParams(layoutParams);
        tvContent.setPadding(mPadding, mPadding, mPadding, mPadding);
        tvContent.setTextSize(22);
        tvContent.setGravity(Gravity.CENTER);
        return new MyViewHolder(tvContent, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ((TextView) holder.itemView).setText(mDataList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private OnItemClickListener mItemClickListener;


        MyViewHolder(final View itemView, OnItemClickListener itemClickListener) {
            super(itemView);
            mItemClickListener = itemClickListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(getAdapterPosition(), mDataList.get(getAdapterPosition()));
                    }
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position, TitleBean bean);
    }

}
