package fpoly.websitefpoly.request;

import lombok.Data;

import java.util.Date;

/**
 * @author Nguyen Hoang Long on 11/5/2020
 * @created 11/5/2020
 * @project website-fpoly
 */
@Data
public class SearchProductRequest {
    private String productName;
    private Long categoryId;
}
