package fpoly.websitefpoly.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fpoly.websitefpoly.entity.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Nguyen Hoang Long on 9/9/2020
 * @created 9/9/2020
 * @project demo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private String imageUrl;

    private Boolean emailVerified;

    private String password;

    private AuthProvider provider;

    private String providerId;

    private String status;

    private String phone;

    private String address;
}
