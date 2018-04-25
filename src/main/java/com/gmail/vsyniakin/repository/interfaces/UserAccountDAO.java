package com.gmail.vsyniakin.repository.interfaces;

import com.gmail.vsyniakin.model.entity.UserAccount;

import java.util.List;

public interface UserAccountDAO {

    UserAccount getByIdFetchRecipes(long id);

    UserAccount getReference(long id);

    UserAccount getByIdFetchMessages(long id);

    UserAccount getByIdFetchRatingFromUser(long id);

    boolean existByLogin(String login);

    UserAccount getByLogin(String login);

    List<UserAccount> getByLoginFetchUserData(String login);

    UserAccount getByIdFetchUserData(long id);

    UserAccount update(UserAccount userAccount);

}
