package fpoly.websitefpoly.service.impl;

import fpoly.websitefpoly.common.ModelMapperUtils;
import fpoly.websitefpoly.dto.*;
import fpoly.websitefpoly.entity.DetailTopping;
import fpoly.websitefpoly.entity.Invoice;
import fpoly.websitefpoly.entity.InvoiceDetails;
import fpoly.websitefpoly.repository.DetailToppingRepository;
import fpoly.websitefpoly.repository.InvoiceDetailsRepository;
import fpoly.websitefpoly.repository.InvoiceRepository;
import fpoly.websitefpoly.service.InvoiceDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Nguyen Hoang Long
 * @created 11/25/2020
 * @project website-fpoly
 */
@Service
public class InvoiceDetailsServiceImpl implements InvoiceDetailsService {
    private final DetailToppingRepository detailToppingRepository;
    private final InvoiceDetailsRepository invoiceDetailsRepository;
    private final InvoiceRepository invoiceRepository;

    public InvoiceDetailsServiceImpl(DetailToppingRepository detailToppingRepository,
                                     InvoiceDetailsRepository invoiceDetailsRepository,
                                     InvoiceRepository invoiceRepository) {
        this.detailToppingRepository = detailToppingRepository;
        this.invoiceDetailsRepository = invoiceDetailsRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public InvoiceDetailDto getInvoiceDetails(Long id) {
        try {
            Optional<Invoice> invoiceEntity = invoiceRepository.findById(id);
            if (!invoiceEntity.isPresent()) {
                return null;
            }
            if (invoiceEntity.get().getStatus().equals(Invoice.CHUA_SU_LY)) {
                invoiceEntity.get().setStatus(Invoice.ĐANG_XU_LY);
            }
            Invoice update = invoiceRepository.save(invoiceEntity.get());
            InvoiceInfo invoiceInfo = ModelMapperUtils.map(update, InvoiceInfo.class);
            List<InvoiceDetails> invoiceDetailsEntities = invoiceDetailsRepository.findAllByInvoice(invoiceEntity.get());
            List<CartProduct> cartProductList = new ArrayList<>();
            for (InvoiceDetails detailsEntity : invoiceDetailsEntities) {
                CartProduct cartProduct = ModelMapperUtils.map(detailsEntity, CartProduct.class);
                ProductInfo productInfo = new ProductInfo(detailsEntity.getProduct());
                List<DetailTopping> detailToppingList = detailToppingRepository.findAllByInvoiceDetails(detailsEntity);
                if (!detailToppingList.isEmpty()) {
                    List<ToppingDto> toppingDtoList = ModelMapperUtils.mapAll(detailToppingList, ToppingDto.class);
                    cartProduct.setToppingList(toppingDtoList);
                } else {
                    cartProduct.setToppingList(new ArrayList<>());
                }
                cartProduct.setProductInfo(productInfo);
                cartProductList.add(cartProduct);
            }

            InvoiceDetailDto invoiceDetailDto = InvoiceDetailDto.builder()
                    .CartProduct(cartProductList)
                    .invoiceInfo(invoiceInfo)
                    .build();
            return invoiceDetailDto;
        } catch (Exception e) {
            return null;
        }
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

    @Override
    public Double revenue(Date fromDate, Date toDate) {
        return null;
    }
}
