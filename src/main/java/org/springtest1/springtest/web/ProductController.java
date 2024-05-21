package org.springtest1.springtest.web;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.ModelAndView;
import org.springtest1.springtest.dao.entities.Category;
import org.springtest1.springtest.dao.entities.Product;

import org.springtest1.springtest.dao.entities.ProductImage;
import org.springtest1.springtest.dao.entities.UserModel;
import org.springtest1.springtest.dao.repositories.CategoryRepository;
import org.springtest1.springtest.dao.repositories.UserRepository;
import org.springtest1.springtest.service.CategoryManager;
import org.springtest1.springtest.service.ProductManager;
import org.springtest1.springtest.service.ProductService;


import java.util.*;

@Controller
public class ProductController {
    @Autowired
    private ProductManager productManager;
    @Autowired
    private ProductService productService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private CategoryManager categoryManager;




    @GetMapping("/")
    public ModelAndView getAllProducts(Model model,
                             @RequestParam(name = "search", defaultValue = "") String keyword,
                             @RequestParam(name = "query", defaultValue = "") Integer md
                            , @RequestParam(name = "categoryId", defaultValue = "") Long categoryId,HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ModelAndView ret = new ModelAndView();
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user1");
        System.out.println("usernnnnnnnnnnnnnnnnnnn " + user.getUsername());
        if (user != null) {
            ret.addObject("user", user);
        }

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
//    } else {
//        products = productManager.getAllProducts();
//    }
//
//
//    Set<Integer> uniquePrices = new HashSet<>();
//    for (Product product : products) {
//        uniquePrices.add(product.getPrice());
//    }

        List<Category> categories = categoryManager.getAllCategories();

        Map<String, Long> productQuantitiesByCategory = productService.getProductQuantitiesByCategory();
        model.addAttribute("productQuantitiesByCategory", productQuantitiesByCategory);

        model.addAttribute("categories", categories);
        model.addAttribute("listProduits", products);
        model.addAttribute("uniquePrices", uniquePrices);
        ret.setViewName("listProduit");
        return ret;
    }
    @GetMapping("/produit_details")
    public ModelAndView getAllProducts1(Model model,
                                  @RequestParam(name = "search", defaultValue = "") String keyword,
                                  @RequestParam(name = "query", defaultValue = "") Integer md
                                 ,@RequestParam(name = "categoryId", defaultValue = "") Long categoryId,
                                  @RequestParam("produit") Long produit,
                                  HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ModelAndView ret = new ModelAndView();
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user1");
        System.out.println("usernnnnnnnnnnnnnnnnnnn " + user.getUsername());
        if (user != null) {
            ret.addObject("user", user);
        }

        Product result = productService.getProductById(produit);
        List<ProductImage> imageslist =  result.getImages();

        System.err.println(result.getId());
        model.addAttribute("produit", result);
        model.addAttribute("listimage", imageslist);
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
//    } else {
//        products = productManager.getAllProducts();
//    }
//
//
//    Set<Integer> uniquePrices = new HashSet<>();
//    for (Product product : products) {
//        uniquePrices.add(product.getPrice());
//    }

        List<Category> categories = categoryManager.getAllCategories();

        Map<String, Long> productQuantitiesByCategory = productService.getProductQuantitiesByCategory();
        model.addAttribute("productQuantitiesByCategory", productQuantitiesByCategory);

        model.addAttribute("categories", categories);
        model.addAttribute("listProduits", products);
        model.addAttribute("uniquePrices", uniquePrices);
        ret.setViewName("produit-details");
        return ret;
    }



//    @PostMapping("/ajouterproduit")
//    public String addProductToDB(@RequestParam("file") MultipartFile file,
//                                 @RequestParam("pname") String name,
//                                 @RequestParam("price") int price,
//                                 @RequestParam("desc") String desc,
//                                 @RequestParam("category") Long categoryId,
//                                 Model model) {
//        try {
//            Category category = categoryManager.getCategoryById(categoryId);
//            if (category == null) {
//                return "redirect:/listProduit?error=categoryNotFound";
//            }
//            productService.saveProductToDB(file, name, desc, price, category);
//        } catch (Exception e) {
//
//            return "redirect:/listProduit?error=internalServerError";
//        }
//        return "redirect:/all-products";
//    }
//    @PostMapping("/ajouterproduits")
//    public String addProductToDB1(@RequestParam("file") MultipartFile file, @RequestParam("pname") String name,
//                                  @RequestParam("price") int price,
//                                  @RequestParam("desc") String desc,
//                                  @RequestParam("category") Long categoryId,
//                                   Model model) {
//        try {
//            // Récupérer la catégorie sélectionnée à partir de son identifiant
//            Category category = categoryManager.getCategoryById(categoryId);
//            if (category == null) {
//                // Gérer le cas où la catégorie n'est pas trouvée
//                // Vous pouvez rediriger vers une page d'erreur ou afficher un message d'erreur dans le modèle
//                return "redirect:/listProduit?error=categoryNotFound";
//            }
//
//            productService.saveProductToDB(file, name, desc, price, category);
//        } catch (Exception e) {
//
//            return "redirect:/listProduit?error=internalServerError";
//        }
//
////        return "redirect:/listProduit";
//        return "redirect:/";
//    }
@PostMapping("/ajouterproduit")
public String addProductToDB(@RequestParam("file") MultipartFile mainImage,
                             @RequestParam("additionalImages") List<MultipartFile> additionalImages,
                             @RequestParam("pname") String name,
                             @RequestParam("price") int price,
                             @RequestParam("desc") String desc,
                             @RequestParam("category") Long categoryId,
                             Model model) {
    try {
        Category category = categoryManager.getCategoryById(categoryId);
        if (category == null) {
            return "redirect:/listProduit?error=categoryNotFound";
        }
        productService.saveProductToDB(mainImage, additionalImages, name, desc, price, category);
    } catch (Exception e) {
        return "redirect:/listProduit?error=internalServerError";
    }
    return "redirect:/";
}

