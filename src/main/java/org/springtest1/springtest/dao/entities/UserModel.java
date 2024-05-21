package org.springtest1.springtest.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.aerogear.security.otp.api.Base32;

import java.sql.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    @Column(length = 60)
    private String password;
    private String tel;
    private String username;
    private String telfixe;
    @Column(length = 60)
    private String adresse;
    private Date DateCreation;
    private String carteNational;
    private UserRole role;
    @Lob
    @Column(columnDefinition="MEDIUMBLOB")
    private String imguser;
    // Relation OneToOne avec SessionData
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private SessionData session;
    private boolean enabled;
    private String secret;


}
