package org.springtest1.springtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springtest1.springtest.dao.entities.SessionData;
import org.springtest1.springtest.dao.repositories.SessionDataRepository;

@Service
public class SessionService {

    @Autowired
    private SessionDataRepository sessionDataRepository;

    // Méthode pour récupérer une session par ID de session
    public SessionData getSessionById(String sessionId) {
        return sessionDataRepository.findBySessionId(sessionId);
    }
}