package fpoly.websitefpoly.service;

import fpoly.websitefpoly.dto.InvoiceDto;
import fpoly.websitefpoly.request.CreateInvocieRequest;
import fpoly.websitefpoly.request.UpdateInvoiceRequest;
import fpoly.websitefpoly.response.ResponeData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author Nguyen Hoang Long on 10/31/2020
 * @created 10/31/2020
 * @project fpoly-food
 */
@Service
public interface InvoiceService {
    ResponeData<Page<InvoiceDto>> search(String status, Pageable pageable) throws Exception;

    ResponeData<Page<InvoiceDto>> searchOffline(String status, Pageable pageable) throws Exception;

    InvoiceDto create(String type, CreateInvocieRequest createInvocieRequest, String email) throws Exception;

    ResponeData<InvoiceDto> update(Long id, UpdateInvoiceRequest updateInvoiceRequest) throws Exception;

    ResponeData<InvoiceDto> details(Long id) throws Exception;

    ResponeData<Boolean> deleted(Long id) throws Exception;

    ResponeData<Boolean> transport(Long id);

    ResponeData<Boolean> setStatus(Long id, String status);
}
