package com.desapoint.desapoint.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.fragments.Subjects;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.desapoint.desapoint.fragments.Articles;

public class Main extends AppCompatActivity {

    private BottomBar bottomBar;
    private Subjects subjectFragment=new Subjects();
    private Articles articlesFragment=new Articles();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_title, null);

        //assign the view to the actionbar
        this.getSupportActionBar().setCustomView(v);

        //if you need to customize anything else about the text, do it here.
        //I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView)v.findViewById(R.id.title)).setText(this.getTitle());



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomBar=BottomBar.attach(this,savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.bottom_menu, new OnMenuTabClickListener(){
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                int id=menuItemId;
                Toast.makeText(getBaseContext(),"subject",Toast.LENGTH_LONG).show();
                if(id==R.id.subjects){
                    Toast.makeText(getBaseContext(),"subject",Toast.LENGTH_LONG).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,subjectFragment).commit();

                }else if(id==R.id.articles){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,articlesFragment).commit();
                }

            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });
        bottomBar.mapColorForTab(0,"#3f51b5");
        bottomBar.mapColorForTab(1,"#aa00ff");
//        bottomBar.mapColorForTab(3,"#2196f3");

        /*BottomBarBadge articles=bottomBar.makeBadgeForTabAt(0,"#ff0000",12);
        articles.show();*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
        }else if(id==R.id.feedBackMenu){
            intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"info@desapoint.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback from Android app user");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }else if(id==R.id.aboutMenu){
            intent=new Intent(getBaseContext(),About.class);
            startActivity(intent);
        }else if(id==R.id.shareMenu){
            String shared_content="Hello am using "+getResources().getString(R.string.app_name)+" to access all course materials " +
                    "Get it on google play today. https://play.google.com/store/apps/details?id=com.desapoint.desapoint";
            intent=new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,shared_content);
            Intent cooler_one=intent.createChooser(intent,"Complete process with:-");
            startActivity(cooler_one);

        }else if(id==R.id.rateUsMenu){

            Uri uri = Uri.parse("market://details?id=" + getBaseContext().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getBaseContext().getPackageName())));
            }
        }

        return true;
    }


}
