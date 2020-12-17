package fpoly.websitefpoly.service.impl;

import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.common.DateTimeUtil;
import fpoly.websitefpoly.common.ModelMapperUtils;
import fpoly.websitefpoly.dto.InvoiceDto;
import fpoly.websitefpoly.entity.*;
import fpoly.websitefpoly.repository.*;
import fpoly.websitefpoly.request.CartRequest;
import fpoly.websitefpoly.request.CreateInvocieRequest;
import fpoly.websitefpoly.request.UpdateInvoiceRequest;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.service.InvoiceService;
import fpoly.websitefpoly.service.SendEmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final ToppingRepository toppingRepository;
    private final DetailToppingRepository detailToppingRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceDetailsRepository invoiceDetailsRepository;
    private final SendEmailService sendEmailService;

    public InvoiceServiceImpl(ToppingRepository toppingRepository,
                              DetailToppingRepository detailToppingRepository,
                              UserRepository userRepository,
                              ProductRepository productRepository,
                              InvoiceRepository invoiceRepository,
                              InvoiceDetailsRepository invoiceDetailsRepository,
                              SendEmailService sendEmailService) {
        this.toppingRepository = toppingRepository;
        this.detailToppingRepository = detailToppingRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.invoiceRepository = invoiceRepository;
        this.invoiceDetailsRepository = invoiceDetailsRepository;
        this.sendEmailService = sendEmailService;
    }

    @Override
    public ResponeData<Page<InvoiceDto>> search(String status, Pageable pageable) throws Exception {
        String[] a = new String[0];
        String type = "online";
        if (status.equals("new")) {
            a = new String[]{Invoice.NEW, Invoice.WATCHED};
            type="offline";
        }
        if (status.equals("processing")) {
            a = new String[]{Invoice.PROCESSING};
        }
        if (status.equals("transport")) {
            a = new String[]{Invoice.TRANSPORT};
        }
        if (status.equals("finish")) {
            a = new String[]{Invoice.FINISH};
        }
        if (status.equals("cancel")) {
            a = new String[]{Invoice.CANCEL};
        }
        Page<Invoice> invoicePage = invoiceRepository.searchInvoice(a,type, pageable);
        Page<InvoiceDto> invoiceDtoPage = invoicePage.map(new Function<Invoice, InvoiceDto>() {
            @Override
            public InvoiceDto apply(Invoice invoice) {
                InvoiceDto invoiceDto = ModelMapperUtils.map(invoice, InvoiceDto.class);
                return invoiceDto;
            }
        });
        return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, invoiceDtoPage);
    }

    @Override
    public ResponeData<Page<InvoiceDto>> searchOffline(String status, Pageable pageable) throws Exception {
        String[] a = new String[0];
        String type = "offline";
        if (status.equals("new")) {
            a = new String[]{Invoice.NEW, Invoice.WATCHED};
        }
        if (status.equals("finish")) {
            a = new String[]{Invoice.FINISH};
        }
        Page<Invoice> invoicePage = invoiceRepository.searchInvoice(a,type, pageable);
        Page<InvoiceDto> invoiceDtoPage = invoicePage.map(new Function<Invoice, InvoiceDto>() {
            @Override
            public InvoiceDto apply(Invoice invoice) {
                InvoiceDto invoiceDto = ModelMapperUtils.map(invoice, InvoiceDto.class);
                return invoiceDto;
            }
        });
        return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, invoiceDtoPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InvoiceDto create(String type, CreateInvocieRequest createInvocieRequest) throws Exception {
        try {
            String email = "";
            if (createInvocieRequest.getEmail() == null) {
                email = "offline@gmail.com";
            } else {
                email = createInvocieRequest.getEmail();
            }
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
                    .type(type)
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
            if (type.equals("online")) {
                sendEmailService.sendEmail(user.get().getEmail(), "[FPOLY FOOD] Thông tin đơn hàng", proudctDetails);
            }

            return ModelMapperUtils.map(saveInvoice, InvoiceDto.class);
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception();
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<InvoiceDto> update(Long id, UpdateInvoiceRequest updateInvoiceRequest) throws Exception {
        Invoice invoice = invoiceRepository.findByIdAndStatus(id, "A");
        if (invoice == null) {
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, null);
        }
        invoice = setUpdate(invoice, updateInvoiceRequest);
        Invoice save = invoiceRepository.save(invoice);
        return null;
    }

    private Invoice setUpdate(Invoice invoice, UpdateInvoiceRequest updateInvoiceRequest) {
//        if (!StringUtils.isEmpty(updateInvoiceRequest.getTotalPrice())) {
//            invoice.setAmountTotal(updateInvoiceRequest.getTotalPrice());
//        }
//        if (!StringUtils.isEmpty(updateInvoiceRequest.getDeliveryAddress())) {
//            invoice.setDeliveryAddress(updateInvoiceRequest.getDeliveryAddress());
//        }
//        if (!StringUtils.isEmpty(updateInvoiceRequest.getDescription())) {
//            invoice.setDescription(updateInvoiceRequest.getDescription());
//        }
//        if (!StringUtils.isEmpty(updateInvoiceRequest.getStatus())) {
//            invoice.setStatus(updateInvoiceRequest.getStatus());
//        }
        return invoice;
    }

    @Override
    public ResponeData<InvoiceDto> details(Long id) throws Exception {
        Invoice invoice = invoiceRepository.findByIdAndStatus(id, "A");
        if (invoice == null) {
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE);
        }
        return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, ModelMapperUtils.map(invoice, InvoiceDto.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<Boolean> deleted(Long id) throws Exception {
        Invoice invoice = invoiceRepository.findByIdAndStatus(id, "A");
        if (invoice != null) {
//            invoiceEntity.setDeletedBy(Flag.userEntityFlag.getEmail());
            invoice.setStatus("D");
            invoiceRepository.save(invoice);
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, true);
        }
        return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, false);
    }

    @Override
    public ResponeData<Boolean> transport(Long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isPresent()) {
            invoice.get().setStatus(Invoice.TRANSPORT);
            invoiceRepository.save(invoice.get());
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, true);
        }
        return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, false);
    }


    @Override
    public ResponeData<Boolean> setStatus(Long id, String status) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isPresent()) {
            invoice.get().setStatus(status);
            invoiceRepository.save(invoice.get());
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, true);
        }
        return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, false);
    }
}
