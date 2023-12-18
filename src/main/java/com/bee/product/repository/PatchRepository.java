package com.bee.product.repository;

import com.bee.product.model.Patch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PatchRepository extends PagingAndSortingRepository<Patch, Long>, JpaSpecificationExecutor<Patch> {

    // Custom methods can be added here for advanced queries

}