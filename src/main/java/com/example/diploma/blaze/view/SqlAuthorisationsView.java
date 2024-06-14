package com.example.diploma.blaze.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.FetchStrategy;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.example.diploma.entity.SQLAuthorisation;
import com.example.diploma.entity.SQLRequest;

import java.util.List;
import java.util.UUID;

@EntityView(SQLAuthorisation.class)
public interface SqlAuthorisationsView {

    @IdMapping
    UUID getId();

    String getUrl();

    String getLogin();

    String getPassword();

    UUID getReportId();

    @Mapping(fetch = FetchStrategy.MULTISET)
    List<SQLRequestView> getSqlRequests();
}
