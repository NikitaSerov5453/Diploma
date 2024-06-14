package com.example.diploma.blaze.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.FetchStrategy;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.example.diploma.entity.Report;

import java.util.List;
import java.util.UUID;

@EntityView(Report.class)
public interface ReportView {

    @IdMapping
    UUID getId();

    String getName();

    String getCronExpression();

    UUID getAutomatedReporting();

    String getReportCreator();

    @Mapping(fetch = FetchStrategy.MULTISET)
    List<AddresseeView> getAddresses();

    @Mapping(fetch = FetchStrategy.MULTISET)
    List<SqlAuthorisationsView> getSqlAuthorisations();
}
