package fpoly.websitefpoly.service;

import fpoly.websitefpoly.dto.InvoiceDetailDto;
import fpoly.websitefpoly.dto.InvoiceDto;
import fpoly.websitefpoly.entity.Invoice;
import fpoly.websitefpoly.entity.InvoiceDetails;
import fpoly.websitefpoly.request.CreateInvocieRequest;

import java.util.List;

public interface OrderByDateService {

    List<InvoiceDetailDto> getInvoiceOrder(String email) throws Exception;
    InvoiceDto createInvoiceOrder(CreateInvocieRequest createInvocieRequest);
}
