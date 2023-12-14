package com.bee.product.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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

@Getter
@Setter
public class PaginationFilterDto {

   // @NotNull
    private String siteId;

    @NotNull
    private int page;

    @NotNull
    private int pageSize;

   // @NotNull
    private Sorting sorting;

    private Filter filter;

    @Getter
    @Setter
    public static class Sorting {
        @NotNull
        private String attribute;
        @NotNull
        private String order;
    }

    @Getter
    @Setter
    public static class Filter {
        private String site;
        private String type;
        private String version;
        private String patch;
        private String status;
        private ModifiedAt modifiedAt;
        private String size;
        private String takenTime;
        private String speed;

        @Getter
        @Setter
        public static class ModifiedAt {
            private String from;
            private String to;
        }
    }
}