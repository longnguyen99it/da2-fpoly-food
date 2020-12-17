package fpoly.websitefpoly.request;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {
    private List<Long> productId;
}
