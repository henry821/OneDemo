package com.demo.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.bean.TitleBean;
import com.demo.one.R;
import com.demo.one.databinding.ItemTitleListBinding;

import java.util.List;

/**
 * Description 首页列表Adapter
 * <br>Author wanghengwei
 * <br>Date   2018/11/19 16:58
 */
public class TitleListAdapter extends RecyclerView.Adapter<TitleListAdapter.MyViewHolder> {

    private Context context;
    private List<TitleBean> mDataList;
    private OnItemClickListener itemClickListener;

    public TitleListAdapter(Context context, List<TitleBean> mDataList, OnItemClickListener itemClickListener) {
        this.context = context;
        this.mDataList = mDataList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTitleListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_title_list, parent, false);
        MyViewHolder holder = new MyViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(mDataList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ItemTitleListBinding binding;

        MyViewHolder(View itemView) {
            super(itemView);
        }

        void setBinding(ItemTitleListBinding binding) {
            this.binding = binding;
        }

        void bind(TitleBean titleBean, OnItemClickListener itemClickListener) {
            binding.setTitleBean(titleBean);
            binding.setOnRvItemClickListener(itemClickListener);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TitleBean bean);
    }

}
