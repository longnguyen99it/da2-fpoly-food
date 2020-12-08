package fpoly.websitefpoly.request;

import lombok.Data;

/**
 * @author Nguyen Hoang Long on 9/9/2020
 * @created 9/9/2020
 * @project demo
 */
@Data
public class CreateCustomerRequest {
    private String email;
    private String passWord;
    private String fullName;
    private String phone;
    private String address;
    private String status;
}
