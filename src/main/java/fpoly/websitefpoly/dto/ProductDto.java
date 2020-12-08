package fpoly.websitefpoly.dto;

import fpoly.websitefpoly.entity.Category;
import fpoly.websitefpoly.entity.Topping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Nguyen Hoang Long on 11/5/2020
 * @created 11/5/2020
 * @project website-fpoly
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {

    private Long id;

    private String productName;

    private Double price;

    private String image;

    private String description;

    private String status;

    private Integer warehouses;

    private String unit;

    private Category category;

    List<Topping> toppingList;
}
