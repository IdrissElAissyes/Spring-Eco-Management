//package org.springtest1.springtest.dao.repositories;
//
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springtest1.springtest.dao.entities.PasswordResetToken;
//import org.springtest1.springtest.dao.entities.User;
//
//import java.util.Date;
//import java.util.stream.Stream;
//
//public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
//
//    PasswordResetToken findByToken(String token);
//
//    PasswordResetToken findByUser(User user);
//
//    Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);
//
//    void deleteByExpiryDateLessThan(Date now);
//
//    @Modifying
//    @Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
//    void deleteAllExpiredSince(Date now);
//}
