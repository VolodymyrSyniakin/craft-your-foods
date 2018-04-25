package com.gmail.vsyniakin.services.impl;

import com.gmail.vsyniakin.model.entity.UserAccount;
import com.gmail.vsyniakin.repository.interfaces.UserAccountDAO;
import com.gmail.vsyniakin.repository.interfaces.UserDataDAO;
import com.gmail.vsyniakin.services.interfaces.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    UserAccountDAO userAccountDAO;
    @Autowired
    UserDataDAO userDataDAO;

    @Override
    public boolean existByLogin(String login) {
        return userAccountDAO.existByLogin(login);
    }

    @Override
    public UserAccount getReference(long id) {
        return userAccountDAO.getReference(id);
    }

    @Override
    public UserAccount getByIdFetchRecipes(long id) {
        return userAccountDAO.getByIdFetchRecipes(id);
    }

    @Override
    public UserAccount getByIdFetchMessages(long id) {
        return userAccountDAO.getByIdFetchMessages(id);
    }

    @Override
    public UserAccount getByIdFetchRatingFromUser(long id) {
        return userAccountDAO.getByIdFetchRatingFromUser(id);
    }

    @Override
    public UserAccount getByLogin(String login) {
        return userAccountDAO.getByLogin(login);
    }

    @Override
    public List<UserAccount> getByLoginFetchUserData(String login) {
        return userAccountDAO.getByLoginFetchUserData(login);
    }

    @Override
    public UserAccount getByIdFetchUserData(long id) {
        return userAccountDAO.getByIdFetchUserData(id);
    }

    @Override
    public UserAccount update(UserAccount userAccount) {
        return userAccountDAO.update(userAccount);
    }
}
