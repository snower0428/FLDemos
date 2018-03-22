package com.example.leihui.fldemos.DropDown;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.leihui.fldemos.R;

import java.util.List;

/**
 * Created by leihui on 2018/3/5.
 *
 */

public class ChapterItemAdapter extends BaseAdapter {

    private List<String> mDataList;
    private LayoutInflater mInflater;

    public ChapterItemAdapter(Context context, List<String> dataList) {
        super();
        mInflater = LayoutInflater.from(context);
        this.mDataList = dataList;
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
            convertView = mInflater.inflate(R.layout.chapter_list_item, null);
            holder.tvTitle = convertView.findViewById(R.id.text_view_title);
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
        return convertView;
    }

    public class ViewHoler {
        TextView tvTitle;
        TextView tvSeparator;
    }
}
