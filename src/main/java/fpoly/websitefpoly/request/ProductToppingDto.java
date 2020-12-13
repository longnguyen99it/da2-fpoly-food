package fpoly.websitefpoly.request;

import fpoly.websitefpoly.entity.Topping;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductToppingDto {
    List<Topping> toppingList;
}
