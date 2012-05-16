package com.jebberwocky.app.contactsbackup.export;

import a_vcard.android.syncml.pim.vcard.ContactStruct;
import a_vcard.android.syncml.pim.vcard.VCardComposer;
import a_vcard.android.syncml.pim.vcard.VCardException;
import com.jebberwocky.app.contactsbackup.Contact;
import com.jebberwocky.app.contactsbackup.Contacts;

/**
 * Created with IntelliJ IDEA.
 * User: chihyuanliu
 * Date: 5/16/12
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class VCardExporter extends AbstractExporter {
    @Override
    public String export(Contacts contacts) {
        StringBuilder sb = new StringBuilder();
        for (Object value : contacts.getContacts().values()) {
            Contact c = (Contact)value;
            VCardComposer composer = new VCardComposer();

            //create a contact
            ContactStruct contact1 = new ContactStruct();
            contact1.name = c.getName();
            for(String v : c.getNumbers()){
                contact1.addPhone(a_vcard.android.provider.Contacts.Phones.TYPE_MOBILE, v, null, true);
            }

            try {
                String vcardString = null;
                vcardString = composer.createVCard(contact1, VCardComposer.VERSION_VCARD30_INT);
                sb.append(vcardString);
            } catch (VCardException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return sb.toString();
    }

    @Override
    protected String getFileName() {
        return "contacts.vcard";
    }
}
