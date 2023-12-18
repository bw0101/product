package com.bee.product.service;

import com.bee.product.model.PaginationFilterDto;
import com.bee.product.model.Patch;
import com.bee.product.model.Product;
import com.bee.product.repository.PatchRepository;
import com.bee.product.repository.PatchSpecs;
import com.bee.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PagingFilterService {

    private PatchRepository patchRepository;

    public List<Patch> handlePaginationFilter(PaginationFilterDto filterDto) {
        // handle page and pageSize
        handlePaging(filterDto.getPage(), filterDto.getPageSize());

        // handle sorting
        handleSorting(filterDto.getSorting());

        // handle additional filters
        handleFilter(filterDto.getFilter());


        Specification<Patch> spec = Specification
                .where(PatchSpecs.statusContains(filterDto.getFilter().getStatus()))
                .and(PatchSpecs.typeContains(filterDto.getFilter().getType()));

        List<Patch> results = patchRepository.findAll(spec);

        return results;
    }

    private void handlePaging(int page, int pageSize) {
        // TODO: implement your paging handling logic here
    }

    private void handleSorting(PaginationFilterDto.Sorting sorting) {
        if (sorting != null) {
            // TODO: implement your sorting handling logic here
        }
    }

    private void handleFilter(PaginationFilterDto.Filter filter) {
        if (filter != null) {
            // TODO: implement your filter handling logic here
        }
    }
}