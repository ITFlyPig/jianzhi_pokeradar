package com.seventeen.goradar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seventeen.goradar.model.SearchModel;
import com.seventeen.goradar.R;

import java.util.List;

/**
 * Created by hyzx on 2016/10/10.
 */
public class PokeModelAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<SearchModel> list;
    public PokeModelAdapter(Context context, List<SearchModel> regionModelList){
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.list = regionModelList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder{
        private TextView textViewtitle;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final   ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView  = mLayoutInflater.inflate(R.layout.list_item,null);
            holder.textViewtitle = (TextView) convertView.findViewById(R.id.textview_text);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        convertView.setBackgroundResource(R.drawable.listselectortwo);
        holder.textViewtitle.setText(list.get(position).getName());

        return convertView;
    }

}
