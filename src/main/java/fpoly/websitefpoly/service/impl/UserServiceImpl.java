package fpoly.websitefpoly.service.impl;

import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.common.ModelMapperUtils;
import fpoly.websitefpoly.dto.InvoiceDetailDto;
import fpoly.websitefpoly.dto.UserDto;
import fpoly.websitefpoly.dto.UserInfoDto;
import fpoly.websitefpoly.entity.Invoice;
import fpoly.websitefpoly.entity.User;
import fpoly.websitefpoly.repository.InvoiceRepository;
import fpoly.websitefpoly.repository.UserRepository;
import fpoly.websitefpoly.request.UpdateUserRequest;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.service.InvoiceDetailsService;
import fpoly.websitefpoly.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final InvoiceDetailsService invoiceDetailsService;
    private final InvoiceRepository invoiceRepository;

    public UserServiceImpl(UserRepository userRepository, InvoiceDetailsService invoiceDetailsService, InvoiceRepository invoiceRepository) {
        this.userRepository = userRepository;
        this.invoiceDetailsService = invoiceDetailsService;
        this.invoiceRepository = invoiceRepository;
    }

    //Lấy danh sách hóa đơn của user
    @Override
    public ResponeData<List<InvoiceDetailDto>> userInvoiceDetail(String email) {
        try {
            //Tìm kiếm user
            Optional<User> user = userRepository.findByEmail(email);
            if (!user.isPresent()) {
                new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
            }
            //Lấy danh sách hóa đơn của user
            List<Invoice> invoiceList = invoiceRepository.findAllByUser(user.get());
            List<InvoiceDetailDto> invoiceDetailDtoList = new ArrayList<>();
            for (Invoice invoice : invoiceList) {
                invoiceDetailDtoList.add(invoiceDetailsService.getInvoiceDetails(invoice.getId()));
            }

            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, invoiceDetailDtoList);
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
        }
    }

    @Override
    public Page<UserInfoDto> topUserInfo(Pageable pageable) {
        Page<User> userPage = userRepository.topInvoice(Invoice.FINISH, pageable);
        Page<UserInfoDto> userInfoDtoPage = userPage.map(new Function<User, UserInfoDto>() {
            @Override
            public UserInfoDto apply(User user) {
                Double totalInvoice = invoiceRepository.sumTotalInvoice(user);
                UserInfoDto userInfoDto = ModelMapperUtils.map(user, UserInfoDto.class);
                userInfoDto.setTotalPrice(totalInvoice.toString());
                return userInfoDto;
            }
        });
        return userInfoDtoPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto updateInfoUser(UpdateUserRequest updateUserRequest) throws Exception {
        User user = userRepository.findByEmail(updateUserRequest.getEmail()).get();
        if (user == null) {
            throw new Exception("Không tìm thấy user");
        }
        user.setAddress(updateUserRequest.getAddress());
        user.setPhone(updateUserRequest.getPhone());
        User update = userRepository.save(user);
        return ModelMapperUtils.map(update, UserDto.class);
    }
}
