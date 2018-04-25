package com.gmail.vsyniakin.services.impl;

import com.gmail.vsyniakin.model.entity.Step;
import com.gmail.vsyniakin.repository.interfaces.StepDAO;
import com.gmail.vsyniakin.services.interfaces.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StepServiceImpl implements StepService {

    @Autowired
    StepDAO stepDAO;

    @Override
    @Transactional
    public void delete(long id) {
        Step step = stepDAO.getReference(id);
        if (step != null) {
            stepDAO.delete(step);
        }
    }
}
