package fpoly.websitefpoly.service;

import fpoly.websitefpoly.dto.InvoiceDetailDto;
import fpoly.websitefpoly.dto.UserDto;
import fpoly.websitefpoly.dto.UserInfoDto;
import fpoly.websitefpoly.request.UpdateUserRequest;
import fpoly.websitefpoly.response.ResponeData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Nguyen Hoang Long
 * @created 11/14/2020
 * @project website-fpoly
 */
public interface UserService {
    ResponeData<List<InvoiceDetailDto>> userInvoiceDetail(String email);

    Page<UserInfoDto> topUserInfo(Pageable pageable);

    UserDto updateInfoUser(UpdateUserRequest updateUserRequest) throws Exception;
}
