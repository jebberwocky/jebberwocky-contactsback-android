package com.jebberwocky.app.contactsbackup.export;

import com.jebberwocky.app.contactsbackup.Contacts;
import com.jebberwocky.app.contactsbackup.export.xml.XmlXstreamConverter;
import com.thoughtworks.xstream.XStream;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jeffliu
 * Date: 5/13/12
 * Time: 1:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class XmlExporter extends AbstractExporter {
    @Override
    public String export(Contacts contacts) {
        XStream magicApi = new XStream();
        magicApi.alias("Contacts", Map.class);
        magicApi.registerConverter(new XmlXstreamConverter());
        String xml = magicApi.toXML(contacts.getContacts());
        return xml;
    }

    @Override
    protected String getFileName() {
        return "contacts.xml";
    }

    @Override
    protected String getFolderName() {
        return super.parent_folder;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
