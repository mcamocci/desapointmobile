package com.desapoint.desapoint.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.pojos.Note;

import java.util.List;

/**
 * Created by root on 3/13/17.
 */

public class NoteItemAdapter extends RecyclerView.Adapter<NoteItemAdapter.NoteViewHolder> {

    private List<Note> content;
    private Context context;

    public NoteItemAdapter(Context context,List<Note> list){
        this.context=context;
        this.content=list;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.downloadable_item,parent,false);
        NoteViewHolder holder=new NoteViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note=content.get(position);
        holder.setData(note);
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView download;
        private ImageView share;
        private TextView title;
        private TextView description;

        public NoteViewHolder(View view){
            super(view);
            download=(ImageView)view.findViewById(R.id.download_action);
            download.setOnClickListener(this);
            share=(ImageView)view.findViewById(R.id.share_action);
            share.setOnClickListener(this);
            description=(TextView) view.findViewById(R.id.description);
            title=(TextView) view.findViewById(R.id.title);
            view.setOnClickListener(this);

        }

        public void setData(Note note){
            description.setText(note.getDescription());
            title.setText(note.getNotes_name());
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.download_action){
                Toast.makeText(context,"download clicked",Toast.LENGTH_SHORT).show();
            }else if(v.getId()==R.id.share_action){
                Toast.makeText(context,"share clicked",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
