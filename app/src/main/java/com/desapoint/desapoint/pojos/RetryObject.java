package com.desapoint.desapoint.pojos;
import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.desapoint.desapoint.R;

/**
 * Created by root on 3/16/17.
 */

public class RetryObject {

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getMessage() {
        return message;
    }

    public void setMessage(TextView message) {
        this.message = message;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void hideProgress(){
        this.progressBar.setVisibility(View.INVISIBLE);
    }
    public void showProgress(){
        this.progressBar.setVisibility(View.VISIBLE);
    }

    public void hideMessage(){
        this.message.setVisibility(View.INVISIBLE);
    }

    public  void showMessage(){
        this.message.setVisibility(View.VISIBLE);
    }

    public void hideName(){
        this.name.setVisibility(View.INVISIBLE);
    }
    public void showName(){
        this.name.setVisibility(View.VISIBLE);
    }

    public void setReason(String reason){
        this.message.setText(reason);
    }

    private TextView name;
    private TextView message;
    private ProgressBar progressBar;

    public static RetryObject getInstance(Activity activity){

        TextView name=(TextView)activity.findViewById(R.id.retryLabel);
        TextView message=(TextView)activity.findViewById(R.id.message);
        ProgressBar progressBar=(ProgressBar)activity.findViewById(R.id.progressBar);

        RetryObject object=new RetryObject();
        object.setMessage(message);
        object.setName(name);
        object.setProgressBar(progressBar);

        return object;
    }



}
