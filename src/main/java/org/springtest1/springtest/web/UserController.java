package org.springtest1.springtest.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springtest1.springtest.dao.entities.Category;
import org.springtest1.springtest.dao.entities.Product;
import org.springtest1.springtest.dao.entities.SessionData;
import org.springtest1.springtest.dao.entities.UserModel;
import org.springtest1.springtest.dao.repositories.CategoryRepository;
import org.springtest1.springtest.dao.repositories.SessionDataRepository;
import org.springtest1.springtest.dao.repositories.UserRepository;
import org.springtest1.springtest.service.*;

import java.sql.Date;
import java.util.*;
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("login")
    public String login(){
        return "login";
    }



//        HttpSession session = request.getSession(true);
//        if (session != null) {
//            UserModel user = (UserModel) session.getAttribute("user1");
//            if (user != null) {
//                ret.addObject("user", user);
//            }
//        }


    @Autowired
    private HttpSession session;
//    @RequestMapping(value = "/initialize", method = RequestMethod.GET)
//    public String initialize() {
//        UserModel user = userService.initializeAdminUser();
//        if (user != null) {
//            session.setAttribute("user1", user);
//        }
//        return "login";
//    }


//    @GetMapping("/registration1")
//    public String getRegistrationPage(Model model) {
//        model.addAttribute("user", new UserModel());
//        return "registration_page";
//    }
    @GetMapping("/registration1")
    public String getRegistrationPage(Model model,HttpSession session) {
        UserModel user=new UserModel();
        model.addAttribute("user",user);
        session.setAttribute("user1", user);
        return "registra";
    }

