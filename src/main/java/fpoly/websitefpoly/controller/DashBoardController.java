package fpoly.websitefpoly.controller;

import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.dto.StatisticsDto;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.service.DashBoardService;
import fpoly.websitefpoly.service.InvoiceDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {
    private final InvoiceDetailsService invoiceDetailsService;
    private final DashBoardService dashBoardService;

    public DashBoardController(InvoiceDetailsService invoiceDetailsService,
                               DashBoardService dashBoardService) {
        this.invoiceDetailsService = invoiceDetailsService;
        this.dashBoardService = dashBoardService;
    }

    @GetMapping("/statistics/{status}")
    private ResponeData<Long> statistics(@PathVariable String status) {
        try {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                    dashBoardService.countInvoiceByStatus(status));
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
        }
    }

    @GetMapping("/chart/{type}")
    private ResponeData<StatisticsDto> chart(@PathVariable String type) {
        return null;
    }
}
