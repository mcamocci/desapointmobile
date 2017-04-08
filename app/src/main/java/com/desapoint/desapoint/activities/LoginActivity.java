package com.desapoint.desapoint.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.desapoint.desapoint.R;
import com.desapoint.desapoint.pojos.University;
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
    private LinearLayout createAccount;
    private TextView forgotPassword;
    private ProgressDialog progress;

    private String username=null;
    private String password=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //is  user loged before?
        if(PreferenceStorage.getUserJson(getBaseContext()).length()>10){
            Intent intent=new Intent(getBaseContext(),MainActivity.class);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            startActivity(intent);
            finish();
        }


        usernameEditText=(EditText)findViewById(R.id.username);
        passwordEditText=(EditText)findViewById(R.id.password);

        progressBar=(ProgressBar)findViewById(R.id.login_progress);
        progressBar.setVisibility(View.INVISIBLE);

        forgotPassword=(TextView)findViewById(R.id.forgot);
        forgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        createAccount=(LinearLayout)findViewById(R.id.create_account);
        createAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getUniversity(getBaseContext(),ConstantInformation.UNIVERSITY_URL);

            }
        });

       getSupportActionBar().hide();

        login=(LinearLayout)findViewById(R.id.sign_in_button);


        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                username=usernameEditText.getText().toString().trim();
                password=passwordEditText.getText().toString().trim();


                View view=LoginActivity.this.getCurrentFocus();
                if(view!=null){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                }

                if(username.length()>0){
                    login(getBaseContext(),username,password, ConstantInformation.LOGIN_URL);
                    progressBar.setVisibility(View.VISIBLE);
                }else{
                    Snackbar.make(usernameEditText,"Invalid information",Snackbar.LENGTH_LONG).show();
                }


            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
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
                Snackbar.make(usernameEditText,"Failed, try again later",Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Log.e("response",responseString);
               if(responseString.length()<8){
                    progressBar.setVisibility(View.INVISIBLE);
                    if(responseString.equalsIgnoreCase("none")){
                        Snackbar.make(usernameEditText,"Invalid information",Snackbar.LENGTH_LONG).show();
                        usernameEditText.setText("");
                        passwordEditText.setText("");
                    }

                }else{
                   progressBar.setVisibility(View.INVISIBLE);
                   Intent intent=new Intent(getBaseContext(),MainActivity.class);
                   PreferenceStorage.addUserJson(getBaseContext(),responseString);
                   overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                   startActivity(intent);
                   finish();
               }


            }
        });

    }


    public void getUniversity(final Context context, String url){

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();
        progress=ProgressDialog.show(LoginActivity.this,"Please wait",
                "loading universities", false);

        progress.show();


        httpClient.post(context,url, params,new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.dismiss();
                Snackbar.make(usernameEditText,"Please try again later",Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                progress.dismiss();
                Log.e("response",responseString);
                if(responseString.length()<8){
                    Snackbar.make(usernameEditText,"Could not prepare registration",Snackbar.LENGTH_LONG).show();
                }else{
                    Intent intent=new Intent(getBaseContext(),RegistrationActivityScreenOne.class);
                    intent.putExtra(University.JSON_VARIABLE,responseString);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }


            }
        });

    }

}
