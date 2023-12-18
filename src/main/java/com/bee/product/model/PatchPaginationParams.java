package com.bee.product.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PatchPaginationParams {

    @NotNull
    private Integer  page;

    @NotNull
    private Integer pageSize;

    @NotNull
    private Sorting sorting;

    public static class Sorting {
        @NotNull
        private String attribute;

        @NotNull
        private String order;

    }

   /* Map<String , String> sorting;
    Map<String , String> filter;*/
    /**
     *

     page*
     pageSize*
     sorting    {
                        attribute*
                        order* [asc, desc]
                     }
     filter         {
                        type
                        version
                        patch
                        status
                        modifiedAt  {
                                                from
                                                to
                                            }
                        size
                        takenTime
                        speed
                        }
     */

}
