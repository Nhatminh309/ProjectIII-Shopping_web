package org.demo.project.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter

public class ProductRequest {
    private String product_name;
    private String description;
    private String type;
    private Integer prize;
    private LocalDate created_at;
    private Integer sizeS;
    private Integer sizeM;
    private Integer sizeL;
    private Integer sizeXL;
    private Integer sizeXXL;
    private String mainImage;
    private List<String> images;


}
