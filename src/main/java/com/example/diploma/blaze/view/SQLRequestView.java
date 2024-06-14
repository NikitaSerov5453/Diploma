package com.example.diploma.blaze.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.example.diploma.entity.SQLRequest;

import java.util.UUID;

@EntityView(SQLRequest.class)
public interface SQLRequestView {

    @IdMapping
    UUID getId();

    String getRequest();

    UUID getSqlAuthorisationsId();
}
