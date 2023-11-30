package com.bee.product.controller;

import com.bee.product.model.Customer;
import com.bee.product.service.CacheService;
import com.bee.product.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/customers", produces="application/json")
public class CustomerRestController {

    private CustomerService service;
    private CacheService cacheService;

        @GetMapping
        public ResponseEntity<List<Customer>> getAll(){
            return  new ResponseEntity<>(service.listAll(), HttpStatus.OK);
        }

    @GetMapping("/entities")
    public Page<Customer> getEntities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Customer> response =service.getEntities(page, size);

        CacheService cacheService1 = cacheService;
        for (Customer customer : response) {
            cacheService1.loadIntoCache(customer.firstName, 42L);
        }

        return response;
    }

    /**
     * Play with cache of caffeine
     * @return
     */
    @GetMapping("/cache")
    public ResponseEntity<List<Customer>> getCacheContent() {
        List<Customer> customers = service.listAll();
        List<Customer> response = new ArrayList<>();

        loadCache();

        IntStream.range(0, 10)          // Generate numbers from 0 to 9
                .mapToObj(Integer::toString) // Convert each int to String
                .map(s -> "Number: " + s)    // Prefix each string with "Number: "
                .forEach(System.out::println); // Print each modified string

        for (Customer customer : customers ) {
            String key = customer.firstName;
            Long value = cacheService.getFromCache(key);
            if (value == null) {
                System.out.println("No value found for key: " + key);
            } else {
                System.out.println("Retrieved value: " + value);
                if (value > 0L){
                    customer.setValue(value);
                }
            }

            response.add(customer);
        }

        ConcurrentMap<String, Object> cacheContent = cacheService.getContent();

        AtomicInteger rowCount = new AtomicInteger(1);
        cacheContent.forEach((key, value) -> {
            System.out.println("## Row " + rowCount.getAndIncrement() + ": " + key + ": " + value);
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void loadCache() {
        Random random = new Random();
        // Generates a number between 0 (inclusive) and 11 (exclusive)
        Long randomNumber = (long) random.nextInt(11);
        for (Customer customer : service.listAll()) {
            randomNumber = (long) random.nextInt(11);
            cacheService.loadIntoCache(customer.firstName, randomNumber);
        }
    }

    @GetMapping("/count")
    public  Long count(){
            return service.count();
    }
}
