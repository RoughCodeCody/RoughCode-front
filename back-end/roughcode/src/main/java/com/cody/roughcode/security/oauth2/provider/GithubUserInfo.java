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
}
