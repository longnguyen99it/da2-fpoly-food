package fpoly.websitefpoly.controller;

import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.dto.InvoiceDetailDto;
import fpoly.websitefpoly.dto.InvoiceDto;
import fpoly.websitefpoly.entity.Invoice;
import fpoly.websitefpoly.request.CreateInvocieRequest;
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
    public ResponeData<List<InvoiceDetailDto>> getInvoiceOrderByDate(@RequestBody String email) throws Exception {
        return new ResponeData<>(AppConstant.SUCCESSFUL_CODE,AppConstant.SUCCESSFUL_MESAGE,orderByDateService.getInvoiceOrder(email));
    }
    @PostMapping("")
    public ResponeData<InvoiceDto> createInvoiceOrderByDate(@RequestBody CreateInvocieRequest createInvocieRequest){
        return new ResponeData<>(AppConstant.SUCCESSFUL_CODE,AppConstant.SUCCESSFUL_MESAGE,orderByDateService.createInvoiceOrder(createInvocieRequest));
    }
}
