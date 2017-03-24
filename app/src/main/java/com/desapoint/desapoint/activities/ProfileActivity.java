package com.desapoint.desapoint.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.desapoint.desapoint.R;
import com.desapoint.desapoint.adapters.ProfileItemAdapter;
import com.desapoint.desapoint.pojos.ProfileObject;
import com.desapoint.desapoint.pojos.User;
import com.desapoint.desapoint.toolsUtilities.PreferenceStorage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.views.AvatarView;
import agency.tango.android.avatarviewglide.GlideLoader;


public class ProfileActivity extends AppCompatActivity {

    AvatarView avatarView;
    IImageLoader imageLoader;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ProfileItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        List<ProfileObject> objectList=new ArrayList<>();

        User user=new Gson().fromJson(
                PreferenceStorage.getUserJson(getBaseContext()),User.class);

        layoutManager=new LinearLayoutManager(getBaseContext());
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);



        itemAdapter=new ProfileItemAdapter(getBaseContext(),objectList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemAdapter);



        avatarView = (AvatarView) findViewById(R.id.avatar_view);
        imageLoader = new GlideLoader();
        imageLoader.loadImage(avatarView,"http://www.vogella.de/img/lars/LarsVogelArticle7.png",user.getFirstName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarTitle(user.getFullname());



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
        }
        return true;
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
