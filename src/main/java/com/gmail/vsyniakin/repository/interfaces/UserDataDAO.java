package com.gmail.vsyniakin.repository.interfaces;

import com.gmail.vsyniakin.model.entity.UserAccount;
import com.gmail.vsyniakin.model.entity.UserData;

public interface UserDataDAO {

	void add(UserData userData);

	void update(UserData userData);

	UserData getByEmail(String email);

	UserData getById(long id);

	boolean existsByEmail(String email);

	String getEmailByUserAccount(UserAccount userAccount);

}
