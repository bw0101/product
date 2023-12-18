package com.bee.product.controller;

import com.bee.product.model.PaginationFilterDto;
import com.bee.product.service.PagingFilterService;
import com.bee.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1", produces="application/json")
public class PatchRestController {

    private ProductService service;

    private PagingFilterService pagingFilterService;

    /**
     * e.g.:
     * http://localhost:8088/api/v1/endpoint?
     * page=1&pageSize=10&sorting[order]=desc&sorting[attribute]=type&filters[site]=asdasd&filters[type]=ESXi&filters[version]=adsa&siteId=123
     *
     * @param filterDto
     * @param bindingResult
     * @return
     */
    @GetMapping("/endpoint")
            public ResponseEntity<?> getList(@Valid PaginationFilterDto filterDto, BindingResult bindingResult){
                if (bindingResult.hasErrors()) {
            // TODO: Handle invalid request parameters
            System.out.println("bindingResult = " + bindingResult);
            return ResponseEntity.badRequest().build();
        }

        System.out.println("filterDto = " + filterDto);
        // TODO: Use filterDto to process the request and produce the result

        int pageNo = filterDto.getPage();
        int pageSize = filterDto.getPageSize();

        if(pageNo < 0 || pageSize <= 0)
            throw new IllegalArgumentException("Invalid page size or number.");
        //TODO: add sorting
        Pageable paging = PageRequest.of(pageNo, pageSize);
       // return new ResponseEntity<>(service.getProducts(paging), HttpStatus.OK);
       return new ResponseEntity<>(pagingFilterService.handlePaginationFilter(filterDto), HttpStatus.OK);

        //return ResponseEntity.ok().build();
    }


    /**
     * When considering best practices for implementing pagination, filtering, and sorting in a REST GET request, we primarily care about efficiency, readability, and usability:
     * Usage of Query Parameters: Query parameters are generally used to handle sorting, filtering and pagination in a RESTful API. This keeps the URL structure clean and clear:
     * For pagination, parameters such as page and pageSize are used. e.g: /items?page=1&pageSize=20.
     * For sorting, parameters such as sort and sortDirection are used. e.g: /items?sort=price&sortDirection=asc.
     * For filtering, the field name can be used as a parameter. e.g: /items?color=blue.
     * Pagination:
     * Always provide defaults for page and pageSize values if they are not supplied in the request, so that the server doesn't get overwhelmed with very large result sets.
     * Return total counts or last page number, so that the client does not need to guess when to stop requesting more pages.
     * Sorting:
     * To prevent the usage of invalid column names, use a whitelist of sortable fields.
     * Filtering:
     * Similar to sorting, make sure to whitelist the filter-able fields.
     * Consider supporting advanced filtering operations like greater than, less than, starts with, etc.
     * Performance:
     * Use database features like LIMIT and OFFSET for pagination, to avoid retrieving more records from the database than necessary.
     * Index columns that are frequently used for sorting and filtering to speed up those operations.
     * API Documentation: Make sure to document the filters, sort, and pagination functionality of the API, so clients know how to use them.
     * Link Headers: Consider using link headers to share links to next and previous pages.
     * Error Handling: Implement proper error handling for scenarios like invalid sort fields, non-existent pages, etc.
     * @param page
     * @param pageSize
     * @param order
     * @return
     */

    @GetMapping("/endpoint2")
    public ResponseEntity<?> getList2(
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("sorting[order]") String order
            /*,@RequestParam("sorting[attribute]") String attribute
            ,@RequestParam("filters[site]") String site
            ,@RequestParam("filters[type]") String type
            ,@RequestParam("filters[version]") String version
            ,@RequestParam("siteId") String siteId*/
            ) {

        PaginationFilterDto paginationFilterDto = new PaginationFilterDto();
        paginationFilterDto.setPage(page);
        paginationFilterDto.setPageSize(pageSize);
        PaginationFilterDto.Sorting sorting = new PaginationFilterDto.Sorting();
        sorting.setOrder(order);
        paginationFilterDto.setSorting(sorting);


        Object result = pagingFilterService.handlePaginationFilter(paginationFilterDto);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/endpoint3")
    public ResponseEntity<?> getList3(
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize
            ,@RequestParam("sort") String sortAttribute
            ,@RequestParam("sortDirection]") String sortDirection
    ) {


        PaginationFilterDto paginationFilterDto = new PaginationFilterDto();
        paginationFilterDto.setPage(page);
        paginationFilterDto.setPageSize(pageSize);
        PaginationFilterDto.Sorting sorting = new PaginationFilterDto.Sorting();
        sorting.setAttribute(sortAttribute);
        sorting.setOrder(sortDirection);
        paginationFilterDto.setSorting(sorting);

        System.out.println("3 : paginationFilterDto = " + paginationFilterDto);

        Object result = pagingFilterService.handlePaginationFilter(paginationFilterDto);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/endpoint4")
    public ResponseEntity<?> getList4(
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize
            ,@RequestParam("sort") String sortAttribute
            ,@RequestParam("sortDirection") String sortDirection
            ,@RequestParam("site") String site
            ,@RequestParam("type") String type
            ,@RequestParam("version") String version
            ,@RequestParam("siteId") String siteId
    ) {


        PaginationFilterDto paginationFilterDto = new PaginationFilterDto();
        paginationFilterDto.setPage(page);
        paginationFilterDto.setPageSize(pageSize);

        PaginationFilterDto.Sorting sorting = new PaginationFilterDto.Sorting();
        sorting.setAttribute(sortAttribute);
        sorting.setOrder(sortDirection);

        paginationFilterDto.setSorting(sorting);

        PaginationFilterDto.Filter filter = new PaginationFilterDto.Filter();
        filter.setType(type);
        filter.setVersion(version);
        filter.setSite(site);
        paginationFilterDto.setFilter(filter);

        paginationFilterDto.setSiteId(siteId);

        Object result = pagingFilterService.handlePaginationFilter(paginationFilterDto);
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }


}