package com.desapoint.desapoint.adapters;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.desapoint.desapoint.R;
import com.desapoint.desapoint.pojos.Assignment;
import com.desapoint.desapoint.toolsUtilities.FileDownloadOperation;

import java.io.File;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.desapoint.desapoint.toolsUtilities.ConstantInformation.ASSIGNMENT_DOWNLOAD_URL;

/**
 * Created by root on 3/24/17.
 */

public class AssignmentItemAdapter extends RecyclerView.Adapter<AssignmentItemAdapter.AssignmentViewHolder> {

    private List<Assignment> content;
    private Context context;

    public AssignmentItemAdapter(Context context, List<Assignment> list){
        this.context=context;
        this.content=list;
    }

    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_item,parent,false);
        AssignmentViewHolder holder=new AssignmentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AssignmentViewHolder holder, int position) {
        Assignment assignment=content.get(position);
        holder.setData(assignment);
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView poster;
        private TextView description;
        private Assignment assignment;
        private TextView endDate;
        private LinearLayout downloadBar;
        private long enqueue;
        private DownloadManager dm;


        public AssignmentViewHolder(View view){
            super(view);
            this.poster=(TextView) view.findViewById(R.id.poster);
            this.description=(TextView) view.findViewById(R.id.description);
            this.endDate=(TextView) view.findViewById(R.id.end_date);
            this.downloadBar=(LinearLayout) view.findViewById(R.id.downloadBar);
            downloadBar.setOnClickListener(this);

        }

        public void setData(Assignment assignment){
            this.assignment=assignment;
            this.poster.setText("Prepared by: "+assignment.getPoster());
            this.description.setText(assignment.getDescription());
            this.endDate.setText("Submission date: "+assignment.getDate_return());
            if(assignment.getUrl().length()<3){
                this.downloadBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {

            Toast.makeText(context,assignment.getUrl(),Toast.LENGTH_LONG).show();

            File file=new File(assignment.getUrl());
            if(FileDownloadOperation.isFileAvaillable(context,file)){
                MimeTypeMap myMime = MimeTypeMap.getSingleton();
                Intent newIntent = new Intent(Intent.ACTION_VIEW);
                String mimeType = myMime.getMimeTypeFromExtension(fileExt(file.getAbsolutePath()).substring(1));
                newIntent.setDataAndType(Uri.fromFile(
                        new File(FileDownloadOperation.downloadToFolder(context)
                                +File.separator+file.getName())),mimeType);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    context.startActivity(newIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No handler for this type of file.", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(context,"Download started",Toast.LENGTH_LONG).show();
                dm = (DownloadManager)context.getSystemService(DOWNLOAD_SERVICE);

                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(ASSIGNMENT_DOWNLOAD_URL+assignment.getUrl()));

                String folder=null;

                if((folder=FileDownloadOperation.downloadToFolder(context))!=null){

                    Uri downloadLocation=Uri.fromFile(new File(folder, file.getName()));
                    request.setDestinationUri(downloadLocation);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    enqueue = dm.enqueue(request);

                }else{
                    Toast.makeText(context,"Something isn't wright",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String fileExt(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }
}
