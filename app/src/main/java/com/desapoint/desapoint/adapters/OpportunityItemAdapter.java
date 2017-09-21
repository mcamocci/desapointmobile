package com.desapoint.desapoint.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.desapoint.desapoint.pojos.OpportunityItem;

import java.util.List;

/**
 * Created by haikarosetz on 9/21/17.
 */

public class OpportunityItemAdapter extends RecyclerView.Adapter<OpportunityItemAdapter.OpportunityViewHolder> {

    List<OpportunityItem> opportunityItemList;
    Context context;


    OpportunityItemAdapter(Context context, List<OpportunityItem> opportunityItems){
        this.opportunityItemList=opportunityItems;
        this.context=context;
    }

    @Override
    public int getItemCount() {
        return opportunityItemList.size();
    }

    @Override
    public OpportunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(OpportunityViewHolder holder, int position) {

    }

    public class OpportunityViewHolder extends RecyclerView.ViewHolder{
        public OpportunityViewHolder(View view){
            super(view);
        }
    }
}
