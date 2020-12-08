package fpoly.websitefpoly.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fpoly.websitefpoly.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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

    @JsonFormat(pattern = "dd/MM/YYYY")
    private String createdAt;

    private User user;
}
