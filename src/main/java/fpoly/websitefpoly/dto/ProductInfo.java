package fpoly.websitefpoly.dto;

import fpoly.websitefpoly.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author Nguyen Hoang Long
 * @created 11/26/2020
 * @project website-fpoly
 */
@AllArgsConstructor
@Data
public class ProductInfo {
    private Long id;

    private String productName;

    private Double price;

    private String image;

    private String description;

    private String status;

    private Integer warehouses;


    public ProductInfo(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.image = product.getImage();
        this.description = product.getDescription();
        this.status = product.getStatus();
        this.warehouses = product.getWarehouses();
    }
}
