package fpoly.websitefpoly.controller;

import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.dto.InvoiceDto;
import fpoly.websitefpoly.dto.OrderByDateDto;
import fpoly.websitefpoly.request.CreateInvocieRequest;
import fpoly.websitefpoly.request.UpdateInvoiceRequest;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.service.OrderByDateService;
import fpoly.websitefpoly.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/order-by-date")
public class OrderByDateController {

    private final OrderByDateService orderByDateService;
    private final UserService userService;

    public OrderByDateController(OrderByDateService orderByDateService, UserService userService) {
        this.orderByDateService = orderByDateService;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponeData<List<OrderByDateDto>> getInvoiceOrderByDate(@RequestParam String email) throws Exception {
        return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, orderByDateService.getInvoiceOrder(email));
    }

    @PostMapping("")
    public ResponeData<InvoiceDto> createInvoiceOrderByDate(@RequestBody CreateInvocieRequest createInvocieRequest, HttpServletRequest request) {
        String email = userService.email(request);
        return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                orderByDateService.createInvoiceOrder(createInvocieRequest, email));
    }

    @PutMapping("/set-default/{id}")
    public ResponeData<Boolean> setDefault(@PathVariable(name = "id") Long id,
                                           HttpServletRequest request) throws Exception {
        try {
            String email = userService.email(request);
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                    orderByDateService.setDefault(id, email));
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE,
                    false);
        }
    }

    @PutMapping("/{id}")
    InvoiceDto updateOrderByDate(@PathVariable Long id, @RequestBody UpdateInvoiceRequest updateInvoiceRequest) throws Exception {
        return orderByDateService.updateInvoiceOrder(id, updateInvoiceRequest);
    }
}
