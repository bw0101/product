package com.bee.product.service;
import com.bee.product.model.Product;
import com.bee.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository repo;
    private MessageSender sender;
    public List<Product> listAllProducts(){
        List<Product> products = new ArrayList<>();
        products = repo.findAll();
        if (products.isEmpty()){
            return null;
        } else {
            sender.sendMessage("action : list All Products");
            return products;
        }
    }
}