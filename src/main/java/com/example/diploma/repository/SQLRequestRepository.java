package com.example.diploma.repository;

import com.example.diploma.entity.SQLRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SQLRequestRepository extends JpaRepository<SQLRequest, UUID> {

    List<SQLRequest> findBySqlAuthorisationsId(UUID sqlAuthorisationsId);
}
