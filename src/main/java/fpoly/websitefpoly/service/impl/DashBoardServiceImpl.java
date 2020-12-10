package fpoly.websitefpoly.service.impl;

import fpoly.websitefpoly.entity.Invoice;
import fpoly.websitefpoly.repository.InvoiceRepository;
import fpoly.websitefpoly.service.DashBoardService;
import org.springframework.stereotype.Service;

@Service
public class DashBoardServiceImpl implements DashBoardService {
    private final InvoiceRepository invoiceRepository;

    public DashBoardServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public long countInvoiceNew() {
        long count1 = invoiceRepository.countAllByStatus(Invoice.CHUA_SU_LY);
        long count2 = invoiceRepository.countAllByStatus(Invoice.ĐANG_XU_LY);
        return count1 + count2;
    }

    @Override
    public long countInvoiceByStatus(String status) {
        if (status.equals(Invoice.CHUA_SU_LY) || status.equals(Invoice.ĐANG_XU_LY)) {
            return countInvoiceNew();
        }
        return invoiceRepository.countAllByStatus(status);
    }
}
