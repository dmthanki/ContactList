package com.contacts.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "Email_Unique",
                        columnNames = "eMail"
                ),
                @UniqueConstraint(
                        name = "Number_Unique",
                        columnNames = "phone"
                )
        }
)
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String eMail;

    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter
    private Boolean deleted = Boolean.FALSE;

    public Contact(){

    }

}
