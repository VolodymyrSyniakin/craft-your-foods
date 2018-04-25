package com.gmail.vsyniakin.repository.interfaces;

import com.gmail.vsyniakin.model.entity.Message;
import com.gmail.vsyniakin.model.entity.Recipe;
import com.gmail.vsyniakin.model.entity.UserAccount;

import java.util.List;

public interface MessageDAO {

    Message add(Message message);

    Message update(Message message);

    Message getMessage(long id);

    Message getReference(long id);

    List<Message> getMessagesByRecipeFetchUserAccountAndSortByDate(Recipe recipe, int start, int count);

    int count(Recipe recipe);

    List<Message> getMessagesByUserAccountFetchRecipeAndSortByDate(UserAccount userAccount, int start, int count);

    int count(UserAccount userAccount);

    List<Message> getMessagesByModeration(boolean moderation, int start, int count);

    int count(boolean moderation);

    void delete(Message message);

    List<Message> getMessageByText(String text, int start, int count);

    int count(String text);
}

