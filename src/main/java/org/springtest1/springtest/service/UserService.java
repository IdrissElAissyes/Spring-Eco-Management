package org.springtest1.springtest.service;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springtest1.springtest.dao.entities.*;
import org.springtest1.springtest.dao.repositories.SessionDataRepository;
import org.springtest1.springtest.dao.repositories.UserRepository;

import java.io.IOException;
import java.sql.Date;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService  implements UserManager {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionDataRepository sessionDataRepository;

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
    @Override
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public List<UserModel> searchEmplyee(String keyword) {
        return userRepository.findByFirstnameContaining(keyword);
    }
    @Override
    public UserModel findById(String carteNational) {
        Optional<UserModel> user = userRepository.findByCarteNational(carteNational);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found with carteNational: " + carteNational);
        }
    }

    @Override
    public UserModel getUserById(Long id) {
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
    @Override
    public void updateUser(Long userId,String firstname,
                           String lastName, String username ,String pwd,String tel,String telfix,
                           Date date,String cartN,String adresse) {
        // Récupérer le produit à partir de l'ID
        UserModel user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            // Mettre à jour les propriétés du produit avec les nouvelles valeurs
            user.setFirstname(firstname);
            user.setLastname(lastName);
            user.setUsername(username);
            user.setTel(tel);
            user.setTelfixe(telfix);
            user.setDateCreation(date);
            user.setCarteNational(cartN);
            user.setAdresse(adresse);

        }
    }
    @PostConstruct
    public void postConstruct() {
        initializeAdminUser();
    }
    public void initializeAdminUser() {
        String adminUsername = "idriss.elaissaouy@gmail.com";
        UserModel existingUser = userRepository.findByUsername(adminUsername);
        if (existingUser == null) {
            UserModel user = new UserModel();
            user.setRole(UserRole.ADMIN);
            user.setFirstname("idriss");
            user.setLastname("El Aissaouy");
            user.setAdresse("Casablanca station alWifaq");
            user.setEnabled(true);
            user.setTel("06565656");
            user.setTelfixe("0509808");
            user.setDateCreation(new Date(2024,1,1));
            user.setCarteNational("CN5454");
            user.setUsername(adminUsername);
            user.setPassword(passwordEncoder.encode("12345"));

            userRepository.save(user);
        }
    }


    @Override
    public void register(MultipartFile file, String firstname, String lastName,
                         String username, String pwd, String tel, String telfix,
                         Date date, String cartN, String adresse,HttpSession session) {

        UserModel user = new UserModel();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("not a valid file");
        }
        try {
            user.setImguser(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setFirstname(firstname);
        user.setLastname(lastName);
        user.setUsername(username);
        user.setTel(tel);
        user.setTelfixe(telfix);
        user.setDateCreation(date);
        user.setCarteNational(cartN);
        user.setAdresse(adresse);
        user.setRole(UserRole.USER);
        user.setPassword(passwordEncoder.encode(pwd));

        UserModel savedUser = userRepository.save(user);

        session.setAttribute("user1",savedUser);

        SessionData sessionData = new SessionData();
        sessionData.setUser(savedUser);
        sessionData.setSessionId(UUID.randomUUID().toString());
        sessionData.setCreatedAt(new Date(2024,06,6));
        sessionDataRepository.save(sessionData);
    }

    @Override
    public void updateUsers( String firstname, String lastName,
                         String username, String pwd, String tel, String telfix,
                         Date date, String cartN, String adresse) {

        UserModel user = new UserModel();
        user.setFirstname(firstname);
        user.setLastname(lastName);
        user.setUsername(username);
        user.setTel(tel);
        user.setTelfixe(telfix);
        user.setDateCreation(date);
        user.setCarteNational(cartN);
        user.setAdresse(adresse);
        user.setRole(UserRole.USER);
        user.setPassword(passwordEncoder.encode(pwd));

         userRepository.save(user);

    }



    @Override
    public int getTotalEmployeesCount() {

        List<UserModel> alluser = userRepository.findAll();
        return alluser.size();
    }
}
