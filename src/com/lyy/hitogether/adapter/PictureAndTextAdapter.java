package com.lyy.hitogether.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.Picture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PictureAndTextAdapter extends MyBaseAdapter<String>{ 
    private static List<Picture> pictures; 
 
    public PictureAndTextAdapter(String[] titles, int[] images, Context context) 
    { 
    	super(context,Arrays.asList(titles)); 
        pictures = new ArrayList<Picture>(); 
        for (int i = 0; i < images.length; i++) 
        { 
            Picture picture = new Picture(titles[i], images[i]); 
            pictures.add(picture); 
        } 
        
    } 
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    { 
        ViewHolder viewHolder; 
        if (convertView == null) 
        { 
            convertView = commomInflater.inflate(R.layout.picture_text_item, null); 
            viewHolder = new ViewHolder(); 
            viewHolder.title = (TextView) convertView.findViewById(R.id.title); 
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image); 
            convertView.setTag(viewHolder); 
        } else
        { 
            viewHolder = (ViewHolder) convertView.getTag(); 
        } 
        viewHolder.title.setText(pictures.get(position).getTitle()); 
        viewHolder.image.setImageResource(pictures.get(position).getImageId()); 
        return convertView; 
    } 
 
} 
 
class ViewHolder 
{ 
    public TextView title; 
    public ImageView image; 
} 


