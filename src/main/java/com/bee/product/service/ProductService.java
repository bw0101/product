package com.bee.product.service;
import com.bee.product.model.Product;
import com.bee.product.model.ProductFilterDTO;
import com.bee.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository repo;
    private MessageSender sender;


    public Page<Product> getProductsFiltered(Pageable pageable, Map<String, String> filters, String siteId) {
        // your logic here
        //TODO: implement filtering
        return repo.findAll(pageable);
    }

    public Page<Product> getProductsFilteredV2(
                Pageable pageable
                , Map<String, String> params
                ) {

      Specification<Product> spec = Specification.where(null);
        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"),"SRY"));

       /* for (Map.Entry<String, String> entry : filters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(key), value));
        }

      ;*/
        return repo.findAll(spec, pageable);
    }

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

        //assert finalSpec != null;
        //return Collections.emptyList();  // Return an empty list instead of null
        return repo.findAll(finalSpec);
    }

    public Page<Product> getProducts(Pageable pageable) {
        return repo.findAll(pageable);
    }
}