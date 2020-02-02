package com.demo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.beans.TitleBean;
import com.demo.one.R;
import com.demo.one.databinding.ItemTitleListDataBindingBinding;

import java.util.List;

/**
 * Description 首页列表Adapter,使用DataBinding
 * <br>Author wanghengwei
 * <br>Date   2018/11/19 16:58
 */
public class TitleListDataBindingAdapter extends RecyclerView.Adapter<TitleListDataBindingAdapter.MyViewHolder> {

    private Context context;
    private List<TitleBean> mDataList;
    private OnItemClickListener itemClickListener;

    public TitleListDataBindingAdapter(Context context, List<TitleBean> mDataList, OnItemClickListener itemClickListener) {
        this.context = context;
        this.mDataList = mDataList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTitleListDataBindingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context)
                , R.layout.item_title_list_data_binding, parent, false);
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

        private ItemTitleListDataBindingBinding binding;

        MyViewHolder(View itemView) {
            super(itemView);
        }

        void setBinding(ItemTitleListDataBindingBinding binding) {
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
