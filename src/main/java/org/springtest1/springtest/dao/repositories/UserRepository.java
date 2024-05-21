package org.springtest1.springtest.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springtest1.springtest.dao.entities.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
  UserModel findByUsername(String username);

  List<UserModel> findByFirstnameContaining(String key);
  Optional<UserModel> findByCarteNational(String carteNational);

    UserModel save(UserModel user);

}