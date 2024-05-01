package com.example.diploma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @JoinColumn(name = "role_id")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Role role;

    @JoinColumn(name = "employee_id")
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Employee employee;

//    @JoinColumn(name = "email_configuration_id")
//    @OneToMany(cascade = {CascadeType.ALL})
//    private List<EmailConfiguration> emailConfiguration;
}
