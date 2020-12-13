package fpoly.websitefpoly.request;

import fpoly.websitefpoly.entity.Topping;
import lombok.Data;

import java.util.List;

@Data
public class ToppingRequest {
    List<Topping> toppingList;
}
