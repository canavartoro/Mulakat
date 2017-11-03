package com.mulakat.huseyin.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mulakat.huseyin.mulakat.R;
import com.mulakat.huseyin.task.DownloadImageTask;
import com.mulakat.huseyin.util.statics;

import java.net.URL;
import java.util.Vector;

import static com.mulakat.huseyin.util.statics.getMainActivity;

/**
 * Created by huseyin on 2.11.2017.
 */

public class adapter_archiveItem extends BaseAdapter {

    private static Vector<ArchiveItemComponent> searchArrayList;

    private LayoutInflater mInflater;
    private int selectedPos = -1;

    public adapter_archiveItem(Context context, Vector<ArchiveItemComponent> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public void setSelectedPosition(int pos){
        selectedPos = pos;
        // inform the view of this change
        notifyDataSetChanged();
    }

    public int getSelectedPosition(){
        return selectedPos;
    }

    public int getCount() {
        if(searchArrayList == null) return 0;
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) statics.getMainActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.row_view_archiveitem, null);
            holder = new ViewHolder();
            holder.imgarchive = (ImageView)convertView.findViewById(R.id.imgarchive);
            holder.txtInfo = (TextView) convertView.findViewById(R.id.txtInfo);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.textTitle);
            holder.txtDesc = (TextView) convertView.findViewById(R.id.textDesc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (searchArrayList.get(position).getImage() != null) {
            holder.imgarchive.setImageBitmap(searchArrayList.get(position).getImage());
        }
        holder.txtInfo.setText(searchArrayList.get(position).getArchiveTime() + " | " + searchArrayList.get(position).getArchiveAuthor());
        holder.txtTitle.setText(searchArrayList.get(position).getArchiveTitle());
        holder.txtDesc.setText(searchArrayList.get(position).getArchiveDesc());

        return convertView;
    }

    static class ViewHolder {
        ImageView imgarchive;
        TextView txtInfo;
        TextView txtTitle;
        TextView txtDesc;
    }
}
