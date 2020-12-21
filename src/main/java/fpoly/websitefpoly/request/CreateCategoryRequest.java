package fpoly.websitefpoly.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateCategoryRequest {
    private String categoryName;
    private String image;
}
