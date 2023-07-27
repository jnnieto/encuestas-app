package com.jnnieto.encuestas.springboot.app.repositories;

import com.jnnieto.encuestas.springboot.app.entities.PollReplyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollReplyRepository extends CrudRepository<PollReplyEntity, Long> {
}
