package com.desapoint.desapoint.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.activities.AnnouncementDetailActivitty;
import com.desapoint.desapoint.kotlindata.AnnouncementDataObject;
import com.desapoint.desapoint.pojos.Announcement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 3/24/17.
 */

public class AnnouncementItemAdapter extends RecyclerView.Adapter<AnnouncementItemAdapter.AnnouncementViewHolder> {

    private List<AnnouncementDataObject> content;
    private Context context;

    public AnnouncementItemAdapter(Context context, List<AnnouncementDataObject> list){
        this.context=context;
        this.content=list;
    }

    @Override
    public AnnouncementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_item,parent,false);
        AnnouncementViewHolder holder=new AnnouncementViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AnnouncementViewHolder holder, int position) {
        AnnouncementDataObject announcement=content.get(position);
        holder.setData(announcement);
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class AnnouncementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title, body, dateTextView;
        private AnnouncementDataObject announcement;

        public AnnouncementViewHolder(View view){
            super(view);
            this.title = view.findViewById(R.id.title);
            this.body = view.findViewById(R.id.body);
            this.dateTextView = view.findViewById(R.id.date);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, AnnouncementDetailActivitty.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("ANNOUNCEMENT",announcement);
            context.startActivity(intent);
        }

        public void setData(AnnouncementDataObject announcement){
            this.announcement=announcement;
            Log.e("The announcement",announcement.toString());
            title.setText(announcement.getTitle());
            body.setText(announcement.getBody());

            String pattern = "yyyy-MM-dd hh:mm:ss";
            Date date = null;
            String requiredFormat = "MMM dd, yyyy";
            Log.e("date",announcement.getDate());

            try {
                date = new SimpleDateFormat(pattern).parse(announcement.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String dateToDisplay = new SimpleDateFormat(requiredFormat).format(date);

            dateTextView.setText(dateToDisplay);
        }

    }
}
