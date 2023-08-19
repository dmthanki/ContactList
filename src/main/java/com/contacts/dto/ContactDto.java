package com.contacts.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

public class ContactDto {

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    @NotBlank
    private String firstName;

    @Getter
    @Setter
    @NotBlank
    private String lastName;

    @Getter
    @Setter
    @Email(message = "Please Provide a Valid E-mail Address..")
    @NotBlank
    private String eMail;

    @Getter
    @Setter
    @Pattern(regexp = "[0-9]{10}",message = "Please Enter A Valid Mobile Number")
    @NotBlank
    private String phone;

//    @Getter
//    @Setter
//    private Boolean deleted = Boolean.FALSE;
}