package com.hovi.hoco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hovi.hoco.R;
import com.hovi.hoco.model.Room;

import java.util.ArrayList;

public class ListRoomButtonAdapter extends BaseAdapter {

    private ArrayList<Room> rooms;
    private LayoutInflater layoutInflater;
    private Context context;

    public int getChooseRoom() {
        return chooseRoom;
    }

    public void setChooseRoom(int chooseRoom) {
        this.chooseRoom = chooseRoom;
    }

    private int chooseRoom = 0;

    public ListRoomButtonAdapter(Context context, ArrayList<Room> rooms) {
        this.rooms = rooms;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_room_layout, null);
            holder = new ViewHolder();
            holder.icon = convertView.findViewById(R.id.item_room_icon);
            holder.name =  convertView.findViewById(R.id.item_room_name);
            holder.background =  convertView.findViewById(R.id.item_room_bg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Room room = this.rooms.get(position);
        //holder.icon.setText(country.getCountryName()); -> TODO:// set color and icon.
        holder.name.setText(room.getName());
        if(position == chooseRoom){
            enableView(holder.background, true);
        }else{
            enableView(holder.background, false);
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView name;
        ImageView background;
    }


    public void enableView(ImageView imageView, boolean isEnable){
        if(isEnable) {
//            bgView.setImageResource(R.drawable.bg_button);
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_button));
        }
        else{
//            bgView.setImageResource(R.drawable.bg_button_disable);
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_button_disable));
        }
    }


}
