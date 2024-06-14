package com.example.diploma.blaze.repository;


import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.example.diploma.blaze.view.ReportView;
import com.example.diploma.entity.Report;
import jakarta.persistence.EntityManager;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class CustomReportRepositoryImpl implements CustomReportRepository {

    private final EntityManager entityManager;

    private final CriteriaBuilderFactory criteriaBuilderFactory;

    private final EntityViewManager entityViewManager;

    @Override
    public Optional<Report> findByReportId(UUID id) {

        return Optional.ofNullable(criteriaBuilderFactory
                .create(entityManager, Report.class)
                .where("id")
                .eq(id)
//                .fetch("addresses")
                .fetch("sqlAuthorisations")
                .getSingleResult());
    }

    @Override
    public Optional<ReportView> searchByReportViewId(UUID id) {
        return Optional.ofNullable(entityViewManager.applySetting(
                        EntityViewSetting.create(ReportView.class),
                        criteriaBuilderFactory.create(entityManager, Report.class))
                .where("id")
                .eq(id)
                .getSingleResult());
    }




    @Override
    public List<Report> findAllReports() {
        return criteriaBuilderFactory
                .create(entityManager, Report.class)
                .from(Report.class)
//                .leftJoinFetch("addresses", "a")
                .fetch("addresses")
//                .fetch("sqlAuthorisations")
                .getResultList();
    }


    @Override
    public List<ReportView> findAllReportsView() {
        return entityViewManager.applySetting(
                EntityViewSetting.create(ReportView.class),
                criteriaBuilderFactory.create(entityManager, Report.class))
                .from(Report.class)
                .getResultList();
    }
}
