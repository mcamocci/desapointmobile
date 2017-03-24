package com.desapoint.desapoint.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.desapoint.desapoint.R;
import com.desapoint.desapoint.pojos.Assignment;

import java.util.List;

/**
 * Created by root on 3/24/17.
 */

public class AssignmentItemAdapter extends RecyclerView.Adapter<AssignmentItemAdapter.AssignmentViewHolder> {

    private List<Assignment> content;
    private Context context;

    public AssignmentItemAdapter(Context context, List<Assignment> list){
        this.context=context;
        this.content=list;
    }

    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item,parent,false);
        AssignmentViewHolder holder=new AssignmentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AssignmentViewHolder holder, int position) {
        Assignment assignment=content.get(position);
        holder.setData(assignment);
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder{

        private TextView titleLabel;
        private Assignment assignment;

        public AssignmentViewHolder(View view){
            super(view);
            this.titleLabel=(TextView) view.findViewById(R.id.topic_name);;
        }

        public void setData(Assignment assignment){
            this.assignment=assignment;
        }
    }
}
