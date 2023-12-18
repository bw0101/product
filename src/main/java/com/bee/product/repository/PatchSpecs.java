package com.bee.product.repository;

import com.bee.product.model.Patch;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

public final class PatchSpecs {

    public static Specification<Patch> customerContains(String keyword) {
        return (root, query, cb) -> cb.like(root.get("customer"), "%" + keyword + "%");
    }

    public static Specification<Patch> typeContains(String keyword) {
        return (root, query, cb) -> cb.like(root.get("type"), "%" + keyword + "%");
    }

    public static Specification<Patch> siteContains(String keyword) {
        return (root, query, cb) -> cb.like(root.get("site"), "%" + keyword + "%");
    }

    public static Specification<Patch> statusContains(String keyword) {
        return (root, query, cb) -> cb.like(root.get("status"), "%" + keyword + "%");
    }
}
