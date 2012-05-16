package com.jebberwocky.app.contactsbackup.export;

import com.jebberwocky.app.contactsbackup.Contacts;

/**
 * Created with IntelliJ IDEA.
 * User: jeffliu
 * Date: 5/12/12
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Exporter {
    //public String export(Contacts contacts);
    public void ExportToFile(Contacts contacts);
}
