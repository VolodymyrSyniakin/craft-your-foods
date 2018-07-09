package com.gmail.vsyniakin.repository.impl;

import com.gmail.vsyniakin.model.entity.RatingFromUser;
import com.gmail.vsyniakin.model.entity.Recipe;
import com.gmail.vsyniakin.model.entity.UserAccount;
import com.gmail.vsyniakin.repository.interfaces.RatingFromUserDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Repository
public class RatingFromUserDAOImpl implements RatingFromUserDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public RatingFromUser add(RatingFromUser ratingFromUser) {
		return entityManager.merge(ratingFromUser);
	}

	@Override
	public RatingFromUser update(RatingFromUser ratingFromUser) {
		return entityManager.merge(ratingFromUser);
	}

	@Override
	public RatingFromUser searchByRecipeAndUser(Recipe recipe, UserAccount userAccount) {
		TypedQuery<RatingFromUser> query = entityManager.createQuery(
				"SELECT r FROM RatingFromUser r JOIN FETCH r.recipe JOIN FETCH r.userAccount WHERE r.recipe = :recipe AND r.userAccount = :userAccount",
				RatingFromUser.class);
		query.setParameter("recipe", recipe);
		query.setParameter("userAccount", userAccount);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
}
