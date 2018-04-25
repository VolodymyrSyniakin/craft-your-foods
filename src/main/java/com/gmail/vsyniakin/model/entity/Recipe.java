package com.gmail.vsyniakin.model.entity;

import com.gmail.vsyniakin.model.enums.DifficultyRecipe;
import com.gmail.vsyniakin.model.enums.TypeRecipe;


import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static javax.persistence.TemporalType.TIMESTAMP;


@NamedEntityGraph(name = "recipe.fetchAll", attributeNodes = {
        @NamedAttributeNode("ingredientsByRecipe"),
        @NamedAttributeNode("steps"),
        @NamedAttributeNode("userAccount"),
})


@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(length = 1024)
    private String text;
    private double rating;
    private int timeMin;

    private boolean moderation;

    @Temporal(TIMESTAMP)
    private Date date;

    @Enumerated(EnumType.STRING)
    private TypeRecipe type;

    @Enumerated(EnumType.STRING)
    private DifficultyRecipe difficulty; // enums

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Image image;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<IngredientByRecipe> ingredientsByRecipe = new HashSet<IngredientByRecipe>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<Message> messages = new HashSet<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<Step> steps = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_acc_id")
    private UserAccount userAccount;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<RatingFromUser> ratingFromUsers;

    public Recipe() {
    }

    public void addLinksToEntities(UserAccount userAccount, Image image) {
        for (Step step : this.getSteps()) {
            step.setRecipe(this);
        }
        for (IngredientByRecipe ingredientByRecipe : this.getIngredientsByRecipe()) {
            ingredientByRecipe.setRecipe(this);
            ingredientByRecipe.getIngredient().getIngredientByRecipes().add(ingredientByRecipe);
        }
        addUserAccount(userAccount);
        if (image != null) {
            addImage(image);
        }
        if (this.getDate() == null) {
            this.setDate(new Date());
        }
        this.moderation = false;
    }

    public void updateRating() {
        double sum = 0;
        for (RatingFromUser rating : this.ratingFromUsers) {
            sum = sum + rating.getValue();
        }
        this.setRating(new BigDecimal((sum / this.ratingFromUsers.size())).setScale(1, RoundingMode.HALF_UP).doubleValue());
    }

    public void addUserAccount(UserAccount userAccount) {
        this.setUserAccount(userAccount);
        userAccount.getRecipes().add(this);
    }

    public void addImage(Image image) {
        this.setImage(image);
        image.setRecipe(this);
    }

    public void addStep(Step step) {
        this.getSteps().add(step);
        step.setRecipe(this);
    }

    public void addIngredientByRecipe(IngredientByRecipe ingredientByRecipe) {
        this.getIngredientsByRecipe().add(ingredientByRecipe);
        ingredientByRecipe.setRecipe(this);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(int timeMin) {
        this.timeMin = timeMin;
    }

    public DifficultyRecipe getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultyRecipe difficulty) {
        this.difficulty = difficulty;
    }

    public Set<IngredientByRecipe> getIngredientsByRecipe() {
        return ingredientsByRecipe;
    }

    public void setIngredientsByRecipe(Set<IngredientByRecipe> ingredientsByRecipe) {
        this.ingredientsByRecipe = ingredientsByRecipe;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public void setSteps(Set<Step> steps) {
        this.steps = steps;
    }

    public Set<Step> getSteps() {
        return steps;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public TypeRecipe getType() {
        return type;
    }

    public void setType(TypeRecipe type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<RatingFromUser> getRatingFromUsers() {
        return ratingFromUsers;
    }

    public void setRatingFromUsers(Set<RatingFromUser> ratingFromUsers) {
        this.ratingFromUsers = ratingFromUsers;
    }

    public boolean isModeration() {
        return moderation;
    }

    public void setModeration(boolean moderation) {
        this.moderation = moderation;
    }


}
