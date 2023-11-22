package com.bee.product.controller;
import com.bee.product.model.LargeRequestDTO;
import com.bee.product.model.Product;
import com.bee.product.model.ProductFilterDTO;
import com.bee.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/products", produces="application/json")
public class ProductRestController {

    private ProductService service;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return  new ResponseEntity<>(service.listAll(), HttpStatus.OK);
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