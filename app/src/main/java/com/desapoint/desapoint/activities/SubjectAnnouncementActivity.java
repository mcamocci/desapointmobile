package com.desapoint.desapoint.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.adapters.AnnouncementItemAdapter;
import com.desapoint.desapoint.pojos.Announcement;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.desapoint.desapoint.toolsUtilities.ConstantInformation.JSONLIST;


public class SubjectAnnouncementActivity extends AppCompatActivity {

    private List<Announcement> announcementContents=new ArrayList<>();
    private AnnouncementItemAdapter announcementItemAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_announcement);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarTitle("Subject announcements");

        String listContent=getIntent().getStringExtra(JSONLIST);

        try{
            Type listType = new TypeToken<List<Announcement>>() {}.getType();
            announcementContents=new Gson().fromJson(listContent,listType);
            announcementItemAdapter=new AnnouncementItemAdapter(getBaseContext(),announcementContents);
            recyclerView.setAdapter(announcementItemAdapter);
        }catch (Exception ex){
            Toast.makeText(getBaseContext(),"Unknown error ",Toast.LENGTH_LONG).show();
        }

    }

    public void actionBarTitle(String title){

        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_title_other, null);

        //if you need to customize anything else about the text, do it here.
        //I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView)v.findViewById(R.id.title)).setText(title);
        //assign the view to the actionbar
        this.getSupportActionBar().setCustomView(v);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
