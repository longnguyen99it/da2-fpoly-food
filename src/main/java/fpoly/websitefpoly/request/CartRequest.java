package fpoly.websitefpoly.request;

import lombok.Data;

import java.util.List;

@Data
public class CartRequest {
    private Long productId;
    private Integer quantity;
    private List<Long> listToppingId;
}
