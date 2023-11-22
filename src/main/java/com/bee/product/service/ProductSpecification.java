package com.bee.product.service;

import com.bee.product.model.Product;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

import java.util.List;

public class ProductSpecification {

    public static Specification<Product> hasNameIn(List<String> names) {
        return (root, query, criteriaBuilder) ->
                root.get("name").in(names);
    }

    public static Specification<Product> hasStatusIn(List<String> statuses) {
        return (root, query, criteriaBuilder) ->
                root.get("status").in(statuses);
    }
}
