package com.gmail.vsyniakin.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.vsyniakin.model.View;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "user_accounts")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonView(View.Message.class)
    private String login;

    private String info;

    @Temporal(TIMESTAMP)
    private Date dateRegistration;

    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY)
    private Set<Message> messages = new HashSet<>();

    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY)
    private Set<Recipe> recipes = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserData userData;

    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY)
    private Set<RatingFromUser> ratingFromUser = new HashSet<>();

    public UserAccount() {
    }

    public UserAccount(String login) {
        this.dateRegistration = new Date();
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Set<RatingFromUser> getRatingFromUser() {
        return ratingFromUser;
    }

    public void setRatingFromUser(Set<RatingFromUser> ratingFromUser) {
        this.ratingFromUser = ratingFromUser;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(Date dateRegistration) {
        this.dateRegistration = dateRegistration;
    }
}
