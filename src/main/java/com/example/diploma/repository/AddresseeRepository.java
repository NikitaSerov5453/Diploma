package com.example.diploma.repository;

import com.example.diploma.entity.Report;
import com.example.diploma.entity.email.Addressee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddresseeRepository extends JpaRepository<Addressee, UUID> {

}
