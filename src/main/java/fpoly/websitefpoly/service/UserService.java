package fpoly.websitefpoly.service;

import fpoly.websitefpoly.dto.InvoiceDetailDto;
import fpoly.websitefpoly.response.ResponeData;

import java.util.List;

/**
 * @author Nguyen Hoang Long
 * @created 11/14/2020
 * @project website-fpoly
 */
public interface UserService {

    ResponeData<List<InvoiceDetailDto>> userInvoiceDetail(String email);
}
