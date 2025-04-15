package com.abhi.ecom_proj.Controller;

import com.abhi.ecom_proj.Model.Product;
import com.abhi.ecom_proj.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.stereotype.Service.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("products")
    public ResponseEntity<List<Product>> getproducts(){
       List<Product> obj=service.getAllProducts();
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
    @GetMapping("product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Product product =service.getProductById(id);
        if(product != null)
         return new ResponseEntity<>(product,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile){
        try {
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
    Product product =service.getProductById(productId);
    byte[] imageFile=product.getImageData();

return ResponseEntity.ok()
        .contentType(MediaType.valueOf(product.getImageType()))
        .body(imageFile);

    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id ,@RequestPart Product product,
                                              @RequestPart MultipartFile imageFile) {


        Product product1 = null;
        try {
            product1 = service.updateProduct(id, product, imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (product1 != null) {
                return new ResponseEntity<>("Product updated", HttpStatus.OK);
            } else
                return new ResponseEntity<>("product not updated", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
     Product product =service.getProductById(id);
     if(product!=null){
          service.deleteProduct(id);
         return new ResponseEntity<>("Product sucessfully deleted",HttpStatus.OK);
     }
     else{
              return new ResponseEntity<>("product not deleted",HttpStatus.BAD_REQUEST);
     }

    }

}

