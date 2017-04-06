package com.desapoint.desapoint.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.adapters.TopicItemAdapter;
import com.desapoint.desapoint.pojos.Topic;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.desapoint.desapoint.toolsUtilities.ConstantInformation.JSONLIST;


public class TopicsActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager;
    private List<Topic> topicContents=new ArrayList<>();
    private TopicItemAdapter topicItemAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);


        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String listContent=getIntent().getStringExtra(JSONLIST);

        try{
            Type listType = new TypeToken<List<Topic>>() {}.getType();
            topicContents=new Gson().fromJson(listContent,listType);
            Log.e("expected json",listContent);

            String course=topicContents.get(0).getSubject();
            if(course.length()>20){
                course=course.substring(0,18).concat("...");
            }
            actionBarTitle("Subject topics",course);
            topicItemAdapter=new TopicItemAdapter(getBaseContext(),topicContents);
            recyclerView.setAdapter(topicItemAdapter);
        }catch (Exception ex){
            Toast.makeText(getBaseContext(),"unknown error",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
        }

        return true;
    }

    public void actionBarTitle(String title,String activity){

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_title, null);

        //if you need to customize anything else about the text, do it here.
        //I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView)v.findViewById(R.id.main_title)).setText(activity);
        //assign the view to the actionbar
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setCustomView(v);

        //if you need to customize anything else about the text, do it here.
        //I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView)v.findViewById(R.id.sub_title)).setText(title);
        //assign the view to the actionbar
        this.getSupportActionBar().setCustomView(v);
    }
}
