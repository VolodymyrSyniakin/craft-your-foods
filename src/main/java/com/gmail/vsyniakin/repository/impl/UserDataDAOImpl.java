package com.gmail.vsyniakin.repository.impl;

import com.gmail.vsyniakin.model.entity.UserAccount;
import com.gmail.vsyniakin.model.entity.UserData;
import com.gmail.vsyniakin.repository.interfaces.UserDataDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Repository
public class UserDataDAOImpl implements UserDataDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(UserData userData) {
        entityManager.persist(userData);
    }

    @Override
    public void update(UserData userData) {
        entityManager.merge(userData);
    }

    @Override
    public UserData getByEmail(String email) {
        TypedQuery<UserData> query = entityManager.createQuery("SELECT u FROM UserData u WHERE u.email = :email", UserData.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public UserData getById(long id) {
        TypedQuery<UserData> query = entityManager.createQuery("SELECT u FROM UserData u WHERE u.id = :id", UserData.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public boolean existsByEmail(String email) {
        TypedQuery<UserData> query = entityManager.createQuery("SELECT u FROM UserData u WHERE u.email = :email", UserData.class);
        query.setParameter("email", email);
        return !query.getResultList().isEmpty();
    }

    @Override
    public String getEmailByUserAccount(UserAccount userAccount) {
        TypedQuery<String> query = entityManager.createQuery("SELECT u.email FROM UserData u WHERE u.userAccount = :userAccount", String.class);
        query.setParameter("userAccount", userAccount);
        return query.getSingleResult();
    }

}
