package com.desapoint.desapoint.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.pojos.Announcement;
import java.util.List;

/**
 * Created by root on 3/24/17.
 */

public class AnnouncementItemAdapter extends RecyclerView.Adapter<AnnouncementItemAdapter.AnnouncementViewHolder> {

    private List<Announcement> content;
    private Context context;

    public AnnouncementItemAdapter(Context context, List<Announcement> list){
        this.context=context;
        this.content=list;
    }

    @Override
    public AnnouncementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item,parent,false);
        AnnouncementViewHolder holder=new AnnouncementViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AnnouncementViewHolder holder, int position) {
        Announcement announcement=content.get(position);
        holder.setData(announcement);
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class AnnouncementViewHolder extends RecyclerView.ViewHolder{

        private TextView titleLabel;
        private Announcement announcement;

        public AnnouncementViewHolder(View view){
            super(view);
            this.titleLabel=(TextView) view.findViewById(R.id.topic_name);;
        }

        public void setData(Announcement announcement){
            this.announcement=announcement;

        }
    }
}
