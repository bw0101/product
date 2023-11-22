package com.bee.product.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductFilterDTO {

    private List<String> names;
    private List<String> statuses;
}