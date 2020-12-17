package fpoly.websitefpoly.controller;

import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.dto.InvoiceDetailDto;
import fpoly.websitefpoly.dto.InvoiceDto;
import fpoly.websitefpoly.dto.OrderByDateDto;
import fpoly.websitefpoly.entity.OrderByDate;
import fpoly.websitefpoly.request.CreateInvocieRequest;
import fpoly.websitefpoly.request.UpdateInvoiceRequest;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.service.OrderByDateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-by-date")
public class OrderByDateController {

    private final OrderByDateService orderByDateService;

    public OrderByDateController(OrderByDateService orderByDateService) {
        this.orderByDateService = orderByDateService;
    }

    @GetMapping("")
    public ResponeData<List<OrderByDateDto>> getInvoiceOrderByDate(@RequestParam String email) throws Exception {
        return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, orderByDateService.getInvoiceOrder(email));
    }

    @PostMapping("")
    public ResponeData<InvoiceDto> createInvoiceOrderByDate(@RequestBody CreateInvocieRequest createInvocieRequest) {
        return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, orderByDateService.createInvoiceOrder(createInvocieRequest));
    }

    @PutMapping("/{id}/{status}")
    public ResponeData<Boolean> setDefault(@PathVariable(name = "id") Long id, @PathVariable(name = "status") Long status) throws Exception {
        try {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                    orderByDateService.setDefault(id, status));
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE,
                    false);
        }
    }
    @PutMapping("/{id}")
    InvoiceDto updateOrderByDate(@PathVariable Long id,@RequestBody UpdateInvoiceRequest updateInvoiceRequest) throws Exception {
        return orderByDateService.updateInvoiceOrder(id,updateInvoiceRequest);
    }
}
