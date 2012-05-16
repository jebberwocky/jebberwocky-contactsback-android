package com.jebberwocky.app.contactsbackup.export;

import android.os.Environment;
import android.util.Log;
import com.jebberwocky.app.contactsbackup.Contacts;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


/**
 * Created with IntelliJ IDEA.
 * User: jeffliu
 * Date: 5/13/12
 * Time: 12:20 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractExporter implements Exporter{

    private String TAG;

    protected  String parent_folder = "/contacts-backup/";

    public AbstractExporter(){
        this.TAG = this.getClass().toString();
    }

    public abstract String export(Contacts contacts);

    protected  abstract  String getFileName();

    protected  String getFolderName(){
        return this.parent_folder;
    }

    private File getFile(){
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            //  to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        if(mExternalStorageAvailable&&mExternalStorageWriteable){
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File (sdCard.getAbsolutePath() + this.getFolderName());
            dir.mkdirs();
            File file = new File(dir, this.getFileName());
            return file;
        }else{
            Log.e(this.TAG
            ,"External storage is not available or writable");
            return null;
        }
    }

    public void ExportToFile(Contacts contacts){
        File file = this.getFile();
        if(file!=null){
            String data = this.export(contacts);
            try {
                FileOutputStream os = new FileOutputStream(file);
                OutputStreamWriter out = new OutputStreamWriter(os,"UTF-8");
                out.write(data);
                out.close();
            }catch (Exception e){
                Log.e(TAG, "Something wrong happens during writing file:" + e.toString());
            }
        }
    }
}
