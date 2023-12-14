package com.bee.product.controller;
import com.bee.product.model.LargeRequestDTO;
import com.bee.product.model.Product;
import com.bee.product.model.ProductFilterDTO;
import com.bee.product.service.FileSystemService;
import com.bee.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/products", produces="application/json")
public class ProductRestController {

    private ProductService service;

    private FileSystemService fileSystemService;

    /**
     *
     * e.g.:

     http://localhost:8088/api/v1/products/endpoint?
     page=1

     &pageSize=10
     &sorting[order]=desc
     &sorting[attribute]=type
     &filters[site]=asdasd
     &filters[type]=ESXi
     &filters[version]=adsa
     &siteId=123

             /endpoint?page=1&pageSize=10&sorting[order]=desc&sorting[attribute]=type&filters[site]=asdasd&filters[type]=ESXi&filters[version]=adsa&siteId=123

     * @param page
     * @param pageSize
     * @param sorting
     * @param filters
     * @param siteId
     * @return
     */
    @GetMapping("/endpoint")
    public Page<Product> getProductsWithFilters(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam Map<String, String> sorting,
            @RequestParam Map<String, String> filters,
            @RequestParam String siteId)
    {
        Pageable pageable = PageRequest.of(page, pageSize,
                Sort.by(sorting.get("order").equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sorting.get("attribute")));
        return service.getProductsFilteredV2(pageable,
                filters,
                siteId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts(){
        return  new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }




    @GetMapping
    public Page<Product> getProducts(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize ) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return service.getProducts(paging);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Product>> getProductsPaginated(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {

        if(pageNo < 0 || pageSize <= 0)
            throw new IllegalArgumentException("Invalid page size or number.");
        //TODO: add sorting
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return new ResponseEntity<>(service.getProducts(paging), HttpStatus.OK);
    }

    @RequestMapping("/paginated2")
    public Page<Product> getProducts(
            @RequestParam int page,
            @RequestParam int size) {
        return service.getProducts(PageRequest.of(page, size));
    }

    /**
     * Optimized version of paginated endpoint
     * e.g.:
     * http://localhost:8080/api/v1/products/paginatedOptimized?page=0&size=10&sort=id,desc
     * @param pageable
     * @return
     */
    @RequestMapping("/paginatedOptimized")
    public Page<Product> getProductsOptimized(
            @PageableDefault( page = 0, value = 5) Pageable pageable) {
        return service.getProducts(pageable);
    }




    @GetMapping("/files")
    public List<String> getFilesInDirectory(@RequestParam String path) {
        return fileSystemService.listAllFiles(path);
    }

    /**
     *  Post request with json example:
     *  {
     *     "names": ["SRY", "PCZ"],
     *     "statuses": ["Pending", "pending", "waiting"]
     * }
     * @param filter
     * @return
     */
    @PostMapping("/filter")
    public ResponseEntity<List<Product>> getProducts(@RequestBody ProductFilterDTO filter) {
        List<Product> response = service.findProductsByFilter(filter);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/largeRequest")
    public ResponseEntity<?> handleLargeRequest(@Valid @RequestBody LargeRequestDTO request) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Request received successfully");
        response.put("data", request);
        return ResponseEntity.ok(response);
    }

    /**
     * Handle validation errors
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            MethodArgumentTypeMismatchException.class,
            PropertyReferenceException.class
    })
    public ResponseEntity<String> handleException(IllegalArgumentException e) {
        return new ResponseEntity<>("Invalid Request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /* @ControllerAdvice
    public class GlobalControllerExceptionHandler {

        @ExceptionHandler(
                    {   IllegalArgumentException.class,
                        MethodArgumentTypeMismatchException.class,
                        PropertyReferenceException.class })
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ResponseEntity<String> handleBadRequest(RuntimeException ex) {
            return new ResponseEntity<>("Invalid Request: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }*/
}