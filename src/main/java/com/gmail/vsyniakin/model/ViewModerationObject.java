package com.gmail.vsyniakin.model;

import com.gmail.vsyniakin.model.entity.Message;
import com.gmail.vsyniakin.model.entity.Recipe;

import java.util.Date;
import java.util.List;

public class ViewModerationObject {

    public enum Type {RECIPE, MESSAGE}

    public enum Status {FALSE, TRUE, DELETE}

    private long id;
    private Type type;
    private Date date;
    private String info;
    private Status status;

    public ViewModerationObject() {
    }

    public ViewModerationObject(long id, Type type, Date date, String info, Status status) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.info = info;
        this.status = status;
    }

    public static List<ViewModerationObject> convertRecipesToListViewModeration(List<Recipe> recipes, List<ViewModerationObject> result) {

        for (Recipe recipe : recipes) {
            result.add(new ViewModerationObject(recipe.getId(), ViewModerationObject.Type.RECIPE, recipe.getDate(), recipe.getName(), (recipe.isModeration()) ? ViewModerationObject.Status.TRUE : ViewModerationObject.Status.FALSE));
        }

        return result;
    }

    public static List<ViewModerationObject> convertMessagesToListViewModeration(List<Message> messages, List<ViewModerationObject> result) {
        for (Message message : messages) {
            result.add(new ViewModerationObject(message.getId(), Type.MESSAGE, message.getDate(), message.getText(), (message.isModeration()) ? ViewModerationObject.Status.TRUE : ViewModerationObject.Status.FALSE));
        }
        return result;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