    @PostMapping("/ajouterproduits")
    public String addProductToDB1(@RequestParam("file") MultipartFile mainImage,
                                 @RequestParam("additionalImages") List<MultipartFile> additionalImages,
                                 @RequestParam("pname") String name,
                                 @RequestParam("price") int price,
                                 @RequestParam("desc") String desc,
                                 @RequestParam("category") Long categoryId,
                                 Model model) {
        try {
            Category category = categoryManager.getCategoryById(categoryId);
            if (category == null) {
                return "redirect:/all-products";
            }
            productService.saveProductToDB(mainImage, additionalImages, name, desc, price, category);
        } catch (Exception e) {
            return "redirect:/all-products";
        }
        return "redirect:/all-products";
    }
//    public String addproductdb(@RequestParam("file") MultipartFile file,
//                               @RequestParam("pname") String name,
//                               @RequestParam("price") int price,
//                               @RequestParam("desc") String desc, Model model, Category category)
//    {
//
//
//
//        List<Category> categories = categoryManager.getAllCategories();
//
//        model.addAttribute("categories", categories);
//      //  productService.saveProductToDB(file, name, desc, price);
//       productService.saveProductToDB(file, name, desc, price,category);
//        return "redirect:/listProduit";
//    }


    @GetMapping("/deleteProduct/{id}")

    public String deleteProduct(Model model, @PathVariable("id") Long id) {
        productService.deleteProductById(id);

        return "redirect:/all-products";
    }

    @GetMapping("/deleteProducts/{id}")
    public String deleteProduct1(Model model, @PathVariable("id") Long id) {
        productService.deleteProductById(id);

        return "redirect:/";
    }


    @PostMapping("/updateProducts/{id}")
    public String updateProduct(@PathVariable("id") Long productId,@RequestParam("description") String description) {
        productManager.updateProduct1(productId,description);

        return "redirect:/all-products";
    }

@PostMapping("/updateProduct/{id}")
public String updateProduct(@PathVariable("id") Long productId,
                            @RequestParam("name") String name,
                            @RequestParam("description") String description,
                            @RequestParam("price") int price,
                            @RequestParam("category") Long categoryId,
                            Model model) {
    List<Category> categories = categoryManager.getAllCategories();
    model.addAttribute("categories", categories);


    Category category = categoryManager.getCategoryById(categoryId);
    if (category == null) {

        return "redirect:/listProduit?error=categoryNotFound";
    }


    productManager.updateProduct(productId, name, description, price, category);

    return "redirect:/";
}


//    @PostMapping("/updateProduct/{id}")
//    public String updateProduct(@PathVariable("id") Long productId,
//                                @RequestParam("name") String name,
//                                @RequestParam("description") String description,
//                                @RequestParam("price") int price,
//                                @RequestParam("imgP") String imgP,
//                              @RequestParam("category") Category category,
//                                Model model) {
//        List<Category> categories = categoryManager.getAllCategories();
//        model.addAttribute("categories", categories);
//
//        productManager.updateProduct(productId, name, description, price,imgP,category);
//
//        return "redirect:/listProduit";
//    }
}