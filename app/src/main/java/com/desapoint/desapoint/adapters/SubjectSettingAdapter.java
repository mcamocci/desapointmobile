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

public class SubjectSettingAdapter extends RecyclerView.Adapter<SubjectSettingAdapter.SubjectHolder> {

    private Context context;
    private List<Subject> list;

    public SubjectSettingAdapter(Context context, List<Subject> subjects){
        this.context=context;
        this.list=subjects;
    }

    @Override
    public void onBindViewHolder(SubjectHolder holder, int position) {
        holder.setData(this.context,list.get(position));
    }

    @Override
    public SubjectSettingAdapter.SubjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_in_settings,parent,false);

        SubjectHolder holder=new SubjectHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SubjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Subject subject;
        private TextView code;
        private TextView title;
        private Context context;

        public SubjectHolder(View view){
            super(view);
            view.setOnClickListener(this);


            code=(TextView)view.findViewById(R.id.subject_code);
            title=(TextView)view.findViewById(R.id.subject);


        }
        public void setData(Context context,Subject subject){
            this.context=context;
            this.subject=subject;

            code.setText(subject.getSubject_code());
            title.setText(subject.getSubject());

        }

        @Override
        public void onClick(View v) {

        }


    }


}
