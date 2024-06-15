package com.example.diploma.dto.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.FetchStrategy;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.example.diploma.entity.SQLAuthorisation;

import java.util.List;
import java.util.UUID;

@EntityView(SQLAuthorisation.class)
public interface SqlAuthorisationsView {

    @IdMapping
    UUID getId();

    String getUrl();

    String getLogin();

    String getPassword();

    @Mapping(fetch = FetchStrategy.MULTISET)
    List<SQLRequestView> getSqlRequests();
}
