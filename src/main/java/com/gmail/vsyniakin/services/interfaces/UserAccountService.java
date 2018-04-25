package com.gmail.vsyniakin.services.interfaces;

import com.gmail.vsyniakin.model.entity.UserAccount;

import java.util.List;


public interface UserAccountService {

    boolean existByLogin(String login);

    UserAccount getByLogin(String login);

    UserAccount getReference (long id);

    UserAccount getByIdFetchRecipes(long id);

    UserAccount getByIdFetchMessages(long id);

    UserAccount getByIdFetchRatingFromUser(long id);

    List<UserAccount> getByLoginFetchUserData (String login);

    UserAccount getByIdFetchUserData(long id);

    UserAccount update(UserAccount userAccount);
}
