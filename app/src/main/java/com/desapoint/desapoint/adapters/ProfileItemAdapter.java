package com.desapoint.desapoint.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.activities.ResourceDownloadActivity;
import com.desapoint.desapoint.pojos.ProfileObject;

import java.util.List;


/**
 * Created by root on 3/13/17.
 */

public class ProfileItemAdapter extends RecyclerView.Adapter<ProfileItemAdapter.ProfileItemViewHolder> {

    private List<ProfileObject> content;
    private Context context;

    public ProfileItemAdapter(Context context, List<ProfileObject> list){
        this.context=context;
        this.content=list;
    }

    @Override
    public ProfileItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item,parent,false);
        ProfileItemViewHolder holder=new ProfileItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProfileItemViewHolder holder, int position) {
        ProfileObject object=content.get(position);
        holder.setData(object);
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class ProfileItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView content;
        ProfileObject profileObject;

        public ProfileItemViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            content=(TextView) view.findViewById(R.id.content_holder);
        }

        public void setData(ProfileObject object){
            this.profileObject=object;
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context, ResourceDownloadActivity.class);
            context.startActivity(intent);
        }
    }
}
