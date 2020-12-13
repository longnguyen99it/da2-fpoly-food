package fpoly.websitefpoly.request;

import lombok.Data;

import java.util.List;

/**
 * @author Nguyen Hoang Long on 10/31/2020
 * @created 10/31/2020
 * @project fpoly-food
 */
@Data
public class CreateInvocieRequest {
    private String fullName;
    private String email;
    private Double amountTotal;
    private String paymentMethods;
    private String deliveryAddress;
    private String description;
    private String phone;
    private List<CartRequest> cartRequests;
}
