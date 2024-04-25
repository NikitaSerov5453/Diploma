package com.example.diploma.entity;


import com.example.diploma.entity.automation.Automation;
import com.example.diploma.entity.email.Addressee;
import com.example.diploma.entity.sql.SQLAuthorisation;
import com.example.diploma.entity.sql.SQLRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @PrimaryKeyJoinColumn(name = "id")
    @OneToOne
    private Automation automation;

    @JoinColumn(name = "report_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Addressee> addresses;

    @JoinColumn(name = "report_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<SQLRequest> sqlRequests;

    @JoinColumn(name = "report_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<SQLAuthorisation> sqlAuthorisations;
}
