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
import com.desapoint.desapoint.pojos.Category;

import java.util.List;

import static com.desapoint.desapoint.toolsUtilities.ConstantInformation.INTENTINFO;

/**
 * Created by root on 3/13/17.
 */

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.NoteViewHolder> {

    private List<Category> content;
    private Context context;

    public CategoryItemAdapter(Context context, List<Category> list){
        this.context=context;
        this.content=list;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_cat_item,parent,false);
        NoteViewHolder holder=new NoteViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Category cat=content.get(position);
        holder.setData(cat);
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView titleLabel;
        private TextView counts;
        Category category;

        public NoteViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            this.counts=(TextView) view.findViewById(R.id.items);
            this.titleLabel=(TextView) view.findViewById(R.id.category);
        }

        public void setData(Category cat){
            this.category=cat;
            this.counts.setText(Integer.toString(cat.getNumber_books()));
            this.titleLabel.setText(cat.getCategory());
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context, ResourceDownloadActivity.class);
            intent.putExtra(INTENTINFO,category.getCategory());
            context.startActivity(intent);
        }
    }
}
