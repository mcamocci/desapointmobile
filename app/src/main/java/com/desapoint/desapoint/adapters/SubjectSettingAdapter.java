package com.desapoint.desapoint.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.activities.LoginActivity;
import com.desapoint.desapoint.pojos.Subject;
import com.desapoint.desapoint.pojos.User;
import com.desapoint.desapoint.toolsUtilities.ConstantInformation;
import com.desapoint.desapoint.toolsUtilities.PreferenceStorage;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

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
            code.setOnClickListener(this);
            title.setOnClickListener(this);


        }
        public void setData(Context context,Subject subject){
            this.context=context;
            this.subject=subject;

            code.setText(subject.getSubject_code());
            title.setText(subject.getSubject());

        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.subject_code){
                Toast.makeText(context,Integer.toString(subject.getId()),Toast.LENGTH_LONG).show();
                //removeSubject(context, ConstantInformation.REMOVE_SUBJECT_URL,subject.getId());
            }
        }


    }


    public void removeSubject(final Context context,String url,int subject_id){

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();
        User user=new Gson().fromJson(PreferenceStorage.getUserJson(context),User.class);
        params.put("user_id",user.getUser_id());
        params.put("subject_id",subject_id);



        httpClient.post(context,url, params,new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                if(responseString.length()<8){

                    if(responseString.equalsIgnoreCase("none")){
                        Toast.makeText(context,"Could not add subject",Toast.LENGTH_LONG).show();
                    }else if(responseString.length()>10){
                        Toast.makeText(context,"Could not add subject",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(context,"Please logout for the changes to occur",Toast.LENGTH_LONG).show();
                        PreferenceStorage.clearInformation(context);
                    }
                }


            }
        });

    }


    //the dialog issues//
    public  void approveRemoveDialog(Context context,String title){

        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_logout);
        dialog.setTitle(title);

        TextView ok=(TextView)dialog.findViewById(R.id.ok);
        TextView cancel= (TextView)dialog.findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        try{
            dialog.show();
        }catch (Exception ex){

        }


    }



}
