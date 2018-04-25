package com.gmail.vsyniakin.repository.impl;

import com.gmail.vsyniakin.model.entity.Message;
import com.gmail.vsyniakin.model.entity.Recipe;
import com.gmail.vsyniakin.model.entity.UserAccount;
import com.gmail.vsyniakin.repository.interfaces.MessageDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class MessageDAOImpl implements MessageDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Message add(Message message) {
        return entityManager.merge(message);
    }

    @Override
    public Message update(Message message) {
        return entityManager.merge(message);
    }

    @Override
    public Message getReference(long id) {
        return entityManager.getReference(Message.class, id);
    }

    @Override
    public Message getMessage(long id) {
        return entityManager.find(Message.class, id);
    }


    @Override
    public List<Message> getMessagesByRecipeFetchUserAccountAndSortByDate(Recipe recipe, int start, int count) {
        TypedQuery<Message> query = entityManager.createQuery("SELECT m FROM Message m LEFT JOIN FETCH m.userAccount WHERE m.recipe = :recipe ORDER BY m.date DESC", Message.class);
        query.setParameter("recipe", recipe);
        query.setFirstResult(start);
        query.setMaxResults(count);
        return query.getResultList();
    }

    @Override
    public int count(Recipe recipe) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(m) FROM Message m WHERE m.recipe = :recipe", Long.class);
        query.setParameter("recipe", recipe);
        return query.getSingleResult().intValue();
    }


    @Override
    public List<Message> getMessagesByUserAccountFetchRecipeAndSortByDate(UserAccount userAccount, int start, int count) {
        TypedQuery<Message> query = entityManager.createQuery("SELECT m FROM Message m LEFT JOIN FETCH m.recipe WHERE m.userAccount = :userAccount ORDER BY m.date DESC", Message.class);
        query.setParameter("userAccount", userAccount);
        query.setFirstResult(start);
        query.setMaxResults(count);
        return query.getResultList();
    }

    @Override
    public int count(UserAccount userAccount) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(m) FROM Message m WHERE m.userAccount = :userAccount", Long.class);
        query.setParameter("userAccount", userAccount);
        return query.getSingleResult().intValue();
    }


    @Override
    public List<Message> getMessagesByModeration(boolean moderation, int start, int count) {
        TypedQuery<Message> query = entityManager.createQuery("SELECT m FROM Message m WHERE m.moderation = :moderation", Message.class);
        query.setParameter("moderation", moderation);
        query.setFirstResult(start);
        query.setMaxResults(count);
        return query.getResultList();
    }

    @Override
    public int count(boolean moderation) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(m) FROM Message m WHERE m.moderation = :moderation", Long.class);
        query.setParameter("moderation", moderation);
        return query.getSingleResult().intValue();
    }

    @Override
    public void delete(Message message) {
        entityManager.remove(message);
    }


    @Override
    public List<Message> getMessageByText(String text, int start, int count) {
        TypedQuery<Message> query = entityManager.createQuery("SELECT m FROM Message m WHERE m.text LIKE :text", Message.class);
        query.setParameter("text", "%" + text + "%");
        query.setFirstResult(start);
        query.setMaxResults(count);
        return query.getResultList();
    }

    @Override
    public int count(String text) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(m) FROM Message m WHERE m.text LIKE :text", Long.class);
        query.setParameter("text", "%" + text + "%");
        return query.getSingleResult().intValue();
    }
}
