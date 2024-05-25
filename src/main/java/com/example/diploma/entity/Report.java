package com.example.diploma.entity;


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
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "report_name")
    private String name;

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "automated_reporting")
    private UUID automatedReporting;

    @Column(name = "report_creator")
    private String reportCreator;

    @JoinColumn(name = "report_id")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Addressee> addresses;

    @JoinColumn(name = "report_id")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<SQLAuthorisation> sqlAuthorisations;
}
