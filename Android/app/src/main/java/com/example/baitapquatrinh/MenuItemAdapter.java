package com.example.baitapquatrinh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MenuItemAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<MenuItem> menuItemList;

    public MenuItemAdapter(Context context, int layout, List<MenuItem> menuItemList) {
        this.context = context;
        this.layout = layout;
        this.menuItemList = menuItemList;
    }
    static class ViewHolder {
        ImageView imgMenuItem;
        TextView tvMenuItem;
        ViewHolder(View view) {
            tvMenuItem = view.findViewById(R.id.tvMenuItem);
            imgMenuItem = view.findViewById(R.id.imgMenuItem);
        }
    }
    @Override
    public int getCount() {
        return menuItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return menuItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        // Nếu convertView chưa có thì inflate layout mới
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MenuItem menuItem = menuItemList.get(i);
        viewHolder.tvMenuItem.setText(menuItem.getName());
        Glide.with(context)
                .load(menuItem.getImgMenuItem())
                .placeholder(R.drawable.ic_launcher_foreground) // Hình ảnh tạm trong khi tải
                .into(viewHolder.imgMenuItem);

        return convertView;
    }
}
