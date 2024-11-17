package com.gymohrim.service.user;

import com.gymohrim.configuration.SecurityConfiguration;
import com.gymohrim.dto.user.UserRegistrationDTO;
import com.gymohrim.entity.User;
import com.gymohrim.entity.account.AuthProviderType;
import com.gymohrim.mapper.UserMapper;
import com.gymohrim.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {



    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Loading user from OAuth2 provider");

        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        log.info("OAuth2 User info: Email = {}, Name = {}", email, name);

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            log.info("User not found, creating new user with email: {}", email);

            UserRegistrationDTO dto = UserRegistrationDTO.builder()
                    .email(email)
                    .name(name)
                    .password(UUID.randomUUID().toString())
                    .build();

            User newUser = userMapper.toEntity(dto);
            newUser.setPassword(SecurityConfiguration.encodePassword(newUser.getPassword()));
            newUser.setAuthProviderType(AuthProviderType.GOOGLE);

            User savedUser = userRepository.save(newUser);
            log.info("New user created: {}", savedUser.getEmail());

            return savedUser;
        });

        log.info("User loaded: {}", user.getEmail());

        return new CustomOAuth2User(oAuth2User, user.getPassword());
    }





}

