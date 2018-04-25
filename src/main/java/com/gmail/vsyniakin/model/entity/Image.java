package com.gmail.vsyniakin.model.entity;


import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(length = 1048576)
    private byte[] image;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Recipe recipe;

    public Image(byte[] image) {
        this.image = image;
    }

    public Image() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

}
