package com.gmail.vsyniakin.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.vsyniakin.model.View;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JsonView(View.Message.class)
    private String text;

    private boolean moderation;

    @Temporal(TIMESTAMP)
    @JsonView(View.Message.class)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_acc_id")
    @JsonView(View.Message.class)
    private UserAccount userAccount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public Message() {
    }

    public Message(String text, boolean moderation, Date date, UserAccount userAccount, Recipe recipe) {
        this.text = text;
        this.moderation = moderation;
        this.date = date;
        this.userAccount = userAccount;
        this.recipe = recipe;
    }

    public static Message addMessage(String text, UserAccount userAccount, Recipe recipe) {
        Message message = new Message(text, false, new Date(), userAccount, recipe);
        userAccount.getMessages().add(message);
        recipe.getMessages().add(message);
        return message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public boolean isModeration() {
        return moderation;
    }

    public void setModeration(boolean moderation) {
        this.moderation = moderation;
    }
}
