package fpoly.websitefpoly.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderByDateDto {
    List<CartProduct> CartProduct;
    private InvoiceInfo invoiceInfo;
    private Boolean setDefault;
}
