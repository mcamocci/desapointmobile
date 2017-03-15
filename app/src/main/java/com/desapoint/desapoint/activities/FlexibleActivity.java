package com.desapoint.desapoint.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.adapters.CategoryItemAdapter;
import com.desapoint.desapoint.pojos.Category;

import java.util.ArrayList;
import java.util.List;

public class FlexibleActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private CategoryItemAdapter adapter;
    private List<Category> categories=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexible);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String title=getIntent().getStringExtra("TITLE");
        actionBarTitle(title);
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
}
