package fpoly.websitefpoly.controller;


import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.common.ModelMapperUtils;
import fpoly.websitefpoly.dto.InvoiceDetailDto;
import fpoly.websitefpoly.dto.UserDto;
import fpoly.websitefpoly.repository.UserRepository;
import fpoly.websitefpoly.request.UpdateUserRequest;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponeData<UserDto> detail(@RequestParam("email") String email) {
        try {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                    ModelMapperUtils.map(userRepository.findByEmail(email).get(), UserDto.class));
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE);
        }
    }

    @GetMapping("/my-invoice")
    public ResponeData<List<InvoiceDetailDto>> getInvoiceUser(@RequestParam("email") String email) {
        return userService.userInvoiceDetail(email);
    }

    @PutMapping("")
    public ResponeData<UserDto> updateInfoUser(@RequestBody UpdateUserRequest updateUserRequest) {
        try {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                    userService.updateInfoUser(updateUserRequest));
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
        }
    }
}
