package fpoly.websitefpoly.dto;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceUserDto {
    List<InvoiceDetailDto> invoiceDetailDtoList;
}
