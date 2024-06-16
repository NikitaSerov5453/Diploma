package com.example.diploma.dto.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.FetchStrategy;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.example.diploma.entity.User;

import java.util.UUID;

@EntityView(User.class)
public interface UserView {

    @IdMapping
    UUID getId();

    String getUsername();

    String getPassword();

    Boolean getIsLocked();

    RoleView getRole();

    EmployeeView getEmployee();

}