//    @PostMapping("/registration1")
//    public String registerUser(@RequestParam("file") MultipartFile file,
//                               @RequestParam("username") String username,
//                               @RequestParam("DateCreation") Date date,
//                               @RequestParam("password") String pwd,
//                               @RequestParam("firstname") String firstname,
//                               @RequestParam("lastname") String lastname,
//                               @RequestParam("tel") String tel,
//                               @RequestParam("cartNational") String cartN,
//                               @RequestParam("adresse") String adresse,
//                               @RequestParam("telfix") String telfix,
//                               HttpSession session,Model model) {
//        UserModel user=new UserModel();
//        model.addAttribute("user",user);
//        session.setAttribute("user1", user);
//        userService.register( file, firstname,lastname,  username , pwd,tel,telfix,date,cartN,adresse);
//        return "redirect:/login?success";
//    }
//@PostMapping("/registration1")
//public String registerUser(@RequestParam("file") MultipartFile file,
//                           @RequestParam("username") String username,
//                           @RequestParam("DateCreation") Date date,
//                           @RequestParam("password") String password,
//                           @RequestParam("firstname") String firstname,
//                           @RequestParam("lastname") String lastname,
//                           @RequestParam("tel") String tel,
//                           @RequestParam("cartNational") String cartNational,
//                           @RequestParam("adresse") String adresse,
//                           @RequestParam("telfix") String telfix,
//                           HttpSession session
//                         ) {
//
//
//    UserModel user = new UserModel();
//    user.setUsername(username);
//    user.setFirstname(firstname);
//    user.setLastname(lastname);
//    user.setPassword(password);
//    user.setTel(tel);
//    user.setDateCreation(date);
//    user.setCarteNational(cartNational);
//    user.setAdresse(adresse);
//    user.setTelfixe(telfix);
//
//
//    session.setAttribute("user1", user);
//
//
//    userService.register(file, firstname, lastname, username, password, tel, telfix, date, cartNational, adresse);
//
//
//    return "redirect:/login";
//}
@PostMapping("/registration1")
public String registerUser(@RequestParam("file") MultipartFile file,
                           @RequestParam("username") String username,
                           @RequestParam("DateCreation") Date date,
                           @RequestParam("password") String password,
                           @RequestParam("firstname") String firstname,
                           @RequestParam("lastname") String lastname,
                           @RequestParam("tel") String tel,
                           @RequestParam("cartNational") String cartNational,
                           @RequestParam("adresse") String adresse,
                           @RequestParam("telfix") String telfix,
                           HttpSession session) {

    userService.register(file, firstname, lastname, username, password, tel, telfix, date, cartNational, adresse,session);
    return "redirect:/login";
}


    @Autowired
    private ProductManager productManager;
    @Autowired
    private ProductService productService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private CategoryManager categoryManager;
    @Autowired
    private UserManager userManager;

    @GetMapping("/profiless")
    public String getprofile(Model model, @RequestParam(name = "search", defaultValue = "") String keyword,
                             @RequestParam(name = "query", defaultValue = "") Integer md, @RequestParam(name = "categoryId", defaultValue = "") Long categoryId) {

        List<Product> products;
        if (!keyword.isEmpty()) {
            products = productManager.searchProduits(keyword);
        } else if (md != null) {
            products = productManager.findByPrice(md);
        } else if (categoryId != null && categoryId != 0) { // Check if categoryId is provided
            Category category = categoryManager.getCategoryById(categoryId);
            if (category != null) {
                products = productManager.getProductsByCategory(category);
            } else {
                // Handle category not found
                products = new ArrayList<>(); // Empty list
            }
        } else {
            products = productManager.getAllProducts();
        }

        Set<Integer> uniquePrices = new HashSet<>();
        for (Product product : products) {
            uniquePrices.add(product.getPrice());
        }


        Map<String, Long> productQuantitiesByCategory = productService.getProductQuantitiesByCategory();


        model.addAttribute("productQuantitiesByCategory", productQuantitiesByCategory);


        int totalProductCount = productService.getTotalProductCount();
        model.addAttribute("totalProductCount", totalProductCount);
        int totalAmount = productService.getTotalAmount();
        model.addAttribute("totalAmount", totalAmount);
        List<Category> categories = categoryManager.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("listProduits", products);
        model.addAttribute("uniquePrices", uniquePrices);
        return "profiless";
    }
    @GetMapping("/profiles")
    public String getprofiles(Model model, @RequestParam(name = "search", defaultValue = "") String keyword,
                             @RequestParam(name = "query", defaultValue = "") Integer md, @RequestParam(name = "categoryId", defaultValue = "") Long categoryId) {

        List<Product> products;
        if (!keyword.isEmpty()) {
            products = productManager.searchProduits(keyword);
        } else if (md != null) {
            products = productManager.findByPrice(md);
        } else if (categoryId != null && categoryId != 0) { // Check if categoryId is provided
            Category category = categoryManager.getCategoryById(categoryId);
            if (category != null) {
                products = productManager.getProductsByCategory(category);
            } else {
                // Handle category not found
                products = new ArrayList<>(); // Empty list
            }
        } else {
            products = productManager.getAllProducts();
        }

        Set<Integer> uniquePrices = new HashSet<>();
        for (Product product : products) {
            uniquePrices.add(product.getPrice());
        }


        Map<String, Long> productQuantitiesByCategory = productService.getProductQuantitiesByCategory();


        model.addAttribute("productQuantitiesByCategory", productQuantitiesByCategory);


        int totalProductCount = productService.getTotalProductCount();
        model.addAttribute("totalProductCount", totalProductCount);
        int totalAmount = productService.getTotalAmount();
        model.addAttribute("totalAmount", totalAmount);
        List<Category> categories = categoryManager.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("listProduits", products);
        model.addAttribute("uniquePrices", uniquePrices);
        return "profiles";
    }

    @GetMapping("/employees_")
    public  ModelAndView getAllUser(Model model,HttpServletRequest request,
                                    @RequestParam(name = "searchEm", defaultValue = "") String keyword1,
                                    @RequestParam(name = "searchId", defaultValue = "") String md) {
        List<UserModel> userModels1=new ArrayList<>();
        if (!keyword1.isEmpty()) {
            userModels1 = userManager.searchEmplyee(keyword1);
        } else if (!md.isEmpty()) {
            UserModel user = userManager.findById(md);
            userModels1 = Collections.singletonList(user);
        } else {
            userModels1 = userManager.getAllUsers();
        }



        model.addAttribute("listUser", userModels1);
//        model.addAttribute("listUser1", userModels1);

        ModelAndView ret = new ModelAndView();
            HttpSession session = request.getSession();
            UserModel user = (UserModel) session.getAttribute("user1");
            if (user != null) {
                ret.addObject("user", user);
            }
        ret.setViewName("employees");
            return ret;
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser( @PathVariable("id") Long id) {
        userManager.deleteUserById(id);

        return "redirect:/employees_";
    }
    @GetMapping("customer")
    public String getCustemr() {

        return "customer";
    }
    @PostMapping("/AddUser")
    public String AddUser(@RequestParam("file") MultipartFile file,
                               @RequestParam("username") String username,
                               @RequestParam("DateCreation") Date date,
                               @RequestParam("password") String pwd,
                               @RequestParam("firstname") String firstname,
                               @RequestParam("lastname") String lastname,
                               @RequestParam("tel") String tel,
                               @RequestParam("cartNational") String cartN,
                               @RequestParam("adresse") String adresse,
                               @RequestParam("telfix") String telfix,
                          HttpSession session

     ) {

        userService.register(file, firstname, lastname, username, pwd, tel, telfix, date, cartN, adresse,session);

        return "redirect:/employees_";
    }

    @PostMapping("/updateUser/{id}")
    public String updateProduct(
                                @PathVariable("username1") String username,
                                @PathVariable("DateCreation1") Date date,
                                @PathVariable("password1") String pwd,
                                @PathVariable("firstname1") String firstname,
                                @PathVariable("lastname1") String lastname,
                                @PathVariable("tel1") String tel,
                                @PathVariable("cartNational1") String cartN,
                                @PathVariable("adresse1") String adresse,
                                @PathVariable("telfix1") String telfix
                               ) {


        userService.updateUsers( firstname, lastname, username, pwd, tel, telfix, date, cartN, adresse);



        return "redirect:/employees_";
    }
    }
