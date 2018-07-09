package com.gmail.vsyniakin.repository.interfaces;

import com.gmail.vsyniakin.model.entity.Step;

public interface StepDAO {

	void delete(Step step);

	Step getReference(long id);
}
