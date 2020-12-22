package fpoly.websitefpoly.request;

import lombok.Data;

@Data
public class CreateToppingRequest {
    private String nameTopping;
    private Double priceTopping;
    private String status;
}
