package fpoly.websitefpoly.service;

import fpoly.websitefpoly.dto.InvoiceDetailDto;

import java.util.Date;

/**
 * @author Nguyen Hoang Long
 * @created 11/25/2020
 * @project website-fpoly
 */
public interface InvoiceDetailsService {

    InvoiceDetailDto getInvoiceDetails(Long id);





    Double revenue(Date fromDate, Date toDate);
}
