package fpoly.websitefpoly.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Nguyen Hoang Long
 * @created 11/11/2020
 * @project website-fpoly
 */
@Data
@Builder
public class InvoiceDetailDto {

    List<CartProduct> CartProduct;

    private InvoiceInfo invoiceInfo;
}
