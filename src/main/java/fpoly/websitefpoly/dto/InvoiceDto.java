package fpoly.websitefpoly.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import fpoly.websitefpoly.entity.Users;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Nguyen Hoang Long on 10/31/2020
 * @created 10/31/2020
 * @project fpoly-food
 */
@Data
public class InvoiceDto implements Serializable {
    private Long id;

    private Double amountTotal;

    private String deliveryAddress;

    private String description;

    private String status;

    private String paymentMethods;

    private String phone;

    private String receivingTime;

    @JsonFormat(pattern = "dd/MM/YYYY")
    private String createdAt;

    private Users users;
}
