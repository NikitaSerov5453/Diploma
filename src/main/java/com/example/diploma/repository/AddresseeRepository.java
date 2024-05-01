package com.example.diploma.repository;

import com.example.diploma.entity.Addressee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddresseeRepository extends JpaRepository<Addressee, UUID> {

}
