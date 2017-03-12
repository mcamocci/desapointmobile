package com.desapoint.desapoint.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.pojos.Subject;


import java.util.List;

/**
 * Created by root on 3/12/17.
 */

public class SubjectItemAdapter extends RecyclerView.Adapter<SubjectItemAdapter.SubjectHolder> {

    private Context context;
    List<Subject> list;

    public SubjectItemAdapter(Context context, List<Subject> subjects){
        this.context=context;
        this.list=subjects;
    }

    @Override
    public void onBindViewHolder(SubjectHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public SubjectItemAdapter.SubjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_model,parent,false);
        SubjectHolder holder=new SubjectHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SubjectHolder extends RecyclerView.ViewHolder{
        private Subject subject;
        private TextView code;
        private TextView letter;
        private TextView title;

        public SubjectHolder(View view){
            super(view);
            code=(TextView)view.findViewById(R.id.code);
            title=(TextView)view.findViewById(R.id.subject);
            letter=(TextView)view.findViewById(R.id.title_short);

        }
        public void setData(Subject subject){
            this.subject=subject;
            code.setText(subject.getCode());
            title.setText(subject.getTitle());
            letter.setText(subject.getTitle().substring(0,1));
        }
    }
}
