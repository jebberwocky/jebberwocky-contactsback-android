package com.jebberwocky.app.contactsbackup.export.xml;

import com.jebberwocky.app.contactsbackup.Contact;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created with IntelliJ IDEA.
 * User: jeffliu
 * Date: 5/13/12
 * Time: 1:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class XmlXstreamConverter implements Converter {
    public boolean canConvert(Class clazz) {
        return AbstractMap.class.isAssignableFrom(clazz);
    }

    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
        HashMap<String, Contact> map = (HashMap<String, Contact>) value;
        for (Entry<String,Contact> entry : map.entrySet()) {
            Contact c = entry.getValue();
            if(c.anyEmail()||c.anyPhoneNumbers()){
                writer.startNode("Contact");
                writer.startNode("Name");
                writer.setValue(c.getName());
                writer.endNode();
                if(c.anyPhoneNumbers()){
                    writer.startNode("Phone");
                    writer.setValue(c.getNumbers().toString());
                    writer.endNode();
                }
                if(c.anyEmail()){
                    writer.startNode("Email");
                    writer.setValue(c.getEmails().toString());
                    writer.endNode();
                }
                writer.endNode();
            }
        }
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        throw new UnsupportedOperationException();
    }
}
