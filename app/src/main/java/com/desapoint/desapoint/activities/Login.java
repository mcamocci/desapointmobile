package com.desapoint.desapoint.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.desapoint.desapoint.R;



public class Login extends AppCompatActivity {

    private LinearLayout login;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar=(ProgressBar)findViewById(R.id.login_progress);
        progressBar.setVisibility(View.INVISIBLE);


        login=(LinearLayout)findViewById(R.id.sign_in_button);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent=new Intent(getBaseContext(),Main.class);
                startActivity(intent);
                finish();

            }
        });



    }

}
