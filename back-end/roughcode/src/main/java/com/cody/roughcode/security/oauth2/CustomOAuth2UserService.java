package com.cody.roughcode.security.oauth2;


import com.cody.roughcode.security.auth.UserDetailsCustom;
import com.cody.roughcode.security.oauth2.provider.GithubUserInfo;
import com.cody.roughcode.security.oauth2.provider.GoogleUserInfo;
import com.cody.roughcode.security.oauth2.provider.KaKaoUserInfo;
import com.cody.roughcode.security.oauth2.provider.OAuth2UserInfo;
import com.cody.roughcode.user.entity.Users;
import com.cody.roughcode.user.enums.Role;
import com.cody.roughcode.user.repository.UsersRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UsersRepository usersRepository;

    public CustomOAuth2UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // 깃허브로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다
    // OAuthAttributes: OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
    // UsersRepository: 엔티티 클래스를 DB에 접근하게 해주는 인터페이스
    // CustomOAuth2UserService: 깃허브 로그인 이후 가져온 사용자의 정보(email, name 등)들을 기반으로 가입 및 정보수정 등의 기능 지원
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo;
        if (userRequest.getClientRegistration().getRegistrationId().equals("github")) {
            System.out.println(oAuth2User.getAttributes());
            //깃허브 로그인 요청
            oAuth2UserInfo = new GithubUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            //구글 로그인 요청
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            //카카오 로그인 요청
            oAuth2UserInfo = new KaKaoUserInfo(oAuth2User.getAttributes());
        } else {
            //다른 소셜 로그인 요청
            return null;
        }
        //ex)kakao_1238471249
//        String username = oAuth2UserInfo.getProvider() + '_' + oAuth2UserInfo.getProviderId();
        String name = oAuth2UserInfo.getName();
        String email = oAuth2UserInfo.getEmail();

        // 이미 가입되어있는지 찾아봄
        // DB에 해당 유저가 있으면 유저를 바로 반환
        Optional<Users> userOptional =
                usersRepository.findByName(name);

        // DB에 해당 유저가 없으면 새로 만들어줌.
        // 닉네임은 해당 유저의 깃허브 아이디로, 이메일은 깃허브 연동 이메일을 넣어줌
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        Users user = userOptional.orElseGet(() ->
                usersRepository.save(
                        Users.builder()
                                .name(name)
                                .email(email)
                                .roles(roles)
                                .build()
                ));
        return new UserDetailsCustom(user, oAuth2User.getAttributes());
    }
}

