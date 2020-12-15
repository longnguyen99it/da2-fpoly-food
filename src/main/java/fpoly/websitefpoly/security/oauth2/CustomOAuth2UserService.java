package fpoly.websitefpoly.security.oauth2;

import fpoly.websitefpoly.entity.AuthProvider;
import fpoly.websitefpoly.entity.Users;
import fpoly.websitefpoly.exception.OAuth2AuthenticationProcessingException;
import fpoly.websitefpoly.repository.UserRepository;
import fpoly.websitefpoly.security.UserPrincipal;
import fpoly.websitefpoly.security.oauth2.user.OAuth2UserInfo;
import fpoly.websitefpoly.security.oauth2.user.OAuth2UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        if (!checkFpoly(oAuth2UserInfo)) {
            throw new OAuth2AuthenticationProcessingException("Không đúng định dạng email");
        }

        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<Users> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        Users users;
        if (userOptional.isPresent()) {
            users = userOptional.get();
            if (!users.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        users.getProvider() + " account. Please use your " + users.getProvider() +
                        " account to login.");
            }
            users = updateExistingUser(users, oAuth2UserInfo);
        } else {
            users = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(users, oAuth2User.getAttributes());
    }

    private boolean checkFpoly(OAuth2UserInfo oAuth2UserInfo) {
        Pattern pattern = Pattern.compile("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$");
        Matcher matcher = pattern.matcher(oAuth2UserInfo.getEmail());
        return matcher.matches();
    }

    private Users registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        Users users = new Users();

        users.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        users.setProviderId(oAuth2UserInfo.getId());
        users.setName(oAuth2UserInfo.getName());
        users.setEmail(oAuth2UserInfo.getEmail());
        users.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(users);
    }

    private Users updateExistingUser(Users existingUsers, OAuth2UserInfo oAuth2UserInfo) {
        existingUsers.setName(oAuth2UserInfo.getName());
        existingUsers.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUsers);
    }

}
