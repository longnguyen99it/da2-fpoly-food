package fpoly.websitefpoly.request;

import lombok.Data;

@Data
public class UpdateToppingRequest {
    private String nameTopping;
    private Double priceTopping;
    private String status;
}
