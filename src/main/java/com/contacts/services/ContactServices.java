package com.contacts.services;

import com.contacts.dto.ContactDto;
import com.contacts.exception.ContactNotFoundException;
import com.contacts.exception.CustomUniqueConstrainException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ContactServices {
    public List<ContactDto> getAllContacts();

    public ContactDto getContactById(Integer id) throws ContactNotFoundException;

    public ContactDto addContacts(ContactDto contactDto) throws CustomUniqueConstrainException;

    public ContactDto update(ContactDto contactDto,Integer id) throws ContactNotFoundException;

    public String remove(Integer id) throws ContactNotFoundException;

    public String forceRemove(Integer id) throws ContactNotFoundException;

    public ContactDto restore(Integer id) throws ContactNotFoundException;

}
