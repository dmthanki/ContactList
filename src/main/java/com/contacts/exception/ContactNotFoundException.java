package com.contacts.exception;

public class ContactNotFoundException extends Throwable {

    public ContactNotFoundException(String s) {
        super(s);
    }
}