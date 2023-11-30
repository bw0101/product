package com.bee.product;
import com.bee.product.service.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;
import java.util.stream.IntStream;

import static java.lang.System.Logger.Level.INFO;

@SpringBootApplication
public class ProductApplication {
    static System.Logger LOG = System.getLogger(ProductApplication.class.getName());
    public static void main(String[] args) {

        String status = "active";
        var uuid = UUID.randomUUID().toString();
        LOG.log(INFO, "Logging : %s with uuid: %s ".formatted(status, uuid));

        SpringApplication.run(ProductApplication.class, args);

        IntStream.range(0, 11).forEach(x -> System.out.println(x));
        IntStream.range(0, 11).forEach(System.out::println);

        //IntStream.range(0, 11).forEach(System.out::println);
        // IntStream.range(0, 11).forEach(x -> {x*=2; System.out.println(x);});
        IntStream.range(0, 10).forEach(x -> {System.out.println(x*=2);});
    }
}