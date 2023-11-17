package com.bee.product.controller;
import com.bee.product.model.Product;
import com.bee.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
public class ProductRestController {
    private ProductService service;
    @GetMapping(value = "/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return  new ResponseEntity<>(service.listAllProducts(), HttpStatus.OK);
    }
}
