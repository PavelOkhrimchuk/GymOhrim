package com.gymohrim.service.user;

import com.gymohrim.configuration.SecurityConfiguration;
import com.gymohrim.entity.User;
import com.gymohrim.entity.account.AuthProviderType;
import com.gymohrim.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {



    private final UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            String randomPassword = UUID.randomUUID().toString();
            newUser.setPassword(SecurityConfiguration.encodePassword(randomPassword));
            newUser.setAuthProviderType(AuthProviderType.GOOGLE);
            return userRepository.save(newUser);
        });

        return new CustomOAuth2User(oAuth2User, user.getPassword());
    }





}

