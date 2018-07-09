package com.gmail.vsyniakin.repository.impl;

import com.gmail.vsyniakin.model.entity.Step;
import com.gmail.vsyniakin.repository.interfaces.StepDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class StepDAOImpl implements StepDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Step getReference(long id) {
		return entityManager.getReference(Step.class, id);
	}

	@Override
	public void delete(Step step) {
		entityManager.remove(step);
	}
}
