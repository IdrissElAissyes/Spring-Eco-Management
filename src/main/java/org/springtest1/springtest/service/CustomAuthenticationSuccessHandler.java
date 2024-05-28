//package org.springtest1.springtest.service;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import org.springtest1.springtest.dao.entities.UserModel;
//import org.springtest1.springtest.dao.repositories.UserRepository;
//
//import java.io.IOException;
//
//@Component
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        HttpSession session = request.getSession();
//        String username = authentication.getName();
//        UserModel user = userRepository.findByUsername(username);
//
//        if (user != null) {
//            session.setAttribute("user1", user);
//        }
//
//        response.sendRedirect("/admin-dashboard");
//    }
//}
