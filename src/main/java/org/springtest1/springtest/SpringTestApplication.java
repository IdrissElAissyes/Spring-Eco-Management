package org.springtest1.springtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.multipart.MultipartFile;

import org.springtest1.springtest.dao.entities.Product;
import org.springtest1.springtest.service.ProductManager;

@SpringBootApplication

public class SpringTestApplication implements CommandLineRunner {
    @Autowired
    ProductManager productManager;

    public static void main(String[] args) {
        SpringApplication.run(SpringTestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

      //  Category category=new Category(null,"MC");
      //  Category category1=new Category(null,"VV");
//        categoryManager.addCategory(category);
//        categoryManager.addCategory(category1);
//       System.out.println(categoryManager.getAllCategories());
//        System.out.println(category.getName());
//Product product1 = new Product(null, "msi","dirss", 122,null);
//productManager.saveProductToDB("gggd","fefef",55);
//
//   Product product2 = new Product(null, "dell","feff",1235,null);
//        Product product5 = new Product(null, "hp","ff", 1823.0);
//        Product product3 = new Product(null, "hgp","fgf", 18263.0);
//        Product product4 = new Product(null, "IDRISSff","fffgf", 18263.0);

  // productManager.saveProductToDB(,"ss","ff",55,category1);
   //  productManager.addProduct(product2);
  //   productManager.saveProductToDB(null,"ddd","fff",444);
//        productManager.addProduct(product3);
//        productManager.addProduct(product4);
//        productManager.addProduct(product5);
//        productManager.deleteProductById(4L);

//        productManager.updateProduct(1L,"aalllla","eeeee",4555);
      //  System.out.println(productManager.getAllProducts());

    }
}
