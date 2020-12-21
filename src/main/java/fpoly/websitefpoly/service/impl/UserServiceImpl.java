package fpoly.websitefpoly.service.impl;

import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.common.ModelMapperUtils;
import fpoly.websitefpoly.config.AppProperties;
import fpoly.websitefpoly.dto.InvoiceDetailDto;
import fpoly.websitefpoly.dto.UserDto;
import fpoly.websitefpoly.dto.UserInfoDto;
import fpoly.websitefpoly.entity.Invoice;
import fpoly.websitefpoly.entity.Users;
import fpoly.websitefpoly.repository.InvoiceRepository;
import fpoly.websitefpoly.repository.UserRepository;
import fpoly.websitefpoly.request.UpdateUserRequest;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.service.InvoiceDetailsService;
import fpoly.websitefpoly.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class UserServiceImpl implements UserService {
    private final AppProperties appProperties;
    private final UserRepository userRepository;
    private final InvoiceDetailsService invoiceDetailsService;
    private final InvoiceRepository invoiceRepository;

    public UserServiceImpl(AppProperties appProperties,
                           UserRepository userRepository,
                           InvoiceDetailsService invoiceDetailsService,
                           InvoiceRepository invoiceRepository) {
        this.appProperties = appProperties;
        this.userRepository = userRepository;
        this.invoiceDetailsService = invoiceDetailsService;
        this.invoiceRepository = invoiceRepository;
    }

    //Lấy danh sách hóa đơn của user
    @Override
    public ResponeData<List<InvoiceDetailDto>> userInvoiceDetail(String email) {
        try {
            //Tìm kiếm user
            Optional<Users> user = userRepository.findByEmail(email);
            if (!user.isPresent()) {
                new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
            }
            //Lấy danh sách hóa đơn của user
            List<Invoice> invoiceList = invoiceRepository.findAllByUsers(user.get());
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
        Page<Users> userPage = userRepository.topInvoice(Invoice.FINISH, pageable);
        Page<UserInfoDto> userInfoDtoPage = userPage.map(new Function<Users, UserInfoDto>() {
            @Override
            public UserInfoDto apply(Users user) {
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
        Users users = userRepository.findByEmail(updateUserRequest.getEmail()).get();
        if (users == null) {
            throw new Exception("Không tìm thấy user");
        }
        users.setAddress(updateUserRequest.getAddress());
        users.setPhone(updateUserRequest.getPhone());
        Users update = userRepository.save(users);
        return ModelMapperUtils.map(update, UserDto.class);
    }

    @Override
    public String email(HttpServletRequest request) {
        String token = "";
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7, bearerToken.length());
        }

        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
