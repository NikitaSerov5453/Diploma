package com.example.diploma.entity;

import com.example.diploma.dto.ReportDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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

    @Column(name = "is_locked")
    @Builder.Default
    private Boolean isLocked = false;

    @JoinColumn(name = "role_id")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Role role;

    @JoinColumn(name = "employee_id")
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Employee employee;

    @JoinColumn(name = "report_creator")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Report> reports;
}