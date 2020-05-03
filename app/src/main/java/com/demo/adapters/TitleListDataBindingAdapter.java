package com.demo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.beans.TitleBean;
import com.demo.one.R;

import java.util.List;

/**
 * Description 首页列表Adapter,使用DataBinding
 * <br>Author wanghengwei
 * <br>Date   2018/11/19 16:58
 */
public class TitleListDataBindingAdapter extends RecyclerView.Adapter<TitleListDataBindingAdapter.MyViewHolder> {

    private Context context;
    private List<TitleBean> mDataList;

    public TitleListDataBindingAdapter(Context context, List<TitleBean> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).
                inflate(R.layout.item_title_list_data_binding, parent, false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tvTitle.setText(mDataList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(mDataList.get(position).getNavAction());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }

    }

}
