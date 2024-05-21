package org.springtest1.springtest.service;






import org.springframework.web.multipart.MultipartFile;

import org.springtest1.springtest.dao.entities.Category;
import org.springtest1.springtest.dao.entities.Product;

import java.util.List;

public interface ProductManager {
  public List<Product> findByPrice(Integer md);
    public List<Product> getProductsByCategory(Category category) ;
    public Product addProduct(Product product);
//    public void updateProduct(Long productId, String name, String designation, Integer price, String imgP , Category category);
public void updateProduct(Long productId, String name, String designation, Integer price,Category category);
 //  public void updateProduct(Long productId, String name, String designation, double price);
    public void deleteProductById(Long id);
    public List<Product> getAllProducts();
    public Product getProductById(Long productId);
    public List<Product> searchProduits(String keyword);
    public void saveProductToDB(MultipartFile mainImage, List<MultipartFile> additionalImages, String name, String description, Integer price, Category category);

    void updateProduct1(Long productId,String description);
//   void  saveProductToDB(MultipartFile file, String name, String description, Integer price, Category category);
      //void  saveProductToDB(MultipartFile file, String name, String description, Integer price);

}