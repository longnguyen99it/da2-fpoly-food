package fpoly.websitefpoly.request;


import lombok.Data;

@Data
public class SearchToppingRequest {
    private String nameTopping;
    private Double priceTopping;
    private String status;
}
