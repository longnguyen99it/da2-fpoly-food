package fpoly.websitefpoly.request;

import lombok.Data;

@Data
public class CreateToppingRequest {
    private String name;
    private Double price;
    private String status;
}
