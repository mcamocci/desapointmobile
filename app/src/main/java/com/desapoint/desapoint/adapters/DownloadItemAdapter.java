package com.desapoint.desapoint.adapters;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.desapoint.desapoint.R;
import com.desapoint.desapoint.pojos.DownloadableItem;
import com.desapoint.desapoint.toolsUtilities.ConstantInformation;
import com.desapoint.desapoint.toolsUtilities.FileDownloadOperation;
import com.desapoint.desapoint.toolsUtilities.PreferenceStorage;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.desapoint.desapoint.pojos.WindowInfo.ARTICLE;
import static com.desapoint.desapoint.pojos.WindowInfo.BOOK;
import static com.desapoint.desapoint.pojos.WindowInfo.NOTES;
import static com.desapoint.desapoint.pojos.WindowInfo.PASTPAPER;

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
        private TextView remark;
        private String type;

        public NoteViewHolder(View view){
            super(view);
            download=(ImageView)view.findViewById(R.id.download_action);
            download.setOnClickListener(this);
            share=(ImageView)view.findViewById(R.id.share_action);
            remark=(TextView)view.findViewById(R.id.remark);
            share.setOnClickListener(this);
            description=(TextView) view.findViewById(R.id.description);
            title=(TextView) view.findViewById(R.id.subject);
            view.setOnClickListener(this);
            type= PreferenceStorage.getWindowInfo(context);
        }

        public void setData(DownloadableItem item){
            this.downloadableItem=item;

            if(type.equals(ARTICLE)){
                item.setFile_url(ConstantInformation.ARTICLE_DOWNLOAD_URL+item.getFile_url());
                remark.setVisibility(View.VISIBLE);
                remark.setTextColor(Color.parseColor("#00C853"));
                remark.setText("UPloaded by "+item.getStatus());
            }else if(type.equals(BOOK)){
                item.setFile_url(ConstantInformation.BOOK_DOWNLOAD_URL+item.getFile_url());
                remark.setVisibility(View.VISIBLE);
                remark.setTextColor(Color.parseColor("#00C853"));
                remark.setText("UPloaded by "+item.getStatus());
            }else if(type.equals(NOTES)){

                item.setFile_url(ConstantInformation.NOTES_DOWNLOAD_URL+item.getFile_url());
                remark.setVisibility(View.VISIBLE);
                if(item.getStatus().contains("Official")){
                    remark.setText(item.getStatus());
                    remark.setTextColor(Color.parseColor("#00C853"));
                }else{
                    remark.setText(item.getStatus());
                    remark.setTextColor(Color.RED);
                }
            }else if(type.equals(PASTPAPER)){

                item.setFile_url(ConstantInformation.PASTPAPER_DOWNLOAD_URL+item.getFile_url());

            }

            File file=new File(item.getFile_url());
            description.setText(item.getName());
            title.setText(item.getDescription());
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
                Toast.makeText(context,downloadableItem.getFile_url(),Toast.LENGTH_LONG).show();
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
                String shared_content=downloadableItem.getName()+". "+downloadableItem.getDescription()+" documents are now availlable on desapoint" +
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
