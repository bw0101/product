package com.bee.product.service;

import com.bee.product.model.Customer;
import com.bee.product.model.Product;
import com.bee.product.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private CustomerRepository repo;
    private MessageSender sender;

    public Long count(){
        sender.sendMessage("## action : count ");
        return repo.count();
    }

    public List<Customer> listAll(){
        List<Customer> customers =  repo.findAll();

        if (customers.isEmpty()) {
            sender.sendMessage("No customers found.");
            return Collections.emptyList();  // Return an empty list instead of null
        } else {
            sender.sendMessage("## action : list All Customers ");
            return customers;
        }
    }

    public Page<Customer> getEntities(int page, int size) {
        sender.sendMessage("## action : pagination with page nr.= " + page + " and number of records on a page = " + size + " and sorted by name, descending");
        return repo.findAll(PageRequest.of(page, size, Sort.by("firstName").descending()));
    }

}