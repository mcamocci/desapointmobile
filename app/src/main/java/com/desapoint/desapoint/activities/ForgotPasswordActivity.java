package com.desapoint.desapoint.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.desapoint.desapoint.R;
import com.desapoint.desapoint.toolsUtilities.ConstantInformation;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class ForgotPasswordActivity extends AppCompatActivity {

    private LinearLayout sendPassword;
    private EditText fullName;
    private EditText phone;
    private ProgressDialog progress;
    private LinearLayout whyPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        actionBarTitle("Forgot password");

        whyPhone=(LinearLayout)findViewById(R.id.why_phone);
        whyPhone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Snackbar.make(whyPhone,"Phone is used to sent the forgotten password",Snackbar.LENGTH_LONG).show();
            }
        });

        sendPassword=(LinearLayout)findViewById(R.id.pass_reset);
        phone=(EditText)findViewById(R.id.phone);
        fullName=(EditText)findViewById(R.id.full_name);
        sendPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String cleaned_fullName=fullName.getText().toString().trim();
                String cleaned_phone=phone.getText().toString().trim();
                if(cleaned_fullName.length()>5 && cleaned_phone.length()>5){
                    resetPassword(getBaseContext(),
                            ConstantInformation.RESET_URL,cleaned_fullName,
                            cleaned_phone);
                }else{
                    Toast.makeText(getBaseContext(),"Invalid phone or name",Toast.LENGTH_LONG).show();
                }

            }
        });

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

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_title, null);

        //if you need to customize anything else about the text, do it here.
        //I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView)v.findViewById(R.id.main_title)).setText(this.getTitle());
        //assign the view to the actionbar
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setCustomView(v);

        //if you need to customize anything else about the text, do it here.
        //I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView)v.findViewById(R.id.sub_title)).setText(title);
        //assign the view to the actionbar
        this.getSupportActionBar().setCustomView(v);
    }

    public void resetPassword(final Context context, String url,String fullname,String phone){

        AsyncHttpClient httpClient=new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("fullname",fullname);
        params.put("phone",phone);
        progress= ProgressDialog.show(ForgotPasswordActivity.this,"Please wait",
                "performing reset action", false);

        progress.show();


        httpClient.post(context,url, params,new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.dismiss();
                Toast.makeText(context,"Please try again later",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                progress.dismiss();
                Log.e("response",responseString);
                if(responseString.length()<8){
                    Toast.makeText(context,"Please make sure your entered email is the same as one used during registration" +
                            "or contact desapoint to help you fixing problem, contacts are on about section",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"Please check the received email to login to your account",Toast.LENGTH_LONG).show();
                }


            }
        });

    }
}
