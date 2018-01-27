package com.desapoint.desapoint.FragmentDialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.adapters.AnnouncementItemAdapter;
import com.desapoint.desapoint.adapters.AssignmentItemAdapter;
import com.desapoint.desapoint.adapters.TopicItemAdapter;
import com.desapoint.desapoint.kotlindata.AnnouncementDataObject;
import com.desapoint.desapoint.pojos.Announcement;
import com.desapoint.desapoint.pojos.Assignment;
import com.desapoint.desapoint.pojos.Category;
import com.desapoint.desapoint.pojos.Topic;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.desapoint.desapoint.toolsUtilities.ConstantInformation.JSONLIST;

/**
 * Created by root on 3/22/17.
 */

public class AssignmentDialog extends DialogFragment {

    private Context context;
    private List<AnnouncementDataObject> announcementContents=new ArrayList<>();
    private AnnouncementItemAdapter announcementItemAdapter;
    private List<Assignment> assignmentContents=new ArrayList<>();
    private AssignmentItemAdapter assignmentItemAdapter;
    private List<Topic> topicContents=new ArrayList<>();
    private TopicItemAdapter topicItemAdapter;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    public  static String TITLE_TOPIC="TOPIC";
    public  static String TITLE_ANNOUNCEMENT="ANNOUNCEMENT";
    public  static String TITLE_ASSIGNMENT="ASSIGNMENT";

    public static String HEAD="HEAD";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        String title=getArguments().getString(HEAD);
        String listContent=getArguments().getString(JSONLIST);



        View view =inflater.inflate(R.layout.subject_dialogs_layout,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        TextView textView=(TextView)view.findViewById(R.id.header);


        if(title.equalsIgnoreCase(TITLE_TOPIC)){
            textView.setText("Subject topics");
            try{
                Type listType = new TypeToken<List<Topic>>() {}.getType();
                topicContents=new Gson().fromJson(listContent,listType);
                topicItemAdapter=new TopicItemAdapter(context,topicContents);
                recyclerView.setAdapter(topicItemAdapter);
            }catch (Exception ex){
                Toast.makeText(context,"Unknown error ",Toast.LENGTH_LONG).show();
                this.dismiss();
            }

        } else if (title.equalsIgnoreCase(TITLE_ANNOUNCEMENT)) {
            textView.setText("Subject announcements");

            try{
                Type listType = new TypeToken<List<Announcement>>() {}.getType();
                announcementContents=new Gson().fromJson(listContent,listType);
                announcementItemAdapter=new AnnouncementItemAdapter(context,announcementContents);
                recyclerView.setAdapter(announcementItemAdapter);
            }catch (Exception ex){
                Toast.makeText(context,"Unknown error ",Toast.LENGTH_LONG).show();
                this.dismiss();
            }

        }else if(title.equalsIgnoreCase(TITLE_ASSIGNMENT)){
            textView.setText("Subject assignemts");

            try{
                Type listType = new TypeToken<List<Assignment>>() {}.getType();
                assignmentContents=new Gson().fromJson(listContent,listType);
                assignmentItemAdapter=new AssignmentItemAdapter(context,assignmentContents);
                recyclerView.setAdapter(assignmentItemAdapter);
            }catch (Exception ex){
                Toast.makeText(context,"Unknown error ",Toast.LENGTH_LONG).show();
                this.dismiss();
            }


        }

        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
}
