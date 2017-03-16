package com.desapoint.desapoint.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.activities.ResourceDownloadActivity;
import com.desapoint.desapoint.activities.SubjectActivity;
import com.desapoint.desapoint.pojos.Subject;
import com.google.gson.Gson;


import java.util.List;

import static com.desapoint.desapoint.toolsUtilities.ConstantInformation.INTENTINFO;

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
        holder.setData(this.context,list.get(position));
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

    public class SubjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Subject subject;
        private TextView code;
        private TextView letter;
        private TextView title;
        private LinearLayout parent;
        private Context context;

        public SubjectHolder(View view){
            super(view);
            view.setOnClickListener(this);
            parent=(LinearLayout)view.findViewById(R.id.parent);
            parent.setOnClickListener(this);
            code=(TextView)view.findViewById(R.id.subject_code);
            title=(TextView)view.findViewById(R.id.subject);
            letter=(TextView)view.findViewById(R.id.title_short);

        }
        public void setData(Context context,Subject subject){
            this.context=context;
            this.subject=subject;
            code.setText(subject.getSubject_code());
            title.setText(subject.getSubject());
            letter.setText(subject.getSubject().substring(0,1));
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context,ResourceDownloadActivity.class);
            intent.putExtra(INTENTINFO,subject.getSubject());
            context.startActivity(intent);
        }


    }




}
