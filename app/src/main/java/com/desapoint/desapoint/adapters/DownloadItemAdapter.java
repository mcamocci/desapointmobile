package com.desapoint.desapoint.adapters;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.desapoint.desapoint.R;
import com.desapoint.desapoint.pojos.DownloadableItem;
import com.desapoint.desapoint.pojos.Note;
import com.desapoint.desapoint.toolsUtilities.FileDownloadOperation;

import java.io.File;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by root on 3/13/17.
 */

public class DownloadItemAdapter extends RecyclerView.Adapter<DownloadItemAdapter.NoteViewHolder> {

    private List<DownloadableItem> content;
    private Context context;

    public DownloadItemAdapter(Context context, List<DownloadableItem> list){
        this.context=context;
        this.content=list;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.downloadable_item,parent,false);
        NoteViewHolder holder=new NoteViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        DownloadableItem item=content.get(position);
        holder.setData(item);
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView download;
        private ImageView share;
        private TextView title;
        private TextView description;
        private long enqueue;
        private DownloadManager dm;
        private DownloadableItem downloadableItem;

        public NoteViewHolder(View view){
            super(view);
            download=(ImageView)view.findViewById(R.id.download_action);
            download.setOnClickListener(this);
            share=(ImageView)view.findViewById(R.id.share_action);
            share.setOnClickListener(this);
            description=(TextView) view.findViewById(R.id.description);
            title=(TextView) view.findViewById(R.id.subject);
            view.setOnClickListener(this);

        }

        public void setData(DownloadableItem item){
            this.downloadableItem=item;
            item.setFile_url("https://www.csee.umbc.edu/courses/331/spring03/0101/lectures/java01.ppt");
            File file=new File(item.getFile_url());
            description.setText(item.getDescription());
            title.setText(item.getName());
            Log.e("url",FileDownloadOperation.downloadToFolder(context)+file.getName());
            if(FileDownloadOperation.isFileAvaillable(context,file)){
                download.setImageResource(R.drawable.ic_remove_red_eye);
            }else{
                download.setImageResource(R.drawable.ic_action_download);
            }
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.download_action){
                File file=new File(downloadableItem.getFile_url());
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
                            Uri.parse(downloadableItem.getFile_url()));

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

            }else if(v.getId()==R.id.share_action){
                String shared_content=downloadableItem.getDescription()+" documents are now availlable on desapoint" +
                        " Get it on google play today." +
                        " https://play.google.com/store/apps/details?id=com.desapoint.desapoint to access this file";
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,shared_content);
                Intent cooler_one=intent.createChooser(intent,"Share file using:-");
                cooler_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(cooler_one);
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
