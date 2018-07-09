package com.gmail.vsyniakin.services.impl;

import com.gmail.vsyniakin.model.entity.RatingFromUser;
import com.gmail.vsyniakin.model.entity.Recipe;
import com.gmail.vsyniakin.model.entity.UserAccount;
import com.gmail.vsyniakin.repository.interfaces.RatingFromUserDAO;
import com.gmail.vsyniakin.services.interfaces.RatingFromUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RatingFromUserServiceImpl implements RatingFromUserService {

	@Autowired
	RatingFromUserDAO ratingFromUserDAO;

	@Transactional
	@Override
	public RatingFromUser add(RatingFromUser ratingFromUser) {
		return ratingFromUserDAO.add(ratingFromUser);
	}

	@Transactional
	@Override
	public RatingFromUser update(RatingFromUser ratingFromUser) {
		return ratingFromUserDAO.update(ratingFromUser);
	}

	@Transactional
	@Override
	public RatingFromUser searchByRecipeAndUser(Recipe recipe, UserAccount userAccount) {
		return ratingFromUserDAO.searchByRecipeAndUser(recipe, userAccount);
	}
}
