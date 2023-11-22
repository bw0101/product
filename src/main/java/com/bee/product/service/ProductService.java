package com.bee.product.service;
import com.bee.product.model.Product;
import com.bee.product.model.ProductFilterDTO;
import com.bee.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository repo;
    private MessageSender sender;

    public List<Product> listAll(){
        List<Product> products =   repo.findAll();
        if (products.isEmpty()){
            return null;
        } else {
            sender.sendMessage("## action : list All Products");
            return products;
        }
    }

    public List<Product> findProductsByFilter(ProductFilterDTO filter) {
        List<Specification<Product>> specs = new ArrayList<>();

        if (filter.getNames() != null && !filter.getNames().isEmpty()) {
            specs.add(ProductSpecification.hasNameIn(filter.getNames()));
        }

        if (filter.getStatuses() != null && !filter.getStatuses().isEmpty()) {
            specs.add(ProductSpecification.hasStatusIn(filter.getStatuses()));
        }

        Specification<Product> finalSpec = specs.stream().reduce(Specification::and).orElse(null);
        return repo.findAll(finalSpec);
    }
}