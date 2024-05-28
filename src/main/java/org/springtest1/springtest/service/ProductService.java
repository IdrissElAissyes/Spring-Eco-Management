package org.springtest1.springtest.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import org.springtest1.springtest.dao.entities.Category;
import org.springtest1.springtest.dao.entities.Product;
import org.springtest1.springtest.dao.entities.ProductImage;
import org.springtest1.springtest.dao.entities.UserModel;
import org.springtest1.springtest.dao.repositories.ProductRepository;

import java.io.IOException;
import java.util.*;

@Service
public class ProductService implements ProductManager {


    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<Product> searchProduits(String keyword) {
        return productRepository.findByNameContains(keyword);
    }



    @Override
    public List<Product> findByPrice(Integer mt) {
        return productRepository.findByPrice(mt);
    }

    @Override
    public Product addProduct(Product product) {
        if (product.getPrice() > 0) {
            return productRepository.save(product);
        } else {
            System.out.println("the price is not valid");
            return null;
        }
    }



    @Override
    public void deleteProductById(Long id) {

        productRepository.deleteById(id);

    }
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
@Override
public Product getProductById(Long productId) {
    Optional<Product> p = productRepository.findById(productId);
    if (p.isPresent()) {
        return p.get();
    } else {
        throw new RuntimeException("Product not found with id: " + productId);
    }
}
    @Override
    public void updateProduct1(Long Id,String description) {
        Product product = productRepository.findById(Id).orElse(null);
        if (product != null) {
        product.setId(Id);
        product.setDescription(description);
        productRepository.save(product);
        }
    }
@Override
public void updateProduct(Long productId, String name, String designation, Integer price,Category category) {

    Product product = productRepository.findById(productId).orElse(null);
    if (product != null) {

        product.setId(productId);
        product.setName(name);
        product.setDescription(designation);
        product.setPrice(price);
      product.setCategory(category);
        productRepository.save(product);
    }
}

//@Override
//public void saveProductToDB(MultipartFile mainImage, List<MultipartFile> additionalImages, String name, String description, Integer price, Category category) {
//    Product product = new Product();
//
//    // Traitement de la principale image du produit
//    String mainImageFileName = StringUtils.cleanPath(mainImage.getOriginalFilename());
//    if (mainImageFileName.contains("..")) {
//        System.out.println("Not a valid file");
//    }
//    try {
//        product.setImgP(Base64.getEncoder().encodeToString(mainImage.getBytes()));
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//
//    // Traitement des images supplémentaires
//    List<ProductImage> productImages = new ArrayList<>();
//    for (MultipartFile additionalImage : additionalImages) {
//        String additionalImageFileName = StringUtils.cleanPath(additionalImage.getOriginalFilename());
//        if (additionalImageFileName.contains("..")) {
//            System.out.println("Not a valid file");
//        }
//        try {
//            ProductImage image = new ProductImage();
//            image.setImageData(Base64.getEncoder().encodeToString(additionalImage.getBytes()));
//            image.setProduct(product); // Assurez-vous d'associer l'image au produit
//            productImages.add(image);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    product.setDescription(description);
//    product.setName(name);
//    product.setPrice(price);
//    product.setCategory(category);
//    product.setImages(productImages); // Associez les images supplémentaires au produit
//
//    productRepository.save(product);
//}
//
//
//
//    public List<ProductImage> getProductImages(Long productId) {
//        Optional<Product> p = productRepository.findById(productId);
//        if (p.isPresent()) {
//            Product product = p.get();
//            return product.getImages();
//        }
//        throw new RuntimeException("Product not found with id: " + productId);
//    }
@Override
public List<Product> getProductsByUser(UserModel user) {
    // Implémentez la logique pour récupérer les produits associés à un utilisateur spécifique
    return productRepository.findByUser(user);
}
    @Override
public void saveProductToDB(MultipartFile mainImage, List<MultipartFile> additionalImages, String name, String description, Integer price, Category category, UserModel user) {
    Product product = new Product();

    // Traitement de la principale image du produit
    String mainImageFileName = StringUtils.cleanPath(mainImage.getOriginalFilename());
    if (mainImageFileName.contains("..")) {
        System.out.println("Not a valid file");
    }
    try {
        product.setImgP(Base64.getEncoder().encodeToString(mainImage.getBytes()));
    } catch (IOException e) {
        e.printStackTrace();
    }


    // Traitement des images supplémentaires
    List<ProductImage> productImages = new ArrayList<>();
    for (MultipartFile additionalImage : additionalImages) {
        String additionalImageFileName = StringUtils.cleanPath(additionalImage.getOriginalFilename());
        if (additionalImageFileName.contains("..")) {
            System.out.println("Not a valid file");
        }
        try {
            ProductImage image = new ProductImage();
            image.setImageData(Base64.getEncoder().encodeToString(additionalImage.getBytes()));
            image.setProduct(product); // Assurez-vous d'associer l'image au produit
            productImages.add(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    product.setDescription(description);
    product.setName(name);
    product.setPrice(price);
    product.setCategory(category);
    product.setUser(user); // Associez le produit à l'utilisateur
    product.setImages(productImages); // Associez les images supplémentaires au produit

    productRepository.save(product);
}


    @Override
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }
    public int getTotalProductCount() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts.size();
    }
    public int getTotalAmount() {
        List<Product> allProducts = productRepository.findAll();
        int totalAmount = 0;
        for (Product product : allProducts) {
            totalAmount += product.getPrice();
        }
        return totalAmount;
    }
    public Map<String, Long> getProductQuantitiesByCategory() {
        Map<String, Long> quantitiesByCategory = new HashMap<>();

        List<Object[]> result = productRepository.countProductsByCategory();

        for (Object[] row : result) {
            String categoryName = (String) row[0];
            Long quantity = (Long) row[1];
            quantitiesByCategory.put(categoryName, quantity);
        }

        return quantitiesByCategory;
    }


@Transactional
public void deleteProductsByCategoryId(Long categoryId) {
    productRepository.deleteByCategoryId(categoryId);
}




}