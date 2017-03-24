package com.desapoint.desapoint.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.toolsUtilities.ConstantInformation;
import com.desapoint.desapoint.toolsUtilities.PreferenceStorage;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;


public class LoginActivity extends AppCompatActivity {

    private LinearLayout login;
    private ProgressBar progressBar;
    private EditText usernameEditText;
    private EditText passwordEditText;

    private String username=null;
    private String password=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //is  user loged before?
        if(PreferenceStorage.getUserJson(getBaseContext()).length()>10){
            Intent intent=new Intent(getBaseContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

        usernameEditText=(EditText)findViewById(R.id.username);
        passwordEditText=(EditText)findViewById(R.id.password);

        progressBar=(ProgressBar)findViewById(R.id.login_progress);
        progressBar.setVisibility(View.INVISIBLE);


        getSupportActionBar().setIcon(R.drawable.ic_action_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_action_home);


        login=(LinearLayout)findViewById(R.id.sign_in_button);


        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                username=usernameEditText.getText().toString().trim();
                password=passwordEditText.getText().toString().trim();

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0,0);

                if(username.length()>0){
                    login(getBaseContext(),username,password, ConstantInformation.LOGIN_URL);
                    progressBar.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(getBaseContext(),"Invalid information",Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    public void login(final Context context, String username, String password, String url){

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username",username);
        params.put("password",password);


        httpClient.post(context,url, params,new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(context,"Code:"+Integer.toString(statusCode),Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"Failed, try again later",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Log.e("response",responseString);
               if(responseString.length()<8){
                    progressBar.setVisibility(View.INVISIBLE);
                    if(responseString.equalsIgnoreCase("none")){
                        Toast.makeText(getBaseContext(),"Invalid information",Toast.LENGTH_SHORT).show();
                        usernameEditText.setText("");
                        passwordEditText.setText("");
                    }

                }else{
                   progressBar.setVisibility(View.INVISIBLE);
                   Intent intent=new Intent(getBaseContext(),MainActivity.class);
                   PreferenceStorage.addUserJson(getBaseContext(),responseString);
                   startActivity(intent);
                   finish();
               }


            }
        });

    }

}