package org.springtest1.springtest.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springtest1.springtest.dao.entities.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
  UserModel findByUsername(String username);

  List<UserModel> findByFirstnameContaining(String key);
  Optional<UserModel> findByCarteNational(String carteNational);
  @Query("SELECT u FROM UserModel u LEFT JOIN FETCH u.products")
  List<UserModel> findAllWithProducts();
    UserModel save(UserModel user);

}