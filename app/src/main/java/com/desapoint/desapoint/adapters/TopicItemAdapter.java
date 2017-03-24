package com.desapoint.desapoint.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.pojos.Topic;

import java.util.List;
/**
 * Created by root on 3/24/17.
 */

public class TopicItemAdapter extends RecyclerView.Adapter<TopicItemAdapter.TopicViewHolder> {

    private List<Topic> content;
    private Context context;

    public TopicItemAdapter(Context context, List<Topic> list){
        this.context=context;
        this.content=list;
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item,parent,false);
        TopicViewHolder holder=new TopicViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {
        Topic topic=content.get(position);
        holder.setData(topic);
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder{

        private TextView titleLabel;
        private Topic topic;

        public TopicViewHolder(View view){
            super(view);
            this.titleLabel=(TextView) view.findViewById(R.id.topic_name);;
        }

        public void setData(Topic topic){
            this.topic=topic;
            this.titleLabel.setText(topic.getTitle());
        }
    }
}
