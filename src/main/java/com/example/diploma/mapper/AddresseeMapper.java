package com.example.diploma.mapper;

import com.example.diploma.dto.AddresseeDto;
import com.example.diploma.entity.email.Addressee;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddresseeMapper {

    AddresseeDto toDto(Addressee addressee);

    Addressee toEntity(AddresseeDto addresseeDto);
}
