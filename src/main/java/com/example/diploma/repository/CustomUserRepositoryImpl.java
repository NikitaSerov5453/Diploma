package com.example.diploma.repository;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.example.diploma.dto.view.UserView;
import com.example.diploma.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final EntityManager entityManager;

    private final CriteriaBuilderFactory criteriaBuilderFactory;

    private final EntityViewManager entityViewManager;

    @Override
    public Optional<UserView> findUserByUsername(String username) {
        return Optional.ofNullable(entityViewManager.applySetting(
                EntityViewSetting.create(UserView.class),
                criteriaBuilderFactory.create(entityManager, User.class))
                .where("username")
                .eq(username)
                .getSingleResult());
    }

    @Override
    public Optional<UserView> findUserByUserID(UUID id) {
        return Optional.ofNullable(entityViewManager.applySetting(
                EntityViewSetting.create(UserView.class),
                criteriaBuilderFactory.create(entityManager, User.class))
                .where("id")
                .eq(id)
                .getSingleResult());
    }

    @Override
    public List<UserView> findAllUsers() {
        return entityViewManager.applySetting(
                EntityViewSetting.create(UserView.class),
                criteriaBuilderFactory.create(entityManager, User.class))
                .from(User.class)
                .getResultList();
    }
}
