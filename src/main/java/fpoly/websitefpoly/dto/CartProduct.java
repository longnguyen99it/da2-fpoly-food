package fpoly.websitefpoly.dto;

import fpoly.websitefpoly.entity.DetailTopping;
import fpoly.websitefpoly.entity.Topping;
import lombok.*;

import java.util.List;

/**
 * @author Nguyen Hoang Long
 * @created 11/26/2020
 * @project website-fpoly
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProduct {

    private Double price;

    private Integer quantity;

    private Double amount;

    private ProductInfo productInfo;

    private String note;

    private List<ToppingDto> toppingList;
}
