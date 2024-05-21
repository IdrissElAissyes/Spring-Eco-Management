package org.springtest1.springtest.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springtest1.springtest.dao.entities.SessionData;
import org.springtest1.springtest.dao.entities.UserModel;


public interface SessionDataRepository extends CrudRepository<SessionData, Long> {
    SessionData findBySessionId(String sessionId);

}