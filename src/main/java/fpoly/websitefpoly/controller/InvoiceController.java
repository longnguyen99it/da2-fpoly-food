package fpoly.websitefpoly.controller;

import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.dto.InvoiceDetailDto;
import fpoly.websitefpoly.dto.InvoiceDto;
import fpoly.websitefpoly.dto.UserInfoDto;
import fpoly.websitefpoly.entity.Invoice;
import fpoly.websitefpoly.request.CreateInvocieRequest;
import fpoly.websitefpoly.request.UpdateInvoiceRequest;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.service.InvoiceDetailsService;
import fpoly.websitefpoly.service.InvoiceService;
import fpoly.websitefpoly.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @author Nguyen Hoang Long on 10/31/2020
 * @created 10/31/2020
 * @project fpoly-food
 */
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceDetailsService invoiceDetailsService;
    private final UserService userService;

    public InvoiceController(InvoiceService invoiceService,
                             InvoiceDetailsService invoiceDetailsService,
                             UserService userService) {
        this.invoiceService = invoiceService;
        this.invoiceDetailsService = invoiceDetailsService;
        this.userService = userService;
    }

    @GetMapping(value = {"", "/"})
    private ResponeData<Page<InvoiceDto>> search(@RequestParam("status") String status, @PageableDefault(size = AppConstant.LIMIT_PAGE, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            return invoiceService.search(status, pageable);
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE, null);
        }
    }
    @GetMapping(value = "/offline")
    private ResponeData<Page<InvoiceDto>> searchOffline(@RequestParam("status") String status, @PageableDefault(size = AppConstant.LIMIT_PAGE, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            return invoiceService.searchOffline(status, pageable);
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE, null);
        }
    }

    @PostMapping("/{type}")
    public ResponeData<InvoiceDto> create(@PathVariable String type, @RequestBody CreateInvocieRequest createInvocieRequest) throws Exception {
        return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                invoiceService.create(type, createInvocieRequest));
    }

    @PutMapping("/{id}")
    public ResponeData<InvoiceDto> update(@PathVariable Long id, @RequestBody UpdateInvoiceRequest updateInvoiceRequest) throws Exception {
        return invoiceService.update(id, updateInvoiceRequest);
    }

    @GetMapping("/{id}")
    public ResponeData<InvoiceDto> details(@PathVariable Long id) throws Exception {
        return invoiceService.details(id);
    }

    @DeleteMapping("/{id}")
    public ResponeData<Boolean> delete(@PathVariable Long id) throws Exception {
        return invoiceService.deleted(id);
    }

    @GetMapping("/details/{id}")
    private ResponeData<InvoiceDetailDto> invoiceDetails(@PathVariable Long id) {
        return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                invoiceDetailsService.getInvoiceDetails(id));
    }

    @GetMapping("/transport/{id}")
    private ResponeData<Boolean> transport(@PathVariable Long id) {
        return invoiceService.transport(id);
    }

    @GetMapping("/processing/{id}")
    private ResponeData<Boolean> processing(@PathVariable Long id) {
        return invoiceService.setStatus(id, Invoice.PROCESSING);
    }

    @GetMapping("/finish/{id}")
    private ResponeData<Boolean> finish(@PathVariable Long id) {
        return invoiceService.setStatus(id, Invoice.FINISH);
    }

    @GetMapping("/cancel/{id}")
    private ResponeData<Boolean> cancel(@PathVariable Long id) {
        return invoiceService.setStatus(id, Invoice.CANCEL);
    }

    @GetMapping("/offline/{id}")
    private ResponeData<Boolean> offlineFinish(@PathVariable Long id) {
        return invoiceService.setStatus(id, Invoice.FINISH);
    }

    @GetMapping("/top-user")
    public ResponeData<Page<UserInfoDto>> topUserInfo(@PageableDefault(size = 10) Pageable pageable) {
        try {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                    userService.topUserInfo(pageable));
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
        }
    }
}
