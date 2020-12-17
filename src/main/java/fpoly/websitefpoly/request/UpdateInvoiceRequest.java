package fpoly.websitefpoly.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Nguyen Hoang Long on 10/31/2020
 * @created 10/31/2020
 * @project fpoly-food
 */
@Data
public class UpdateInvoiceRequest {
    private String fullName;
    private String email;
    private Double amountTotal;
    private String paymentMethods;
    private String deliveryAddress;
    private String buildingAddress;
    @JsonFormat(pattern = "hh:mm")
    private Date receivingTime;
    private String description;
    private String phone;
    private List<CartRequest> cartRequests;
}
