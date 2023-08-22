package com.contacts.controller;

import com.contacts.dto.ContactDto;
import com.contacts.exception.ContactNotFoundException;
import com.contacts.exception.CustomUniqueConstrainException;
import com.contacts.services.ContactServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController
@RequestMapping("contactlist")
public class ContactController {

    private final ContactServices contactServices;
    private final HttpServletRequest request;

    @Autowired
    public ContactController(ContactServices contactServices,HttpServletRequest request){
        this.contactServices = contactServices;
        this.request = request;
    }

    @GetMapping("contact")
    public ResponseEntity<List<ContactDto>> getAllContacts(){
        return new ResponseEntity<>(contactServices.getAllContacts(),HttpStatus.OK);
    }

    @GetMapping("contact/{id}")
    public ResponseEntity<ContactDto> getContById(@PathVariable @Valid Integer id) throws ContactNotFoundException {
        return new ResponseEntity(contactServices.getContactById(id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @PostMapping("addcontact")
    public ResponseEntity<ContactDto> add(@RequestBody @Valid ContactDto contactDto) throws CustomUniqueConstrainException {
        return new ResponseEntity<>(contactServices.addContacts(contactDto),HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @PutMapping("updatecontact/{id}")
    public ResponseEntity<ContactDto> update(@RequestBody @Valid ContactDto contactDto,@PathVariable Integer id) throws CustomUniqueConstrainException, ContactNotFoundException {
        return new ResponseEntity<>(contactServices.update(contactDto,id),HttpStatus.OK);
    }


    @DeleteMapping("remove/{id}")
    public ResponseEntity<String> remove(@PathVariable Integer id) throws ContactNotFoundException{
        return new ResponseEntity<>(contactServices.remove(id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @DeleteMapping("forcedelete/{id}")
    public ResponseEntity<String> forceRemove(@PathVariable Integer id) throws ContactNotFoundException {
        return new ResponseEntity<>(contactServices.forceRemove(id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @GetMapping("restore/{id}")
    public ResponseEntity<ContactDto> restore(@PathVariable Integer id) throws ContactNotFoundException {
        return new ResponseEntity<>(contactServices.restore(id),HttpStatus.OK);
    }

    @PostMapping("sendeml")
    public ResponseEntity<String> sendEmail(@RequestBody String to){
        return new ResponseEntity<>(contactServices.sendEmail(to),HttpStatus.OK);
    }

    @GetMapping("getip")
    public ResponseEntity<String> storeIp(){
        String ip = request.getHeader("X-Forwarded-For");

        if(ip == null || ip.isEmpty()){
            ip = request.getRemoteAddr();
        }

        return new ResponseEntity<>(ip,HttpStatus.OK);
    }
}
