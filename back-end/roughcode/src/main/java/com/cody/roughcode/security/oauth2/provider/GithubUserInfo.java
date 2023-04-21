package com.cody.roughcode.security.oauth2.provider;

import java.util.Map;

public class GithubUserInfo  implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    public GithubUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "github";
    }

    @Override
    public String getName() {
        return attributes.get("login").toString();
    }

    @Override
    public String getEmail() {
        // github의 경우 public으로 설정한 이메일이 없다면 null로 넘어옴
        // email이 null이라면 빈 문자열 넣기
        return (attributes.get("email") == null) ? "" : attributes.get("email").toString();
    }

}
