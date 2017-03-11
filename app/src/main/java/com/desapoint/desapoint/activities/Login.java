package com.desapoint.desapoint.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.desapoint.desapoint.R;


public class Login extends AppCompatActivity {

    private LinearLayout buttonLogin;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle(getResources().getString(R.string.LoginTitle));

        buttonLogin=(LinearLayout)findViewById(R.id.sign_in_button);
        progress=(ProgressBar)findViewById(R.id.login_progress);
        progress.setVisibility(View.INVISIBLE);
        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),Main.class);
                startActivity(intent);
            }
        });

    }

}
