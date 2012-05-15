package com.jebberwocky.app.contactsbackup;

import com.jebberwocky.app.contactsbackup.export.Exporter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jeffliu
 * Date: 5/12/12
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class Contacts {
    private HashMap<String, Contact> contacts = new HashMap<String, Contact>();

    public void put(Contact contact){
        this.contacts.put(contact.getName(), contact);
    }

    public int getSize(){
        if(this.contacts!=null)
            return contacts.size();
        else
            return 0;
    }

    public HashMap getContacts(){
        return this.contacts;
    }
}
