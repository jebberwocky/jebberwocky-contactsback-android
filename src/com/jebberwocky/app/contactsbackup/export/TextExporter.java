package com.jebberwocky.app.contactsbackup.export;

import com.jebberwocky.app.contactsbackup.Contact;
import com.jebberwocky.app.contactsbackup.Contacts;


/**
 * Created with IntelliJ IDEA.
 * User: jeffliu
 * Date: 5/12/12
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class TextExporter extends AbstractExporter {

    @Override
    public String export(Contacts contacts) {
        StringBuilder sb = new StringBuilder();
        for (Object value : contacts.getContacts().values()) {
            Contact c = (Contact)value;
            if(c.anyPhoneNumbers()||c.anyEmail()){
                sb.append(c.getName()).append("\r\n");
                if(c.anyPhoneNumbers())
                    sb.append(c.getNumbers()).append("\r\n");
                if(c.anyEmail())
                    sb.append(c.getEmails()).append("\r\n");
            }
        }
        return sb.toString();
    }

    @Override
    protected String getFileName() {
        return "contacts.txt";
    }

    @Override
    protected String getFolderName() {
        return super.parent_folder;
    }
}
