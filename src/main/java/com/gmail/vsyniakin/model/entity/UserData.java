package com.gmail.vsyniakin.model.entity;

import com.gmail.vsyniakin.model.enums.RoleUser;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private RoleUser role;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "acc_id")
	private UserAccount userAccount;

	public UserData() {
	}

	public UserData(String email, String password, RoleUser role) {
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public void addUser(UserAccount userAccount) {
		this.setUserAccount(userAccount);
		userAccount.setUserData(this);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RoleUser getRole() {
		return role;
	}

	public void setRole(RoleUser role) {
		this.role = role;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
}
