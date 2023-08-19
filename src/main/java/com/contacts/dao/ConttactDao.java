package com.contacts.dao;

import com.contacts.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ConttactDao extends JpaRepository<Contact,Integer> {
    @Query(value = "SELECT c FROM Contact c WHERE c.deleted=false")
    List<Contact> findAllwithoutDelete();

    @Query(value = "SELECT c.eMail FROM Contact c WHERE c.deleted=false ")
    List<String> eMails();

    @Query(value = "SELECT c.firstName FROM Contact c WHERE c.deleted=false ")
    List<String> pwd();
}