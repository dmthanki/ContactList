package com.contacts.services.impl;

import com.contacts.dao.ConttactDao;
import com.contacts.dto.ContactDto;
import com.contacts.entity.Contact;
import com.contacts.exception.ContactNotFoundException;
import com.contacts.exception.CustomUniqueConstrainException;
import com.contacts.services.ContactServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactServicesImpl implements ContactServices {

    private final ConttactDao conttactDao;

    private final JavaMailSender javaMailSender;

    private final SimpleMailMessage simpleMailMessage;

    @Autowired
    public ContactServicesImpl(ConttactDao conttactDao, JavaMailSender javaMailSender, SimpleMailMessage simpleMailMessage){
        this.conttactDao = conttactDao;
        this.javaMailSender = javaMailSender;
        this.simpleMailMessage = simpleMailMessage;
    }

    @Override
    public List<ContactDto> getAllContacts() {
        return contactsToDtos(conttactDao.findAllwithoutDelete());
    }

    @Override
    public ContactDto getContactById(Integer id) throws ContactNotFoundException {
        Optional<Contact> contact =conttactDao.findById(id);
        if(conttactDao.existsById(id)){
            Contact cnt = contact.get();
            if(cnt.getDeleted().equals(Boolean.FALSE)) {
                ContactDto dto = contactToDto(contact.get());
                return dto;
            }
            else {
                throw new ContactNotFoundException("Contact Id Not Found with id : "+ id);
            }
        }
        else {
            throw new ContactNotFoundException("Contact Id Not Found with id : "+ id);
        }
    }

    @Override
    public ContactDto addContacts(ContactDto contactDto) throws CustomUniqueConstrainException {
        Contact c = dtoToContact(contactDto);
        try{
            conttactDao.save(c);
        }
        catch (DataIntegrityViolationException e){
            throw new CustomUniqueConstrainException("Field is already defined with this data Please Enter Unique Value");
        }
        return contactToDto(c);
    }

    @Override
    public ContactDto update(ContactDto contactDto, Integer id) throws ContactNotFoundException {
        Optional<Contact> cont = conttactDao.findById(id);
        if (conttactDao.existsById(id)){
            Contact updt =cont.get();
            if(updt.getDeleted().equals(Boolean.FALSE)){
                Contact cnt = dtoToContact(contactDto);
                updt.setFirstName(cnt.getFirstName());
                updt.setLastName(cnt.getLastName());
                updt.setEMail(cnt.getEMail());
                updt.setPhone(cnt.getPhone());

                try{
                    conttactDao.save(updt);
                }
                catch (DataIntegrityViolationException e){
                    throw new DataIntegrityViolationException("Please Enter Unique Record");
                }
                return contactToDto(cnt);
            }
            else {
                throw new ContactNotFoundException("No Data Found with id "+id);
            }
        }
        else {
            throw new ContactNotFoundException("No Data Found with id "+id);
        }
    }

    @Override
    public String remove(Integer id) throws ContactNotFoundException {
        Optional<Contact> contact = conttactDao.findById(id);
        if (conttactDao.existsById(id)){
            Contact del = contact.get();
            del.setDeleted(Boolean.TRUE);
            conttactDao.save(del);
            return "Contact deleted Successfully";
        }
        else {
            throw new ContactNotFoundException("No Record Found with id " + id);
        }
    }

    @Override
    public String forceRemove(Integer id) throws ContactNotFoundException {
        Optional<Contact> contact = conttactDao.findById(id);
        if (conttactDao.existsById(id)){
            Contact del = contact.get();
            conttactDao.delete(del);
            return "Contact deleted Permanently";
        }
        else {
            throw new ContactNotFoundException("No Record Found with id " + id);
        }
    }

    @Override
    public ContactDto restore(Integer id) throws ContactNotFoundException{
        Optional<Contact> contact = conttactDao.findById(id);
        if (conttactDao.existsById(id)){
            Contact del = contact.get();
            del.setDeleted(Boolean.FALSE);
            conttactDao.save(del);
            return contactToDto(del);
        }
        else {
            throw new ContactNotFoundException("No Record Found with id " + id);
        }
    }

    public Contact dtoToContact(ContactDto dto){
        Contact c = new Contact();

        c.setId(dto.getId());
        c.setFirstName(dto.getFirstName());
        c.setLastName(dto.getLastName());
        c.setEMail(dto.getEMail());
        c.setPhone(dto.getPhone());

        return c;
    }

    public ContactDto contactToDto(Contact c){
        ContactDto d = new ContactDto();

        d.setId(c.getId());
        d.setFirstName(c.getFirstName());
        d.setLastName(c.getLastName());
        d.setEMail(c.getEMail());
        d.setPhone(c.getPhone());

        return d;
    }

    public List<Contact> dtosToContacts(List<ContactDto> dtos){
        List<Contact> contacts = new ArrayList<Contact>();
        for (ContactDto d:
             dtos) {
            contacts.add(dtoToContact(d));
        }
        return contacts;
    }

    public List<ContactDto> contactsToDtos(List<Contact> c){
        List<ContactDto> dtos =new ArrayList<ContactDto>();
        for (Contact c1:
             c) {
            dtos.add(contactToDto(c1));
        }
        return dtos;
    }

    public String sendEmail(String to){
        String from = "dharmikthanki70@gmail.com";
        String msg = "Email is just for checking that it works in Spring boot or not";
        String sub = "Mail through Spring Boot";
        boolean f = false;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(sub);
        mailMessage.setText(msg);

        try{
            javaMailSender.send(mailMessage);
            f = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return (f == true) ? "Email Sent Successfully" : "Can't Send Email";
    }
}