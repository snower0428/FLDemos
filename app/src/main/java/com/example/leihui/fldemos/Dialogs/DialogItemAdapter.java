package com.example.leihui.fldemos.Dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leihui.fldemos.R;

import java.util.List;

/**
 * Created by leihui on 2018/3/2.
 *
 */

public class DialogItemAdapter extends BaseAdapter {

    private List<String> mDataList;
    private LayoutInflater mInflater;
    private int mCheckPosition = 0;

    public DialogItemAdapter(Context context, List<String> dataList) {
        super();
        mInflater = LayoutInflater.from(context);
        this.mDataList = dataList;
    }

    public void checkItem(int position) {
        mCheckPosition = position;
        notifyDataSetChanged();
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
        ViewHoler holder;
        if (convertView == null) {
            holder = new ViewHoler();
            convertView = mInflater.inflate(R.layout.dialog_list_item, null);
            holder.tvTitle = convertView.findViewById(R.id.text_view_title);
            holder.ivCheck = convertView.findViewById(R.id.image_view_check);
            holder.tvSeparator = convertView.findViewById(R.id.text_view_separator);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHoler) convertView.getTag();
        }
        holder.tvTitle.setText(mDataList.get(position));
        if (position == 0) {
            holder.tvSeparator.setVisibility(View.INVISIBLE);
        }
        else {
            holder.tvSeparator.setVisibility(View.VISIBLE);
        }
        if (position == mCheckPosition) {
            holder.ivCheck.setVisibility(View.VISIBLE);
        }
        else {
            holder.ivCheck.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class ViewHoler {
        TextView tvTitle;
        ImageView ivCheck;
        TextView tvSeparator;
    }
}
