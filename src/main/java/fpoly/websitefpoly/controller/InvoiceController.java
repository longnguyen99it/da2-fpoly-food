package fpoly.websitefpoly.controller;

import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.dto.InvoiceDetailDto;
import fpoly.websitefpoly.dto.InvoiceDto;
import fpoly.websitefpoly.entity.Invoice;
import fpoly.websitefpoly.request.CreateInvocieRequest;
import fpoly.websitefpoly.request.UpdateInvoiceRequest;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.service.InvoiceDetailsService;
import fpoly.websitefpoly.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceDetailsService invoiceDetailsService;

    @GetMapping(value = {"", "/"})
    private ResponeData<Page<InvoiceDto>> search(@RequestParam("status") String status, @PageableDefault(size = AppConstant.LIMIT_PAGE, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            return invoiceService.search(status, pageable);
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE, null);
        }
    }

    @PostMapping("/")
    public ResponeData<InvoiceDto> create(@RequestBody CreateInvocieRequest createInvocieRequest) throws Exception {
        return invoiceService.created(createInvocieRequest);
    }

    @PutMapping("/{id}")
    public ResponeData<InvoiceDto> update(@PathVariable Long id, @RequestBody UpdateInvoiceRequest updateInvoiceRequest) throws Exception {
        return invoiceService.updated(id, updateInvoiceRequest);
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
        return invoiceService.setStatus(id, Invoice.ĐANG_CHẾ_BIẾN);
    }

    @GetMapping("/finish/{id}")
    private ResponeData<Boolean> finish(@PathVariable Long id) {
        return invoiceService.setStatus(id, Invoice.HOAN_THANH);
    }

    @GetMapping("/statistics/{status}")
    private ResponeData<Long> statistics(@PathVariable String status) {
        try {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                    invoiceDetailsService.countInvoiceByStatus(status));
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
        }
    }
}
