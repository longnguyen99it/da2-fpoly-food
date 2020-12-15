package fpoly.websitefpoly.service.impl;

import fpoly.websitefpoly.common.DateTimeUtil;
import fpoly.websitefpoly.common.ModelMapperUtils;
import fpoly.websitefpoly.dto.InvoiceDetailDto;
import fpoly.websitefpoly.dto.InvoiceDto;
import fpoly.websitefpoly.entity.*;
import fpoly.websitefpoly.repository.*;
import fpoly.websitefpoly.request.CartRequest;
import fpoly.websitefpoly.request.CreateInvocieRequest;
import fpoly.websitefpoly.service.InvoiceDetailsService;
import fpoly.websitefpoly.service.OrderByDateService;
import org.springframework.stereotype.Service;

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
    public List<InvoiceDetailDto> getInvoiceOrder(String email) throws Exception {
        List<OrderByDate> orderByDateList = orderByDateRepository.findAllByUsersEmail(email);
        List<InvoiceDetailDto> invoiceDetailDtoList = new ArrayList<>();
        for (OrderByDate orderByDate : orderByDateList){
            invoiceDetailDtoList.add(invoiceDetailsService.getInvoiceDetails(orderByDate.getInvoice().getId()));
        }
        return invoiceDetailDtoList;
    }

    @Override
    public InvoiceDto createInvoiceOrder(CreateInvocieRequest createInvocieRequest) {

        Optional<Users> user = userRepository.findByEmail(createInvocieRequest.getEmail());

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

        String proudctDetails = "";
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
            proudctDetails += save.getProduct().getProductName() + "|" + save.getProduct().getPrice() + " |" +
                    save.getQuantity() + "|" + save.getAmount() + "\n";
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
        return ModelMapperUtils.map(saveInvoice,InvoiceDto.class);
    }
}
