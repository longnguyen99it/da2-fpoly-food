package fpoly.websitefpoly.service;

import fpoly.websitefpoly.dto.InvoiceDto;
import fpoly.websitefpoly.dto.OrderByDateDto;
import fpoly.websitefpoly.request.CreateInvocieRequest;
import fpoly.websitefpoly.request.UpdateInvoiceRequest;

import java.util.List;

public interface OrderByDateService {

    List<OrderByDateDto> getInvoiceOrder(String email) throws Exception;

    InvoiceDto createInvoiceOrder(CreateInvocieRequest createInvocieRequest);

    InvoiceDto updateInvoiceOrder(Long id, UpdateInvoiceRequest updateInvoiceRequest) throws Exception;

    Boolean setDefault(Long id,Long status) throws Exception;
}
