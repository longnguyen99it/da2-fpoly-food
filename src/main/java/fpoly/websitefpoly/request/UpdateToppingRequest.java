package fpoly.websitefpoly.request;

import lombok.Data;

@Data
public class UpdateToppingRequest {
    private String name;
    private Double price;
    private String status;
}
