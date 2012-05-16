package com.jebberwocky.app.contactsbackup.export;

import android.os.Environment;
import android.text.Html;
import android.util.Log;
import au.com.bytecode.opencsv.CSVWriter;
import com.jebberwocky.app.contactsbackup.Contact;
import com.jebberwocky.app.contactsbackup.Contacts;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jeffliu
 * Date: 5/13/12
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class CsvExporter implements Exporter {

    private String TAG = this.getClass().toString();

    protected  String parent_folder = "/contacts-backup/";

    /*
    @Override
    public String export(Contacts contacts) {
       //use ExportToFile
       throw new UnsupportedOperationException();
    }
    */

    @Override
    public void ExportToFile(Contacts contacts) {
        CSVWriter writer = null;
        String path = Environment.getExternalStorageDirectory()+parent_folder+"contacts.csv";
        try
        {
            List<String[]> results = new LinkedList<String[]>();
            writer = new CSVWriter(new FileWriter(path), ',');
            writer.writeNext(new String[]{"name","phone","email"});
            for (Object value : contacts.getContacts().values()) {
                Contact c = (Contact)value;

                String[] entries = new String[]{c.getName(),c.getNumbers().toString(),c.getEmails().toString()};
                results.add(entries);
            }
            writer.writeAll(results);
            writer.close();
        }
        catch (IOException e)
        {
            Log.d(TAG,"Something went wrong during writing csv:"+e.toString());
        }
    }
}
