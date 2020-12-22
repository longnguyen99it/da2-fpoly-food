package fpoly.websitefpoly.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateProductRequest {
    private String productName;
    private Double price;
    private String image;
    private String description;
    private String unit;
    private String status;
    private Integer warehouses;
    private Long categoryId;
}
