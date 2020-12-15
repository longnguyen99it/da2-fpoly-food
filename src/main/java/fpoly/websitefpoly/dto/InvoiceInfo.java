package fpoly.websitefpoly.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import fpoly.websitefpoly.entity.Users;
import lombok.Data;

/**
 * @author Nguyen Hoang Long
 * @created 11/26/2020
 * @project website-fpoly
 */
@Data
public class InvoiceInfo {
    private Long id;

    private Double amountTotal;

    private String fullName;

    private String deliveryAddress;

    private String description;

    private String status;

    private String paymentMethods;

    private String phone;

    @JsonFormat(pattern = "dd/MM/YYYY")
    private String createdAt;

    private Users users;
}
