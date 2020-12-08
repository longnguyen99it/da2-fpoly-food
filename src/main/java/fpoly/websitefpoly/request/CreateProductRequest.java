package fpoly.websitefpoly.request;

import lombok.Data;

/**
 * @author Nguyen Hoang Long on 11/5/2020
 * @created 11/5/2020
 * @project website-fpoly
 */
@Data
public class CreateProductRequest {

    private String productName;

    private Double price;

    private String image;

    private String description;

    private String status;

    private Integer warehouses;

    private Long categoryId;
}
