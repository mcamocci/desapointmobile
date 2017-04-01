package com.desapoint.desapoint.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.desapoint.desapoint.FragmentDialog.AssignmentDialog;
import com.desapoint.desapoint.R;
import com.desapoint.desapoint.activities.ResourceDownloadActivity;
import com.desapoint.desapoint.pojos.Announcement;
import com.desapoint.desapoint.pojos.Assignment;
import com.desapoint.desapoint.pojos.Subject;
import com.desapoint.desapoint.pojos.Topic;
import com.desapoint.desapoint.pojos.WindowInfo;
import com.desapoint.desapoint.toolsUtilities.ConstantInformation;
import com.desapoint.desapoint.toolsUtilities.PreferenceStorage;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;


import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.desapoint.desapoint.toolsUtilities.ConstantInformation.INTENTINFO;

/**
 * Created by root on 3/12/17.
 */

public class SubjectItemAdapter extends RecyclerView.Adapter<SubjectItemAdapter.SubjectHolder> {

    private Context context;
    private List<Subject> list;
    private List<Topic> topics;
    private List<Announcement> announcements;
    private List<Assignment> assignments;
    private  ProgressDialog progress;
    private int dialogTracker=-1; //0 announcement, //1 assignment , 2 topics

    private String FLAG;

    public SubjectItemAdapter(Context context, List<Subject> subjects,String flag){
        this.context=context;
        this.list=subjects;
        this.FLAG=flag;
    }

    @Override
    public void onBindViewHolder(SubjectHolder holder, int position) {
        holder.setData(this.context,list.get(position));
    }

    @Override
    public SubjectItemAdapter.SubjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(PreferenceStorage.getWindowInfo(context).equalsIgnoreCase(WindowInfo.NOTES)){
           view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_model,parent,false);
           view.findViewById(R.id.text_short_bg).setBackgroundColor(Color.parseColor("#292B2C"));
        }else if(PreferenceStorage.getWindowInfo(context).equalsIgnoreCase(WindowInfo.PASTPAPER)){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_model,parent,false);
            view.findViewById(R.id.text_short_bg).setBackgroundColor(Color.parseColor("#F0AD4E"));
        }else if(FLAG.equals(WindowInfo.SUBJECT)){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_item_options,parent,false);
        }else{
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_model,parent,false);
            view.findViewById(R.id.text_short_bg).setBackgroundColor(Color.parseColor("#0275D8"));
        }



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
        private TextView announcement;
        private TextView  assignment;
        private TextView  option3;

        public SubjectHolder(View view){
            super(view);
            view.setOnClickListener(this);

            if(FLAG.equals(WindowInfo.SUBJECT)){

                announcement=(TextView)view.findViewById(R.id.announce);
                announcement.setOnClickListener(this);
                assignment=(TextView)view.findViewById(R.id.assignment);
                assignment.setOnClickListener(this);
                option3=(TextView)view.findViewById(R.id.topics);
                option3.setOnClickListener(this);

            }else{
                parent=(LinearLayout)view.findViewById(R.id.parent);
                parent.setOnClickListener(this);
                letter=(TextView)view.findViewById(R.id.title_short);

            }

            code=(TextView)view.findViewById(R.id.subject_code);
            title=(TextView)view.findViewById(R.id.subject);


        }
        public void setData(Context context,Subject subject){
            this.context=context;
            this.subject=subject;

            if(FLAG.equals(WindowInfo.SUBJECT)){

            }else{
                letter.setText(subject.getSubject().substring(0,1));
            }
            code.setText(subject.getSubject_code());
            title.setText(subject.getSubject());

        }

        @Override
        public void onClick(View v) {

            if(FLAG.equals(WindowInfo.SUBJECT)){

                if(v.getId()==R.id.announce){
                    dialogTracker=0;
                    getListOfContent(context,subject.getSubject(),
                            "subject Announcements", ConstantInformation.SUBJECT_ANNOUNCEMENTS);

                }else if(v.getId()==R.id.assignment){
                    dialogTracker=1;
                    getListOfContent(context,subject.getSubject(),
                            "subject Assignments", ConstantInformation.SUBJECT_ASSIGNMENTS);

                }else if(v.getId()==R.id.topics){
                    dialogTracker=2;
                    getListOfContent(context,subject.getSubject(),
                            "subject Topics", ConstantInformation.SUBJECT_TOPICS);
                }

            }else if(FLAG.equals(WindowInfo.NOTES)){
                Intent intent=new Intent(context,ResourceDownloadActivity.class);
                //intent.putExtra(INTENTINFO,subject.getSubject());
                intent.putExtra(INTENTINFO,subject.getSubject());
                context.startActivity(intent);
            }else if(FLAG.equals(WindowInfo.PASTPAPER)){
                Intent intent=new Intent(context,ResourceDownloadActivity.class);
                intent.putExtra(INTENTINFO,subject.getSubject());
                context.startActivity(intent);
            }
        }


    }


    //get list of contents for the dialogs

    public void getListOfContent(final Context context, String subject, final String message, String url){

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("subject",subject);

        progress = ProgressDialog.show(context, "Please wait",
                message, false);
        progress.show();


        httpClient.post(context,url, params,new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.dismiss();
                Toast.makeText(context,"Code:"+Integer.toString(statusCode),Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"Failed, try again later",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                progress.dismiss();

                Log.e("response",responseString);
                if(responseString.length()<8){

                    if(responseString.equalsIgnoreCase("none")){
                        Toast.makeText(context,
                                "There are no items of your selection , check again later",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(context,
                                "Please try again later , un expected error ",Toast.LENGTH_LONG).show();
                    }

                }else{

                    if(dialogTracker==0){
                        Toast.makeText(context,
                                responseString,Toast.LENGTH_LONG).show();
                        AssignmentDialog dialog=new AssignmentDialog();
                        Bundle bundle=new Bundle();
                        bundle.putString(AssignmentDialog.HEAD,AssignmentDialog.TITLE_ANNOUNCEMENT);
                        dialog.setArguments(bundle);
                        bundle.putString(AssignmentDialog.JSONLIST,responseString);
                        FragmentManager manager=((Activity) context).getFragmentManager();
                        dialog.show(manager,"tag");


                    }else if(dialogTracker==1){
                        Toast.makeText(context,
                                responseString,Toast.LENGTH_LONG).show();
                        AssignmentDialog dialog=new AssignmentDialog();
                        Bundle bundle=new Bundle();
                        bundle.putString(AssignmentDialog.HEAD,AssignmentDialog.TITLE_ASSIGNMENT);
                        dialog.setArguments(bundle);
                        bundle.putString(AssignmentDialog.JSONLIST,responseString);
                        FragmentManager manager=((Activity) context).getFragmentManager();
                        dialog.show(manager,"tag");


                    }else{
                        Toast.makeText(context,
                                responseString,Toast.LENGTH_LONG).show();
                        AssignmentDialog dialog=new AssignmentDialog();
                        Bundle bundle=new Bundle();
                        bundle.putString(AssignmentDialog.HEAD,AssignmentDialog.TITLE_TOPIC);
                        bundle.putString(AssignmentDialog.JSONLIST,responseString);
                        dialog.setArguments(bundle);
                        FragmentManager manager=((Activity) context).getFragmentManager();
                        dialog.show(manager,"tag");

                    }

                }


            }
        });

    }




}
