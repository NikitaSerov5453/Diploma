package com.example.diploma.dto.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.example.diploma.entity.Addressee;

import java.util.UUID;

@EntityView(Addressee.class)
public interface AddresseeView {

    @IdMapping
    UUID getId();

    String getEmail();
}
