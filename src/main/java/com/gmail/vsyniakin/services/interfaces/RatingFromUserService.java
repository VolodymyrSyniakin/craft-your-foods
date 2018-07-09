package com.gmail.vsyniakin.services.interfaces;

import com.gmail.vsyniakin.model.entity.RatingFromUser;
import com.gmail.vsyniakin.model.entity.Recipe;
import com.gmail.vsyniakin.model.entity.UserAccount;

public interface RatingFromUserService {

	RatingFromUser add(RatingFromUser ratingFromUser);

	RatingFromUser update(RatingFromUser ratingFromUser);

	RatingFromUser searchByRecipeAndUser(Recipe recipe, UserAccount userAccount);

}
