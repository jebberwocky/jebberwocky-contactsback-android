package com.jebberwocky.app.contactsbackup;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jeffliu
 * Date: 5/12/12
 * Time: 2:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class Contact {
    private String name;
    private List<String> numbers = new LinkedList<String>();
    private List<String> emails = new LinkedList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public Boolean anyPhoneNumbers(){
        return !(this.numbers==null||this.numbers.isEmpty());
    }

    public Boolean anyEmail(){
        return ! (this.emails==null||this.emails.isEmpty());
    }

    public Boolean isEmpty(){
        return !(anyEmail()||anyPhoneNumbers());
    }

    @Override
    public String toString() {
        return this.name;
    }
}
