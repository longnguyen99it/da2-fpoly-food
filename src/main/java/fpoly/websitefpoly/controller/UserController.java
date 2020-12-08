package fpoly.websitefpoly.controller;


import fpoly.websitefpoly.dto.InvoiceDetailDto;
import fpoly.websitefpoly.exception.ResourceNotFoundException;
import fpoly.websitefpoly.entity.User;
import fpoly.websitefpoly.repository.UserRepository;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.security.CurrentUser;
import fpoly.websitefpoly.security.UserPrincipal;
import fpoly.websitefpoly.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @GetMapping("/my-invoice")
    public ResponeData<List<InvoiceDetailDto>> getInvoiceUser(@RequestParam("email") String email){
        return userService.userInvoiceDetail(email);
    }
}
