package fpoly.websitefpoly.request;

import lombok.Data;

/**
 * @author Nguyen Hoang Long on 10/31/2020
 * @created 10/31/2020
 * @project fpoly-food
 */
@Data
public class UpdateInvoiceRequest {
    private Double totalPrice;
    private String deliveryAddress;
    private String description;
    private String status;
}
