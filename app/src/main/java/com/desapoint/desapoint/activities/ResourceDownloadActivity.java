package com.desapoint.desapoint.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.adapters.NoteItemAdapter;
import com.desapoint.desapoint.pojos.Note;

import java.util.ArrayList;
import java.util.List;

public class ResourceDownloadActivity extends AppCompatActivity {

    private List<Note> list=new ArrayList<>();
    private RecyclerView recyclerView;
    private NoteItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_download);

        actionBarTitle("Notes Download/view");
        list=testDataGenerator();

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new NoteItemAdapter(getBaseContext(),list);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
        }
        return true;
    }

    public List<Note> testDataGenerator(){
        List<Note> list=new ArrayList<>();
        for(int i=0;i<12;i++){
            Note note=new Note();
            note.setNotes_name("Database manage.ppt");
            note.setDescription("Notes for lecture 2");
            note.setNotes_upload("https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=3&cad=rja&uact=8&ved=0ahUKEwjKh9uswdPSAhVkK8AKHW2NDosQFgglMAI&url=http%3A%2F%2Fcodex.cs.yale.edu%2Favi%2Fdb-book%2Fdb6%2Fslide-dir%2FPPT-dir%2Fch1.ppt&usg=AFQjCNFdBYesZZnqpEEaaQ7_-s7kVF93DQ&sig2=xqcJiOcOWUMlkXOmVRplrQ&bvm=bv.149397726,d.d24");
            list.add(note);
        }
        return list;
    }


    public void actionBarTitle(String title){

        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View v = inflator.inflate(R.layout.custom_title_other, null);

        //if you need to customize anything else about the text, do it here.
        //I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView)v.findViewById(R.id.title)).setText(title);
        //assign the view to the actionbar
        this.getSupportActionBar().setCustomView(v);
    }

}
