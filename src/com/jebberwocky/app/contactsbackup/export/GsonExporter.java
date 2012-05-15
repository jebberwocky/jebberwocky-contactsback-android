package com.jebberwocky.app.contactsbackup.export;

import com.google.gson.Gson;
import com.jebberwocky.app.contactsbackup.Contacts;

/**
 * Created with IntelliJ IDEA.
 * User: jeffliu
 * Date: 5/12/12
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class GsonExporter extends AbstractExporter{

    @Override
    public String export(Contacts contacts) {
        Gson gson = new Gson();
        return gson.toJson(contacts.getContacts());
    }

    @Override
    protected String getFileName() {
        return "contacts.json";
    }

    @Override
    protected String getFolderName() {
        return super.parent_folder;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
