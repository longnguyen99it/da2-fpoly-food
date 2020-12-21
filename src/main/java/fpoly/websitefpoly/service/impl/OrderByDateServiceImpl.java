package fpoly.websitefpoly.service.impl;

import fpoly.websitefpoly.common.DateTimeUtil;
import fpoly.websitefpoly.common.ModelMapperUtils;
import fpoly.websitefpoly.dto.InvoiceDetailDto;
import fpoly.websitefpoly.dto.InvoiceDto;
import fpoly.websitefpoly.dto.OrderByDateDto;
import fpoly.websitefpoly.entity.*;
import fpoly.websitefpoly.repository.*;
import fpoly.websitefpoly.request.CartRequest;
import fpoly.websitefpoly.request.CreateInvocieRequest;
import fpoly.websitefpoly.request.UpdateInvoiceRequest;
import fpoly.websitefpoly.service.InvoiceDetailsService;
import fpoly.websitefpoly.service.OrderByDateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderByDateServiceImpl implements OrderByDateService {

    private final InvoiceDetailsService invoiceDetailsService;
    private final OrderByDateRepository orderByDateRepository;
    private final UserRepository userRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final InvoiceDetailsRepository invoiceDetailsRepository;
    private final ToppingRepository toppingRepository;
    private final DetailToppingRepository detailToppingRepository;

    public OrderByDateServiceImpl(InvoiceDetailsService invoiceDetailsService,
                                  OrderByDateRepository orderByDateRepository, UserRepository userRepository, InvoiceRepository invoiceRepository, ProductRepository productRepository, InvoiceDetailsRepository invoiceDetailsRepository, ToppingRepository toppingRepository, DetailToppingRepository detailToppingRepository) {
        this.invoiceDetailsService = invoiceDetailsService;
        this.orderByDateRepository = orderByDateRepository;
        this.userRepository = userRepository;
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
        this.invoiceDetailsRepository = invoiceDetailsRepository;
        this.toppingRepository = toppingRepository;
        this.detailToppingRepository = detailToppingRepository;
    }

    @Override
    public List<OrderByDateDto> getInvoiceOrder(String email) throws Exception {
        List<OrderByDate> orderByDateList = orderByDateRepository.findAllByUsersEmail(email);
        List<OrderByDateDto> orderByDateDtoList = new ArrayList<>();
        for (OrderByDate orderByDate : orderByDateList) {
            InvoiceDetailDto invoiceDetailDto = invoiceDetailsService.getInvoiceDetails(orderByDate.getInvoice().getId());
            OrderByDateDto orderByDateDto = OrderByDateDto.builder()
                    .CartProduct(invoiceDetailDto.getCartProduct())
                    .invoiceInfo(invoiceDetailDto.getInvoiceInfo())
                    .setDefault(orderByDate.getSetDefault())
                    .build();
            orderByDateDtoList.add(orderByDateDto);
        }
        return orderByDateDtoList;
    }

    @Override
    public InvoiceDto createInvoiceOrder(CreateInvocieRequest createInvocieRequest, String email) {

        Optional<Users> user = userRepository.findByEmail(email);

        String date = createInvocieRequest.getReceivingTime() == null ? null : DateTimeUtil.convertToShortTimeString(createInvocieRequest.getReceivingTime());
        Invoice invoice = Invoice.builder()
                .fullName(createInvocieRequest.getFullName())
                .users(user.get())
                .phone(createInvocieRequest.getPhone())
                .amountTotal(createInvocieRequest.getAmountTotal())
                .deliveryAddress(createInvocieRequest.getDeliveryAddress())
                .receivingTime(date)
                .description(createInvocieRequest.getDescription())
                .paymentMethods(createInvocieRequest.getPaymentMethods())
                .status(Invoice.NEW)
                .type("Order By Date")
                .createdAt(new Date())
                .build();
        Invoice saveInvoice = invoiceRepository.save(invoice);

        OrderByDate orderByDate = OrderByDate.builder()
                .invoice(saveInvoice)
                .users(user.get())
                .setDefault(false)
                .build();
        orderByDateRepository.save(orderByDate);

        //lưu hóa đơn chi tiết
        for (CartRequest cartRequest : createInvocieRequest.getCartRequests()) {
            Product product = productRepository.findByIdAndStatus(cartRequest.getProductId(), "A");
            InvoiceDetails invoiceDetails = InvoiceDetails.builder()
                    .invoice(saveInvoice)
                    .product(product)
                    .note(cartRequest.getNote())
                    .amount(cartRequest.getQuantity() * product.getPrice())
                    .quantity(cartRequest.getQuantity())
                    .price(product.getPrice())
                    .build();
            InvoiceDetails save = invoiceDetailsRepository.save(invoiceDetails);

            if (!cartRequest.getListToppingId().isEmpty()) {
                for (Long toppingId : cartRequest.getListToppingId()) {
                    Topping topping = toppingRepository.findById(toppingId).get();
                    DetailTopping detailTopping = DetailTopping.builder()
                            .invoiceDetails(invoiceDetails)
                            .nameTopping(topping.getName())
                            .priceTopping(topping.getPrice())
                            .build();
                    detailToppingRepository.save(detailTopping);
                }
            }
        }
        return ModelMapperUtils.map(saveInvoice, InvoiceDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InvoiceDto updateInvoiceOrder(Long id, UpdateInvoiceRequest updateInvoiceRequest) throws Exception {
        try {
            Invoice invoice = invoiceRepository.findById(id).get();
            String date = updateInvoiceRequest.getReceivingTime() == null ? null : DateTimeUtil.convertToShortTimeString(updateInvoiceRequest.getReceivingTime());

            invoice.setFullName(updateInvoiceRequest.getFullName());
            invoice.setPhone(updateInvoiceRequest.getPhone());
            invoice.setAmountTotal(updateInvoiceRequest.getAmountTotal());
            invoice.setDeliveryAddress(updateInvoiceRequest.getDeliveryAddress());
            invoice.setReceivingTime(date);
            invoice.setDescription(updateInvoiceRequest.getDescription());
            invoice.setPaymentMethods(updateInvoiceRequest.getPaymentMethods());
            invoice.setStatus(Invoice.NEW);
            invoice.setType("Order By Date");
            invoice.setCreatedAt(new Date());

            Invoice saveInvoice = invoiceRepository.save(invoice);

            List<InvoiceDetails> invoiceDetailsList = invoiceDetailsRepository.findAllByInvoice(saveInvoice);
            if (!invoiceDetailsList.isEmpty()) {
                for (InvoiceDetails invoiceDetails : invoiceDetailsList) {
                    invoiceDetailsRepository.deleteById(invoiceDetails.getId());
                }
            }
            //lưu hóa đơn chi tiết
            for (CartRequest cartRequest : updateInvoiceRequest.getCartRequests()) {
                Product product = productRepository.findByIdAndStatus(cartRequest.getProductId(), "A");
                InvoiceDetails invoiceDetails = InvoiceDetails.builder()
                        .invoice(saveInvoice)
                        .product(product)
                        .note(cartRequest.getNote())
                        .amount(cartRequest.getQuantity() * product.getPrice())
                        .quantity(cartRequest.getQuantity())
                        .price(product.getPrice())
                        .build();
                InvoiceDetails save = invoiceDetailsRepository.save(invoiceDetails);

                if (!cartRequest.getListToppingId().isEmpty()) {
                    for (Long toppingId : cartRequest.getListToppingId()) {
                        Topping topping = toppingRepository.findById(toppingId).get();
                        DetailTopping detailTopping = DetailTopping.builder()
                                .invoiceDetails(invoiceDetails)
                                .nameTopping(topping.getName())
                                .priceTopping(topping.getPrice())
                                .build();
                        detailToppingRepository.save(detailTopping);
                    }
                }
            }
            return ModelMapperUtils.map(saveInvoice, InvoiceDto.class);
        } catch (Exception e) {
            throw new Exception();
        }
    }


    @Override
    public Boolean setDefault(Long id,String email) throws Exception {
        OrderByDate invoice = orderByDateRepository.findAllByInvoiceId(id);
        List<OrderByDate> orderByDateServiceList = orderByDateRepository.findAllByUsersEmail(email);
        for (OrderByDate orderByDate : orderByDateServiceList){
            if(invoice.equals(orderByDate)){
                orderByDate.setSetDefault(true);
                orderByDateRepository.save(orderByDate);
            }else {
                orderByDate.setSetDefault(false);
                orderByDateRepository.save(orderByDate);
            }
        }
        return true;
    }
}
