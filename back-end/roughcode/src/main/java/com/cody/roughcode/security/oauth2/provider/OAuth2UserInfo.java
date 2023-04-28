package com.cody.roughcode.security.oauth2.provider;

public interface OAuth2UserInfo {

    String getProviderId();

    String getProvider();

    String getName();

    String getEmail();
}
