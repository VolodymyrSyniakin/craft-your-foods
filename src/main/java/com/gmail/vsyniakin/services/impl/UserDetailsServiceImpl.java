package com.gmail.vsyniakin.services.impl;

import com.gmail.vsyniakin.model.entity.UserData;
import com.gmail.vsyniakin.services.interfaces.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserDataService userDataService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserData userData;
		try {
			userData = userDataService.getByEmail(email);
		} catch (PersistenceException e) {
			userData = null;
		}

		if (userData == null) {
			throw new UsernameNotFoundException(email + " not found");
		}
		Set<GrantedAuthority> roles = new HashSet<>();
		roles.add(new SimpleGrantedAuthority(userData.getRole().toString()));

		return new User(userData.getEmail(), userData.getPassword(), roles);
	}
}
