package fpoly.websitefpoly.dto;

import lombok.Data;

@Data
public class UserInfoDto {
    private Long id;
    private String name;
    private String email;
    private String imageUrl;
    private String phone;
    private String address;
    private String totalPrice;
}
