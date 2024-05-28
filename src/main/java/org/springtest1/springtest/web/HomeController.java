package org.springtest1.springtest.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springtest1.springtest.dao.entities.Category;
import org.springtest1.springtest.dao.entities.Product;
import org.springtest1.springtest.dao.entities.UserModel;
import org.springtest1.springtest.dao.repositories.ProductRepository;
import org.springtest1.springtest.dao.repositories.UserRepository;
import org.springtest1.springtest.service.*;

import java.util.*;

@Controller
public class HomeController {
    @Autowired
    private ProductManager productManager;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    CategoryService categoryService;
    @Autowired
    private CategoryManager categoryManager;

    @RequestMapping(value = "/addProduct", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView returnAddProduct() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("addProduct");
        return mv;
    }
    @RequestMapping(value = "/listProduct", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView returnListProduct() {
        ModelAndView mv = new ModelAndView();
        List<Product> products = productRepo.findAll();
        mv.setViewName("listProducts");
        mv.addObject("products", products);
        return mv;
    }
    //    @GetMapping("/admin-dashboard")
//    public ModelAndView adminDashboard(HttpServletRequest request) {
//        ModelAndView ret = new ModelAndView();
//        HttpSession session = request.getSession();
//        UserModel user = (UserModel) session.getAttribute("user1");
//        System.out.println("usernnnnnnnnnnn" + user.getUsername());
//        if (user != null) {
//            ret.addObject("user", user);
//        }
//        return ret;
//
//    }
    @GetMapping("/admin-dashboard")
    public ModelAndView adminDashboard(HttpServletRequest request, HttpServletResponse response,Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        ModelAndView ret = new ModelAndView();

        // Vérifiez si l'utilisateur est authentifié
        if (userDetails != null) {
            // Récupérez l'utilisateur à partir des détails de l'utilisateur
            UserModel user = userService.findUserByUsername(userDetails.getUsername());
            if (user != null) {
                ret.addObject("user", user);


                List<Product> products = productService.getProductsByUser(user);
                model.addAttribute("products", products);



                ret.setViewName("Admin-dashboard");

                int totalProductCount = productService.getTotalProductCount();
                model.addAttribute("totalProductCount", totalProductCount);

                int totalEmployees = userManager.getTotalEmployeesCount();
                model.addAttribute("totaluser", totalEmployees);

                int totalAmount = productService.getTotalAmount();
                model.addAttribute("totalAmount", totalAmount);

                List<Product> products1;
                List<UserModel> userModels;

                userModels = userManager.getAllUsers();
                products1 = productManager.getAllProducts();

                Set<Integer> uniquePrices = new HashSet<>();
                for (Product product : products) {
                    uniquePrices.add(product.getPrice());
                }
                Map<String, Long> productQuantitiesByCategory = productService.getProductQuantitiesByCategory();
                model.addAttribute("productQuantitiesByCategory", productQuantitiesByCategory);
                List<Category> categories = categoryManager.getAllCategories();
                model.addAttribute("listUsers", userModels);
                model.addAttribute("categories", categories);
                model.addAttribute("listProduits", products1);
                model.addAttribute("uniquePrices", uniquePrices);
            }}
        return ret;
    }

    @RequestMapping("produits-list")
    public String admindashboard1(Model model,
                                  @RequestParam(name = "search", defaultValue = "") String keyword,
                                  @RequestParam(name = "query", defaultValue = "") Integer md) {

        List<Product> products;
        if (!keyword.isEmpty()) {
            products = productManager.searchProduits(keyword);
        } else if (md != null) {
            products = productManager.findByPrice(md);
        } else {
            products = productManager.getAllProducts();
        }


        Set<Integer> uniquePrices = new HashSet<>();
        for (Product product : products) {
            uniquePrices.add(product.getPrice());
        }

        model.addAttribute("listProduits", products);
        model.addAttribute("uniquePrices", uniquePrices);
        return "produits-list";
    }
@Autowired
private UserService userService;
    @GetMapping("all-products")
    public ModelAndView admindashboard2(Model model, @RequestParam(name = "search", defaultValue = "") String keyword,
                                  @RequestParam(name = "query", defaultValue = "") Integer md,
                                  @RequestParam(name = "categoryId", defaultValue = "") Long categoryId,
                                        @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        ModelAndView ret = new ModelAndView();

        if (userDetails != null) {
            // Récupérez l'utilisateur à partir des détails de l'utilisateur
            UserModel user = userService.findUserByUsername(userDetails.getUsername());
            if (user != null) {
                ret.addObject("user", user);



                        // Récupérer les produits de l'utilisateur actuellement connecté
                        List<Product> products1 = productService.getProductsByUser(user);
                        model.addAttribute("products", products1);




                List<Product> products;
                if (!keyword.isEmpty()) {
                    products = productManager.searchProduits(keyword);
                } else if (md != null) {
                    products = productManager.findByPrice(md);
                } else if (categoryId != null && categoryId != 0) {
                    Category category = categoryManager.getCategoryById(categoryId);
                    if (category != null) {
                        products = productManager.getProductsByCategory(category);
                    } else {

                        products = new ArrayList<>();
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
            }}
   ret.setViewName("gestion-produits");
        return ret;
    }


    @RequestMapping("/category")
    public String category() {

        return "/category";
    }

    @GetMapping("/category")
    public ModelAndView category1(Model model, HttpServletRequest request, HttpServletResponse response,  @AuthenticationPrincipal UserDetails userDetails)
            throws Exception {
        ModelAndView ret = new ModelAndView();
        if (userDetails != null) {
            // Récupérez l'utilisateur à partir des détails de l'utilisateur
            UserModel user = userService.findUserByUsername(userDetails.getUsername());
            if (user != null) {
                ret.addObject("user", user);
                List<Category> categories;
                categories = categoryManager.getAllCategories();
                model.addAttribute("categories", categories);

            }
        }
        ret.setViewName("category");
        return ret;
    }
    @PostMapping("/addCategory")
    public String addCategory(@RequestParam("Cname") String name) {
        Category category = new Category();
        category.setName(name);
        categoryService.addCategory(category);

        return "redirect:/category";
    }

    //    @GetMapping("/deleteCategory/{id}")
//    public String deleteCategory(Model model, @PathVariable("id") Long id) {
//        categoryService.deleteCategoryById(id);
//        return "redirect:/category";
//    }
    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryAndProducts(id);
        return "redirect:/category";
    }

    @Autowired
    UserManager userManager;

    @GetMapping("/AdminProfile")
    public ModelAndView ProfileAdmin(Model model,
                                     @RequestParam(name = "search", defaultValue = "") String keyword,
                                     @RequestParam(name = "query", defaultValue = "") Integer md
            , @RequestParam(name = "categoryId", defaultValue = "") Long categoryId,  @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        ModelAndView ret = new ModelAndView();

        // Vérifiez si l'utilisateur est authentifié
        if (userDetails != null) {
            // Récupérez l'utilisateur à partir des détails de l'utilisateur
            UserModel user = userService.findUserByUsername(userDetails.getUsername());
            if (user != null) {
                ret.addObject("user", user);

                List<Product> products1 = productService.getProductsByUser(user);
                model.addAttribute("products", products1);


                List<Product> products;
                if (!keyword.isEmpty()) {
                    products = productManager.searchProduits(keyword);
                } else if (md != null) {
                    products = productManager.findByPrice(md);
                } else if (categoryId != null && categoryId != 0) {
                    Category category = categoryManager.getCategoryById(categoryId);
                    if (category != null) {
                        products = productManager.getProductsByCategory(category);
                    } else {
                        products = new ArrayList<>();
                    }
                } else {
                    products = productManager.getAllProducts();
                }

                Set<Integer> uniquePrices = new HashSet<>();
                for (Product product : products) {
                    uniquePrices.add(product.getPrice());
                }
                List<Category> categories = categoryManager.getAllCategories();

                Map<String, Long> productQuantitiesByCategory = productService.getProductQuantitiesByCategory();
                model.addAttribute("productQuantitiesByCategory", productQuantitiesByCategory);

                model.addAttribute("categories", categories);
                model.addAttribute("listProduits", products);
                model.addAttribute("uniquePrices", uniquePrices);
            }}
        ret.setViewName("profileAdmin");
        return ret;
    }

    @PostMapping("/{userId}/activate")
    public String activateUser(@PathVariable Long userId) {
        userService.activateUser(userId);
        return "redirect:/users";
    }

    @PostMapping("/{userId}/deactivate")
    public String deactivateUser(@PathVariable Long userId) {
        userService.deactivateUser(userId);
        return "redirect:/users";
    }

}
