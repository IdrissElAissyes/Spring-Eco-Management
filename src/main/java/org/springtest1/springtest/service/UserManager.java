package org.springtest1.springtest.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;
import org.springtest1.springtest.dao.entities.Product;
import org.springtest1.springtest.dao.entities.UserModel;

import java.sql.Date;
import java.util.List;

public interface UserManager {
    public void deleteUserById(Long id);
    public void register(MultipartFile file, String firstname, String lastName, String username , String pwd, String tel, String telfix, Date date, String cartN, String adresse,HttpSession session );
    public List<UserModel> getAllUsers();
    public UserModel getUserById(Long id);
    public UserModel findById(String id);
    public int getTotalEmployeesCount();
    public List<UserModel> searchEmplyee(String keyword);
    public void updateUser(Long userId,String firstname,String lastName, String username ,String pwd,String tel,String telfix,Date date,String cartN,String adresse);
    public void updateUsers( String firstname, String lastName,
                             String username, String pwd, String tel, String telfix,
                             Date date, String cartN, String adresse);

}
