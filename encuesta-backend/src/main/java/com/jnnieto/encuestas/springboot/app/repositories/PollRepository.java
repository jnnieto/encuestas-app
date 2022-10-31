package com.jnnieto.encuestas.springboot.app.repositories;

import com.jnnieto.encuestas.springboot.app.entities.PollEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends CrudRepository<PollEntity, Long> {

    public PollEntity findByPollId(String id);

}
