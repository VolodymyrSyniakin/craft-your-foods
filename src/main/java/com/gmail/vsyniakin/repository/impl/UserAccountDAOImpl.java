package com.gmail.vsyniakin.repository.impl;

import com.gmail.vsyniakin.model.entity.UserAccount;
import com.gmail.vsyniakin.repository.interfaces.UserAccountDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserAccountDAOImpl implements UserAccountDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean existByLogin(String login) {
		TypedQuery<UserAccount> query = entityManager.createQuery("SELECT u FROM UserAccount u WHERE u.login = :login",
				UserAccount.class);
		query.setParameter("login", login);
		return !query.getResultList().isEmpty();
	}

	@Override
	public UserAccount getReference(long id) {
		return entityManager.getReference(UserAccount.class, id);
	}

	@Override
	public UserAccount update(UserAccount userAccount) {
		return entityManager.merge(userAccount);
	}

	@Override
	public UserAccount getByLogin(String login) {
		TypedQuery<UserAccount> query = entityManager.createQuery("SELECT u FROM UserAccount u WHERE u.login = :login",
				UserAccount.class);
		query.setParameter("login", login);
		return query.getSingleResult();
	}

	@Override
	public List<UserAccount> getByLoginFetchUserData(String login) {
		TypedQuery<UserAccount> query = entityManager.createQuery(
				"SELECT u FROM UserAccount u LEFT JOIN FETCH u.userData WHERE u.login LIKE :login", UserAccount.class);
		query.setParameter("login", "%" + login + "%");
		return query.getResultList();
	}

	@Override
	public UserAccount getByIdFetchRecipes(long id) {
		TypedQuery<UserAccount> query = entityManager.createQuery(
				"SELECT u FROM UserAccount u LEFT JOIN FETCH u.recipes WHERE u.id = :id", UserAccount.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

	@Override
	public UserAccount getByIdFetchMessages(long id) {
		TypedQuery<UserAccount> query = entityManager.createQuery(
				"SELECT u FROM UserAccount u LEFT JOIN FETCH u.messages WHERE u.id = :id", UserAccount.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

	@Override
	public UserAccount getByIdFetchUserData(long id) {
		TypedQuery<UserAccount> query = entityManager.createQuery(
				"SELECT u FROM UserAccount u LEFT JOIN FETCH u.userData WHERE u.id = :id", UserAccount.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

	@Override
	public UserAccount getByIdFetchRatingFromUser(long id) {
		TypedQuery<UserAccount> query = entityManager.createQuery(
				"SELECT u FROM UserAccount u LEFT JOIN FETCH u.ratingFromUser WHERE u.id = :id", UserAccount.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

}
