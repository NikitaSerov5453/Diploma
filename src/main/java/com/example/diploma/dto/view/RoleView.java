package com.example.diploma.dto.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.FetchStrategy;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.example.diploma.entity.Role;
import com.example.diploma.enums.RoleType;

import java.util.UUID;

@EntityView(Role.class)
public interface RoleView {

    @IdMapping
    UUID getId();

    RoleType getRoleType();
}
