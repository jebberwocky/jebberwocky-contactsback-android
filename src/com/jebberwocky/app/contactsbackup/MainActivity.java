package com.jebberwocky.app.contactsbackup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;

import java.util.LinkedList;
import java.util.List;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jebberwocky.app.contactsbackup.export.*;

public class MainActivity extends Activity {
    final String TAG = this.getClass().toString();

    protected static final int STOP = 0x10000;
    protected static final int NEXT = 0x10001;
    protected static final int FAILED = 0x10009;

    private ProgressBar progressBar;
    private Button mButton;
    private AlertDialog.Builder alert;
    private TextView currentText;

    Contacts contacts = new Contacts();

    Intent intentContact = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        intentContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mButton = (Button) findViewById(R.id.btn_start);
        currentText = (TextView) findViewById(R.id.currentText);
        alert = new AlertDialog.Builder(this);

        mButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mButton.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
                currentText.setText(0 + "");
                Thread mThread = new Thread(new Runnable() {
                    public void run() {
                        getContactInfo(intentContact);
                    }
                });
                mThread.start();
            }
        });

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOP:
                    alert.setTitle("Finish!").setMessage("Total " + msg.arg2 + " contacts").setNeutralButton("OK", null).show();
                    mButton.setEnabled(true);
                    runExporter();
                    break;
                case NEXT:
                    if (!Thread.currentThread().isInterrupted()) {
                        progressBar.setProgress(msg.arg1);
                        currentText.setText(msg.arg2 + "");
                    }
                    break;
                case FAILED:
                    //TODO: add the handler for failed situation
                    break;
            }
        }
    };

    protected void getContactInfo(Intent intent) {
        Cursor cursor = null;

        int totalCount = cursor.getCount();
        int current = 0;
        try {
            cursor = managedQuery(intent.getData(), null, null, null, null);

            contacts = new Contacts();
            while (cursor.moveToNext()) {
                // Log.d(TAG, "---contact---");
                Contact c = new Contact();
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                //Log.d(TAG, "name:"+name);
                String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                c.setName(name);

                if (hasPhone.equalsIgnoreCase("1"))
                    hasPhone = "true";
                else
                    hasPhone = "false";

                if (Boolean.parseBoolean(hasPhone)) {
                    List<String> numbers = new LinkedList<String>();
                    Cursor phones = null;
                    try {
                        phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                        while (phones.moveToNext()) {
                            String phoneNumber;
                            phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            //Log.d(TAG, "phone:"+phoneNumber);
                            if (phoneNumber != null && phoneNumber.trim().length() > 0)
                                numbers.add(phoneNumber);
                        }
                    } finally {
                        if (phones != null)
                            phones.close();
                    }
                    c.setNumbers(numbers);
                }

                // Find Email Addresses
                Cursor emails = null;
                List<String> emailadrs = new LinkedList<String>();
                try {
                    emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);

                    while (emails.moveToNext()) {
                        String emailAddress;
                        emailAddress = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        if (emailAddress != null && emailAddress.trim().length() > 0)
                            emailadrs.add(emailAddress);
                    }
                } finally {
                    if (emails != null)
                        emails.close();
                }
                c.setEmails(emailadrs);
                /*
         Cursor address = getContentResolver().query(
                 ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                 null,
                 ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = " + contactId,
                 null, null);
         while (address.moveToNext())
         {
             // These are all private class variables, don't forget to create them.
             poBox      = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
             street     = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
             city       = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
             state      = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
             postalCode = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
             country    = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
             type       = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
         }  //address.moveToNext()     */
                if (!c.isEmpty())
                    this.contacts.put(c);
                current++;
                triggerMessage(totalCount, current, false);
            }  //while (cursor.moveToNext())
        } catch (Exception ex) {
            triggerMessage(totalCount, current, true);
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }

    }//getContactInfo

    private void triggerMessage(int totalCount, int current, boolean failed) {
        Message msg = new Message();
        msg.arg1 = (current * 100) / totalCount;
        msg.arg2 = current;
        if (!failed) {
            if (current == totalCount) {
                msg.what = STOP;
            } else {
                msg.what = NEXT;
            }
        } else {
            msg.what = FAILED;
        }
        mHandler.sendMessage(msg);
    }

    protected void runExporter() {
        List<Exporter> exporters = new LinkedList<Exporter>();
        exporters.add(new CsvExporter());
        exporters.add(new GsonExporter());
        exporters.add(new TextExporter());
        exporters.add(new XmlExporter());
        for (Exporter exporter : exporters) {
            exporter.ExportToFile(contacts);
        }
    }
}
