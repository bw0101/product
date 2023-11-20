package com.bee.product.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *  DTO (Data Transfer Object) that encapsulates all the parameters you expect.
 *  This helps in validating and processing the data easily.
 */
public class LargeRequestDTO {

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    private String path;

    @NotNull
    @Size(min = 2, max = 50)
    private String status;

    @Email
    private String email;
}
