package com.example.diploma.dto.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.example.diploma.entity.Employee;

import java.util.UUID;

@EntityView(Employee.class)
public interface EmployeeView {

    @IdMapping
    UUID getId();

    String getName();

    String getLastName();

    String getPatronymicName();
}
