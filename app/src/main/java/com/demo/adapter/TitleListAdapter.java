package com.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.one.R;

import java.util.List;

/**
 * Description 首页列表Adapter
 * <br>Author wanghengwei
 * <br>Date   2018/11/19 16:58
 */
public class TitleListAdapter extends BaseAdapter {

    private Context context;
    private List<String> mDataList;

    public TitleListAdapter(Context context, List<String> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_title_list, parent, false);

            holder.tvTitle = convertView.findViewById(R.id.tv_title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvTitle.setText(mDataList.get(position));

        return convertView;
    }

    private static class ViewHolder {
        private TextView tvTitle;
    }
}
