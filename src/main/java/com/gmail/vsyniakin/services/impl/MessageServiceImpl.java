package com.gmail.vsyniakin.services.impl;

import com.gmail.vsyniakin.model.entity.Message;
import com.gmail.vsyniakin.model.entity.Recipe;
import com.gmail.vsyniakin.model.entity.UserAccount;
import com.gmail.vsyniakin.repository.interfaces.MessageDAO;
import com.gmail.vsyniakin.services.interfaces.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageDAO messageDAO;

    @Override
    @Transactional
    public Message add(Message message) {
        return messageDAO.add(message);
    }

    @Override
    @Transactional
    public Message update(Message message) {
        return messageDAO.update(message);
    }

    @Override
    @Transactional
    public Message getMessage(long id) {
        return messageDAO.getMessage(id);
    }

    @Override
    @Transactional
    public List<Message> getMessagesByRecipeFetchUserAccountAndSortByDate(Recipe recipe, int start, int count) {
        return messageDAO.getMessagesByRecipeFetchUserAccountAndSortByDate(recipe, start, count);
    }

    @Override
    @Transactional
    public int count(Recipe recipe) {
        return messageDAO.count(recipe);
    }

    @Override
    @Transactional
    public List<Message> getMessagesByUserAccountFetchRecipeAndSortByDate(UserAccount userAccount, int start, int count) {
        return messageDAO.getMessagesByUserAccountFetchRecipeAndSortByDate(userAccount, start, count);
    }

    @Override
    @Transactional
    public int count(UserAccount userAccount) {
        return messageDAO.count(userAccount);
    }

    @Override
    @Transactional
    public List<Message> getMessagesByModeration(boolean moderation, int start, int count) {
        return messageDAO.getMessagesByModeration(moderation, start, count);
    }

    @Override
    @Transactional
    public int count(boolean moderation) {
        return messageDAO.count(moderation);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Message message = messageDAO.getReference(id);
        messageDAO.delete(message);
    }

    @Override
    @Transactional
    public List<Message> getMessageByText(String text, int start, int count) {
        return messageDAO.getMessageByText(text, start, count);
    }

    @Override
    @Transactional
    public int count(String text) {
        return messageDAO.count(text);
    }

}
