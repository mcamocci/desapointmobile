package com.desapoint.desapoint.toolsUtilities;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/**
 * Created by root on 3/13/17.
 */

public class FileDownloadOperation {

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static String downloadToFolder(Context context) {

        ///checking for access of writting external storage of the device
        if (FileDownloadOperation.isExternalStorageWritable()) {
            ///creating app Folder//////////////////////////

            File appFolder = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "" +
                    "Desapoint" + File.separator + "files");

            ////creating folder when the stated folder above isnt availlable//////
            if (!appFolder.exists()) {
                appFolder.mkdirs();
                return Environment.getExternalStorageDirectory() + File.separator + "Desapoint" + File.separator
                        + "files";
                //Toast.makeText(context,"folder created",Toast.LENGTH_LONG).show();
            }else{
                return Environment.getExternalStorageDirectory() + File.separator + "Desapoint" + File.separator
                        + "files";
            }
        } else {
            Toast.makeText(context, "External storage is not accessible for write", Toast.LENGTH_LONG).show();

        }
        return null;
    }

    public static  boolean isFileAvaillable(Context context,File fileToDownload){
        String url=downloadToFolder(context)+File.separator+fileToDownload.getName();
        File file=new File(url);
        if(file.exists()){
            Log.e("file status","file available");
            return true;
        }
             Log.e("file status","file not available");
        return false;
    }
}

