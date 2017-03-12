package com.desapoint.desapoint.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.pojos.Article;
import com.desapoint.desapoint.pojos.Book;

import java.util.List;

/**
 * Created by root on 3/12/17.
 */

public class PostItemAdapter extends RecyclerView.Adapter<PostItemAdapter.ItemHolder>{

    private List<Object> contents;
    private Context context;


    public PostItemAdapter(Context context, List<Object> list){
        this.context=context;
        this.contents=list;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        try{
            Article article=(Article)contents.get(position);
            holder.setData(article);
        }catch (Exception ex ){
            Book book=(Book)contents.get(position);
            holder.setData(book);
        }
    }

    @Override
    public PostItemAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_model, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{

        private Object object;
        private TextView title;
        private TextView shortens;
        private TextView descriptioin;


        public ItemHolder(View view){

            super(view);

        }

        public void setData(Object object){
           this.object=object;

            if(object instanceof Article){
                shortens.setText("A");
                title.setText("Article");
                descriptioin.setText("From ellow");
            }

        }
    }
}
