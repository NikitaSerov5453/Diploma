package com.example.diploma.repository;


import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.example.diploma.dto.view.ReportView;
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
    public Optional<ReportView> findReportByReportId(UUID id) {
        return Optional.ofNullable(entityViewManager.applySetting(
                        EntityViewSetting.create(ReportView.class),
                        criteriaBuilderFactory.create(entityManager, Report.class))
                .where("id")
                .eq(id)
                .getSingleResult());
    }

    @Override
    public List<ReportView> findAllReportsView() {
        return entityViewManager.applySetting(
                EntityViewSetting.create(ReportView.class),
                criteriaBuilderFactory.create(entityManager, Report.class))
                .from(Report.class)
                .getResultList();
    }

    @Override
    public List<ReportView> findReportsByReportCreator(String reportCreator) {
        return entityViewManager.applySetting(
                EntityViewSetting.create(ReportView.class),
                criteriaBuilderFactory.create(entityManager, Report.class))
                .from(Report.class)
                .where("reportCreator")
                .eq(reportCreator)
                .getResultList();
    }
}
