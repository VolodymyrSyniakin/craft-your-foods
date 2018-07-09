package com.gmail.vsyniakin.services.impl;

import com.gmail.vsyniakin.model.entity.UserAccount;
import com.gmail.vsyniakin.model.entity.UserData;
import com.gmail.vsyniakin.repository.interfaces.UserDataDAO;
import com.gmail.vsyniakin.services.interfaces.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDataServiceImpl implements UserDataService {
	@Autowired
	private UserDataDAO userDataDAO;

	@Override
	public void add(UserData userData) {
		userDataDAO.add(userData);
	}

	@Override
	public void update(UserData userData) {
		userDataDAO.update(userData);
	}

	@Override
	public UserData getByEmail(String email) {
		return userDataDAO.getByEmail(email);
	}

	@Override
	public UserData getById(long id) {
		return userDataDAO.getById(id);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userDataDAO.existsByEmail(email);
	}

	@Override
	public String getEmailByUserAccount(UserAccount userAccount) {
		return userDataDAO.getEmailByUserAccount(userAccount);
	}

}
