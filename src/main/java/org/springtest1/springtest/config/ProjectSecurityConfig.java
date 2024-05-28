package org.springtest1.springtest.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springtest1.springtest.service.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig implements WebMvcConfigurer {


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/all-products","/profiless","/product_details","/category","/AdminProfile","/profile","/customer","/employees_","/produit_details").authenticated()
                        .requestMatchers("/deleteProduct","/updateProducts/*","/registration","/admin-dashboard","/addCategory","/AddUser","/","/listProduit","/ajouterproduit","/updateUser/*","/ajouterproduits","/deleteProducts/*","/deleteUser/*","/deleteProduct/*","/deleteCategory/*","/static.cdn-cgi.scripts.7d0fa10a.cloudflare-static/**","/ajouterproduit","/deleteProduct/*","/updateProduct/*","/addProduct","/login",
                                "/assets/**","/assets2/**","/js2/**","/js/**","/src/**","/src2/**","/dom/**","/util/**","/src/**" ,"/node_modules/**","/scss/**","/wp-content/**"
                                ,"/wp-includes/**","/resources/**","/webjars/**",
                                "/email_templates/**",
                                "/resources/**",
                                "/img/**",
                                "/images/**",
                                "/contactform/**",
                                "/font/**",
                                "/plugins/**",
                                "/fonts/**",
                                "/ico/**",
                                "/css/**",
                                "/css1/**",
                                "/vendor/**",
                                "/img1/**",
                                "/font-awesome/**",
                                "/scss/**","/scss2/**",
                                "/js1/**",
                                "/skins/**",
                                "/assets/**",
                                "/cdn-cgi/**",
                                "/node_modules/**",
                                "/node_modules2/**",
                                "/wp-conten/**",
                                "/wp-includes/**"
                                ,"/registration1"
                        ).permitAll())
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/admin-dashboard", true)
                        .permitAll())
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

}

